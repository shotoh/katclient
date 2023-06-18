package io.github.shotoh.katclient.mixins;

import io.github.shotoh.katclient.events.PacketSentEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onPacketSentEvent(Packet<?> packet, CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post(new PacketSentEvent(packet))) {
            callbackInfo.cancel();
        }
    }
}
