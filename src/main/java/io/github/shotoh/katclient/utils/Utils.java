package io.github.shotoh.katclient.utils;

import io.github.shotoh.katclient.KatClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.UUID;

public class Utils {
    public static void sendMessage(String msg) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        player.addChatMessage(new ChatComponentText(msg));
    }

    public static UUID getUUID(String name) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null) return null;
        UUID uuid = null;
        for (EntityPlayer e : world.playerEntities) {
            if (e.getName().equals(name)) {
                uuid = e.getUniqueID();
                break;
            }
        }
        return uuid;
    }

    public static void sendSocket(String msg) {
        if (KatClient.SOCKET != null) {
            KatClient.SOCKET.send(msg);
        } else {
            sendMessage("§c[KC] Socket unavailable");
        }
    }
}
