package io.github.shotoh.katclient.features.general;

import io.github.shotoh.katclient.KatClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PartyNotifications {
    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (KatClient.CONFIG.generalCategory.fishingNotifications && player != null) {
            String text = event.message.getUnformattedText();
            String coords = "(" + (int) player.posX + ", " + (int) player.posY + ", " + (int) player.posZ + ")";
            switch (text) {
                case "A Vanquisher is spawning nearby!":
                    player.sendChatMessage("/pc [KC] Vanquisher @ " + coords);
                    break;
                case "You hear a massive rumble as Thunder emerges.":
                    player.sendChatMessage("/pc [KC] Thunder @ " + coords);
                    break;
                case "You have angered a legendary creature... Lord Jawbus has arrived":
                    player.sendChatMessage("/pc [KC] Lord Jawbus @ " + coords);
                    break;
                case "What is this creature!?":
                    player.sendChatMessage("/pc [KC] Yeti @ " + coords);
                    break;
                case "You found a forgotten Nutcracker laying beneath the ice.":
                    player.sendChatMessage("/pc [KC] Nutcracker @ " + coords);
                    break;
                case "Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood!":
                    player.sendChatMessage("/pc [KC] Great White Shark @ " + coords);
                    break;
                case "The spirit of a long lost Phantom Fisher has come to haunt you.":
                    player.sendChatMessage("/pc [KC] Phantom Fisher @ " + coords);
                    break;
                case "This can't be! The manifestation of death himself!":
                    player.sendChatMessage("/pc [KC] Grim Reaper @ " + coords);
                    break;
                case "The Sea Emperor arises from the depths.":
                    player.sendChatMessage("/pc [KC] The Sea Emperor @ " + coords);
                    break;
                case "You are not in a party right now.":
                    KatClient.CONFIG.generalCategory.fishingNotifications = false;
                    break;
            }
        }
    }
}
