package io.github.shotoh.katclient.features.general;

import gg.essential.universal.UChat;
import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FishingPartyNotifications {
    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (KatClientConfig.partyFishingNotifications && player != null) {
            String text = event.message.getUnformattedText();
            String coords = "(" + (int) player.posX + ", " + (int) player.posY + ", " + (int) player.posZ + ")";
            switch (text) {
                case "A Vanquisher is spawning nearby!":
                    UChat.say("/pc [KC] Vanquisher @ " + coords);
                    break;
                case "You hear a massive rumble as Thunder emerges.":
                    UChat.say("/pc [KC] Thunder @ " + coords);
                    break;
                case "You have angered a legendary creature... Lord Jawbus has arrived":
                    UChat.say("/pc [KC] Lord Jawbus @ " + coords);
                    break;
                case "What is this creature!?":
                    UChat.say("/pc [KC] Yeti @ " + coords);
                    break;
                case "You found a forgotten Nutcracker laying beneath the ice.":
                    UChat.say("/pc [KC] Nutcracker @ " + coords);
                    break;
                case "Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood!":
                    UChat.say("/pc [KC] Great White Shark @ " + coords);
                    break;
                case "The spirit of a long lost Phantom Fisher has come to haunt you.":
                    UChat.say("/pc [KC] Phantom Fisher @ " + coords);
                    break;
                case "This can't be! The manifestation of death himself!":
                    UChat.say("/pc [KC] Grim Reaper @ " + coords);
                    break;
                case "The Sea Emperor arises from the depths.":
                    UChat.say("/pc [KC] The Sea Emperor @ " + coords);
                    break;
                case "You are not in a party right now.":
                    KatClientConfig.partyFishingNotifications = false;
                    break;
            }
        }
    }
}
