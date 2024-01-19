package io.github.shotoh.katclient;

import com.google.gson.Gson;
import io.github.shotoh.katclient.commands.KatClientCommand;
import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.features.general.DungeonBlacklist;
import io.github.shotoh.katclient.features.general.InquisitorWaypoints;
import io.github.shotoh.katclient.features.general.FishingNotifications;
import io.github.shotoh.katclient.network.KatSocket;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Mod(modid = KatClient.MODID, name = KatClient.NAME, version = KatClient.VERSION, clientSideOnly = true)
public class KatClient {
    public static final String MODID = "katclient";
    public static final String NAME = "Kat Client";
    public static final String VERSION = "1.5.0";

    public static KatClientConfig CONFIG;
    public static Connection DB;
    public static KatSocket SOCKET;
    public static Gson GSON;

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        KatClient.CONFIG = new KatClientConfig();
        KatClient.GSON = new Gson();

        new Thread(() -> {
            try {
                KatClient.SOCKET = new KatSocket(KatClientConfig.serverIP, KatClientConfig.socketPort);
                KatClient.SOCKET.listen();
            } catch (IOException ex) {
                System.out.println("Failed to form socket connection, some features may not work!");
            }
        }).start();

        try {
            KatClient.DB = DriverManager.getConnection("jdbc:mysql://" + KatClientConfig.serverIP + ":3306/kat",
                    KatClientConfig.databaseUsername, KatClientConfig.databasePassword);
        } catch (SQLException ex) {
            System.out.println("Database connection failed, some features may not work!");
        }

        // commands
        ClientCommandHandler.instance.registerCommand(new KatClientCommand());

        // features
        MinecraftForge.EVENT_BUS.register(new DungeonBlacklist());
        MinecraftForge.EVENT_BUS.register(new InquisitorWaypoints());
        MinecraftForge.EVENT_BUS.register(new FishingNotifications());
    }
}
