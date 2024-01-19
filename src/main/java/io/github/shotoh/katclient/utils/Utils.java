package io.github.shotoh.katclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;

public class Utils {
    public static void sendMessage(String msg) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        player.addChatMessage(new ChatComponentText(msg));
    }
}
