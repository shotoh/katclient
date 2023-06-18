package io.github.shotoh.katclient.mixins;

import io.github.shotoh.katclient.KatClient;
import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(FontRenderer.class)
public abstract class NickHider {
    @Shadow
    protected abstract void renderStringAtPos(String text, boolean shadow);

    @Shadow public abstract int getStringWidth(String text);

    @Inject(method = "renderStringAtPos", at = @At("HEAD"), cancellable = true)
    private void renderStringAtPos(String text, boolean shadow, CallbackInfo ci) {
        if (KatClientConfig.nickHider) {
            if (KatClient.data.getNickHiderMap().keySet().stream().anyMatch(text::contains)) {
                ci.cancel();
                this.renderStringAtPos(replaceText(text), shadow);
            }
        }
    }

    @Inject(method = "getStringWidth", at = @At("RETURN"), cancellable = true)
    private void getStringWidth(String text, CallbackInfoReturnable<Integer> cir) {
        if (text != null && KatClientConfig.nickHider) {
            if (KatClient.data.getNickHiderMap().keySet().stream().anyMatch(text::contains)) {
                cir.setReturnValue(this.getStringWidth(replaceText(text)));
            }
        }
    }

    private String replaceText(String text) {
        for (Map.Entry<String, String> entry : KatClient.data.getNickHiderMap().entrySet()) {
            text = text.replaceAll(entry.getKey(), entry.getValue());
        }
        return text;
    }
}
