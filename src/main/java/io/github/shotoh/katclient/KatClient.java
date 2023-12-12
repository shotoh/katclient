package io.github.shotoh.katclient;

import io.github.shotoh.katclient.commands.KatClientCommand;
import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.features.general.FishingPartyNotifications;
import io.github.shotoh.katclient.features.qol.FreeCam;
import io.github.shotoh.katclient.features.qol.GhostBlocks;
import io.github.shotoh.katclient.features.qol.TermAutoClicker;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = KatClient.MODID, name = KatClient.NAME, version = KatClient.VERSION, clientSideOnly = true)
public class KatClient {
    public static final String MODID = "katclient";
    public static final String NAME = "Kat Client";
    public static final String VERSION = "1.3.3";

    public static KatClientConfig config;

    public KatClient() {
        config = KatClientConfig.config;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        config.preload();

        // commands
        ClientCommandHandler.instance.registerCommand(new KatClientCommand());

        // features
        MinecraftForge.EVENT_BUS.register(new FishingPartyNotifications());

        // qol
        MinecraftForge.EVENT_BUS.register(new FreeCam());
        MinecraftForge.EVENT_BUS.register(new GhostBlocks());
        MinecraftForge.EVENT_BUS.register(new TermAutoClicker());

        // keybinds
        ClientRegistry.registerKeyBinding(FreeCam.freeCamKeyBind);
        ClientRegistry.registerKeyBinding(GhostBlocks.ghostBlocksKeyBind);
    }
}
