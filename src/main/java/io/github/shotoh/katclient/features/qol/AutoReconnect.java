package io.github.shotoh.katclient.features.qol;

import gg.essential.universal.UChat;
import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoReconnect {
    private final ScheduledExecutorService pool;

    public AutoReconnect() {
        pool = Executors.newScheduledThreadPool(3);
    }

    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (KatClientConfig.autoReconnect && player != null) {
            String text = event.message.getUnformattedText();
            if (text.contains("You were spawned in Limbo")) {
                pool.schedule(() -> {
                    UChat.say("/lobby");
                    pool.schedule(() -> {
                        UChat.say("/play skyblock");
                        pool.schedule(() -> UChat.say("/is"), 15000, TimeUnit.MILLISECONDS);
                    }, 15000, TimeUnit.MILLISECONDS);
                }, 15000, TimeUnit.MILLISECONDS);
            } else if (text.contains("You are AFK. Move around to return from AFK.")) {
                pool.schedule(() -> {
                    UChat.say("/lobby");
                    pool.schedule(() -> {
                        UChat.say("/play skyblock");
                        pool.schedule(() -> UChat.say("/is"), 15000, TimeUnit.MILLISECONDS);
                    }, 15000, TimeUnit.MILLISECONDS);
                }, 15000, TimeUnit.MILLISECONDS);
            } else if (text.contains("https://discord.gg/mQZC94xr4d")) {
                pool.schedule(() -> {
                    UChat.say("/play skyblock");
                    pool.schedule(() -> UChat.say("/is"), 15000, TimeUnit.MILLISECONDS);
                }, 15000, TimeUnit.MILLISECONDS);
            } else if (text.contains("[Important] This server will restart soon")) {
                pool.schedule(() -> UChat.say("/is"), 15000, TimeUnit.MILLISECONDS);
            }
        }
    }
}
