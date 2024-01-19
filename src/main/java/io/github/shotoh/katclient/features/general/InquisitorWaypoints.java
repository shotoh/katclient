package io.github.shotoh.katclient.features.general;

import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.utils.RenderUtils;
import io.github.shotoh.katclient.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class InquisitorWaypoints {
    private static long prev = System.currentTimeMillis();
    private static BlockPos coords = null;

    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (!KatClientConfig.inquisitorWaypoints) return;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        Entity entity = event.entity;
        if (!(entity instanceof EntityOtherPlayerMP)) return;
        String name = entity.getName();
        if (!name.contains("Minos Inquisitor")) return;
        long curr = System.currentTimeMillis();
        long diff = curr - prev;
        if (diff < 60000) return;
        prev = curr;

        alertInquisitor(player, new BlockPos(player.posX, player.posY, player.posZ));
        player.sendChatMessage("/pc [KC] Inquisitor @ " +
                coords.getX() + ", " + coords.getY() + ", " + coords.getZ());
        Utils.socket("Inquisitor @ " +
                coords.getX() + ", " + coords.getY() + ", " + coords.getZ());
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if (coords == null) return;
        RenderUtils.renderBeaconBeam(coords, 0xFF0000, event.partialTicks);
    }

    public static void checkSocket(String msg) {
        if (!KatClientConfig.inquisitorWaypoints) return;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        if (!msg.startsWith("Inquisitor @ ")) return;

        double[] arr = Arrays.stream(msg.split(" @ ")[1].split(", "))
                .mapToDouble(Double::parseDouble).toArray();
        alertInquisitor(player, new BlockPos(arr[0], arr[1], arr[2]));
    }

    public static void alertInquisitor(EntityPlayerSP player, BlockPos pos) {
        coords = pos;
        new Thread(() -> {
            try {
                Thread.sleep(30000);
                coords = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
