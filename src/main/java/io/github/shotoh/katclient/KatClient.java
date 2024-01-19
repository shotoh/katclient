package io.github.shotoh.katclient;

import io.github.shotoh.katclient.commands.KatClientCommand;
import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.features.general.InquisitorWaypoints;
import io.github.shotoh.katclient.features.general.PartyNotifications;
import io.github.shotoh.katclient.network.KatSocket;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.IOException;

@Mod(modid = KatClient.MODID, name = KatClient.NAME, version = KatClient.VERSION, clientSideOnly = true)
public class KatClient {
    public static final String MODID = "katclient";
    public static final String NAME = "Kat Client";
    public static final String VERSION = "1.4.0";

    public static KatClientConfig CONFIG;
    public static KatSocket SOCKET;

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        KatClient.CONFIG = new KatClientConfig();

        new Thread(() -> {
            try {
                KatClient.SOCKET = new KatSocket("localhost", -1);
                KatClient.SOCKET.listen();
            } catch (IOException ex) {
                System.out.println("Failed to form socket connection, some features may not work!");
            }
        }).start();

        // commands
        ClientCommandHandler.instance.registerCommand(new KatClientCommand());

        // features
        MinecraftForge.EVENT_BUS.register(new InquisitorWaypoints());
        MinecraftForge.EVENT_BUS.register(new PartyNotifications());
    }
}
