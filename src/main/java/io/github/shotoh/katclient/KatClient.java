package io.github.shotoh.katclient;

import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.features.general.InquisitorWaypoints;
import io.github.shotoh.katclient.features.general.PartyNotifications;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = KatClient.MODID, name = KatClient.NAME, version = KatClient.VERSION, clientSideOnly = true)
public class KatClient {
    public static final String MODID = "katclient";
    public static final String NAME = "Kat Client";
    public static final String VERSION = "1.4.0";

    public static KatClientConfig CONFIG = new KatClientConfig();

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        // features
        MinecraftForge.EVENT_BUS.register(new InquisitorWaypoints());
        MinecraftForge.EVENT_BUS.register(new PartyNotifications());
    }
}
