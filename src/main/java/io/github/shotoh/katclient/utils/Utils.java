package io.github.shotoh.katclient.utils;

import io.github.shotoh.katclient.KatClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundList;
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

    public static void playSound(String name, float volume, float pitch) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        player.playSound(name, volume, pitch);
    }

    public static void socket(String msg) {
        if (KatClient.SOCKET != null) {
            KatClient.SOCKET.send(msg);
        } else {
            sendMessage("§c[KC] Socket unavailable");
        }
    }
}
