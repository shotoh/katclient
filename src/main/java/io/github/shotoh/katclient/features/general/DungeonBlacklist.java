package io.github.shotoh.katclient.features.general;

import io.github.shotoh.katclient.KatClient;
import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DungeonBlacklist {
    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        WorldClient world = mc.theWorld;
        Connection conn = KatClient.DB;
        if (KatClientConfig.dungeonBlacklist && player != null && world != null) {
            String text = event.message.getUnformattedText();
            if (!text.contains("joined the dungeon group")) return;
            if (conn == null) {
                Utils.sendMessage("§c[KC] Database connection does not exist");
                return;
            }
            String[] args = text.split(" ");
            if (args.length < 4) return;
            String name = args[3];
            UUID uuid = Utils.getUUID(name);
            if (uuid == null) return;
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM dungeon_blacklist WHERE uuid=UUID_TO_BIN(?)");
                statement.setString(1, uuid.toString());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String reason = rs.getString("reason");
                    if (reason != null) {
                        player.sendChatMessage("/p kick " + name);
                        Utils.sendMessage("§c[KC] " + name + " is §4blacklisted§c, reason='" + reason + "'");
                        Utils.playSound("random.anvil_land", 1f, 1f);
                    }
                }
                rs.close();
                statement.close();
            } catch (SQLException e) {
                Utils.sendMessage("§c[KC] Database query failed");
            }
        }
    }

    public static void addPlayer(String name, String reason) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        if (name == null || reason == null) {
            Utils.sendMessage("§c[KC] Username or reason is invalid");
        }
        Connection conn = KatClient.DB;
        if (conn == null) {
            Utils.sendMessage("§c[KC] Database connection does not exist");
            return;
        }
        UUID uuid = Utils.getUUID(name);
        if (uuid == null) {
            Utils.sendMessage("§c[KC] Invalid player");
            return;
        }
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO dungeon_blacklist (uuid, username, reason) " +
                            "VALUES UUID_TO_BIN(?), ?, ?");
            statement.setString(1, uuid.toString());
            statement.setString(2, name);
            statement.setString(3, reason);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Utils.sendMessage("§c[KC] " + name + " is now §4blacklisted");
                Utils.socket(player.getName() + " blacklisted " + name + ", reason=" + reason);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            Utils.sendMessage("§c[KC] Database query failed (maybe duplicate uuid)");
        }
    }

    public static void removePlayer(String name) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        if (name == null) {
            Utils.sendMessage("§c[KC] Username is invalid");
        }
        Connection conn = KatClient.DB;
        if (conn == null) {
            Utils.sendMessage("§c[KC] Database connection does not exist");
            return;
        }
        UUID uuid = Utils.getUUID(name);
        if (uuid == null) {
            Utils.sendMessage("§c[KC] Invalid player");
            return;
        }
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM dungeon_blacklist WHERE uuid=UUID_TO_BIN(?)");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Utils.sendMessage("§c[KC] " + name + " is no longer §4blacklisted");
                Utils.socket(player.getName() + " unblacklisted " + name);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            Utils.sendMessage("§c[KC] Database query failed (maybe invalid uuid)");
        }
    }

    public static void checkSocket(String msg) {
        if (!msg.contains("blacklisted")) return;
        Utils.sendMessage("§c[KC] " + msg);
        Utils.playSound("note.pling", 1f, 1f);
    }
}
