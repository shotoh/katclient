package io.github.shotoh.katclient.features.qol;

import gg.essential.api.EssentialAPI;
import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class TermAutoClicker {
    private long lastRightClick;
    private double speed;
    private double duration;

    public TermAutoClicker() {
        updateValues();
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        if (mc.thePlayer.getHeldItem() == null || !mc.thePlayer.getHeldItem().getDisplayName().contains("Terminator")) {
            return;
        }
        if (mc.currentScreen != null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            return;
        }
        if (Mouse.isButtonDown(1)) {
            if (System.currentTimeMillis() - lastRightClick > speed * 1000.0) {
                lastRightClick = System.currentTimeMillis();
                int key = mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                updateValues();
            } else if (System.currentTimeMillis() - lastRightClick > duration * 1000.0) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                updateValues();
            }
        }
    }

    private void updateValues() {
        if (KatClientConfig.maxCPS <= KatClientConfig.minCPS) {
            KatClientConfig.minCPS = 8.0f;
            KatClientConfig.maxCPS = 12.0f;
            EssentialAPI.getNotifications().push("Term Auto Clicker", "CPS config values were incorrect and has been reset!");
        }
        speed = 1.0 / ThreadLocalRandom.current().nextDouble(KatClientConfig.minCPS, KatClientConfig.maxCPS);
        duration = speed / ThreadLocalRandom.current().nextDouble(KatClientConfig.minCPS, KatClientConfig.maxCPS);
    }
}
