package io.github.shotoh.katclient.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketSentEvent extends Event {
    public Packet<?> packet;

    public PacketSentEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
