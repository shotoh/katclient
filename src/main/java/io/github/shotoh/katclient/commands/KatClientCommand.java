package io.github.shotoh.katclient.commands;

import io.github.shotoh.katclient.KatClient;
import io.github.shotoh.katclient.features.general.InquisitorWaypoints;
import io.github.shotoh.katclient.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.Collections;
import java.util.List;

public class KatClientCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "katclient";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("kc");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/katclient";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            KatClient.CONFIG.openGui();
        } else {
            if (args[0].equals("test")) {
                Utils.sendMessage("§ddev test");

                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                if (player == null) return;
                BlockPos coords = new BlockPos(player.posX, player.posY, player.posZ);
                InquisitorWaypoints.alertInquisitor(player, coords);
                player.sendChatMessage("/pc [KC] Inquisitor @ " +
                        coords.getX() + ", " + coords.getY() + ", " + coords.getZ());
                Utils.socket("Inquisitor @ " +
                        coords.getX() + ", " + coords.getY() + ", " + coords.getZ());
            }
        }
    }
}