package io.github.shotoh.katclient.features.qol;

import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class GhostBlocks {
    public static KeyBinding ghostBlocksKeyBind = new KeyBinding("Ghost Blocks", Keyboard.KEY_G, "Kat Client");

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        ArrayList<Block> interactables = new ArrayList<>(Arrays.asList(Blocks.chest, Blocks.lever, Blocks.trapped_chest, Blocks.wooden_button, Blocks.stone_button, Blocks.skull));
        BlockPos blockPos;
        Block block;
        if (KatClientConfig.ghostBlocks && ghostBlocksKeyBind.isKeyDown()) {
            blockPos = mc.thePlayer.rayTrace(mc.playerController.getBlockReachDistance(), 1.0f).getBlockPos();
            if (blockPos != null) {
                block = mc.theWorld.getBlockState(blockPos).getBlock();
                if (!interactables.contains(block)) {
                    mc.theWorld.setBlockToAir(blockPos);
                }
            }
        }
    }
}
