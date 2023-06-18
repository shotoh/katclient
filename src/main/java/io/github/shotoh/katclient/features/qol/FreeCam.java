package io.github.shotoh.katclient.features.qol;

import gg.essential.api.EssentialAPI;
import io.github.shotoh.katclient.core.KatClientConfig;
import io.github.shotoh.katclient.events.PacketSentEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class FreeCam {
    public static final KeyBinding freeCamKeyBind = new KeyBinding("Free Cam", Keyboard.KEY_J, "Kat Client");

    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean enabled;
    private EntityOtherPlayerMP playerEntity;

    public FreeCam() {
        enabled = false;
    }

    private void enable() {
        EssentialAPI.getNotifications().push("FreeCam", "FreeCam is now enabled");
        if (mc.theWorld != null) {
            (this.playerEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile())).copyLocationAndAnglesFrom(mc.thePlayer);
            this.playerEntity.onGround = mc.thePlayer.onGround;
            mc.theWorld.addEntityToWorld(-2137, this.playerEntity);
        }
    }

    private void disable() {
        EssentialAPI.getNotifications().push("FreeCam", "FreeCam is now disabled");
        if (mc.thePlayer == null || mc.theWorld == null || this.playerEntity == null) {
            return;
        }
        mc.thePlayer.noClip = false;
        mc.thePlayer.setPosition(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ);
        mc.theWorld.removeEntityFromWorld(-2137);
        this.playerEntity = null;
        mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        if (KatClientConfig.freeCam) {
            if (freeCamKeyBind.isPressed()) {
                enabled = !enabled;
                if (enabled) {
                    enable();
                } else {
                    disable();
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (enabled) {
            mc.thePlayer.noClip = true;
            mc.thePlayer.fallDistance = 0.0f;
            mc.thePlayer.onGround = false;
            mc.thePlayer.capabilities.isFlying = false;
            mc.thePlayer.motionY = 0.0;
            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f) {
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
            }
            double speed = 0.1;
            mc.thePlayer.jumpMovementFactor = (float) speed;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY += speed * 3.0;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY -= speed * 3.0;
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        if (enabled) {
            enabled = false;
            disable();
        }
    }

    @SubscribeEvent
    public void onPacket(PacketSentEvent event) {
        if (enabled && event.packet instanceof C03PacketPlayer) {
            event.setCanceled(true);
        }
    }
}
