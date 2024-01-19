package io.github.shotoh.katclient.features.general;

import io.github.shotoh.katclient.KatClient;
import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DungeonBlacklist {
    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        Connection conn = KatClient.DB;
        if (KatClientConfig.dungeonBlacklist && player != null && conn != null) {
            String text = event.message.getUnformattedText();
            if (!text.contains("joined the dungeon group")) return;
            String[] args = text.split(" ");
            if (args.length < 4) return;
            String user = args[3];

            try {
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM dungeon_blacklist WHERE username=?");
            } catch (SQLException e) {
                player.addChatMessage(new ChatComponentText("§cDatabase query failed"));
            }
        }
    }
}
