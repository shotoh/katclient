package io.github.shotoh.katclient.commands;

import gg.essential.api.EssentialAPI;
import gg.essential.universal.UChat;
import io.github.shotoh.katclient.KatClient;
import io.github.shotoh.katclient.features.general.CrystalHollowsScan;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
            EssentialAPI.getGuiUtil().openScreen(KatClient.config.gui());
        } else {
            switch (args[0]) {
                case "ch":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "scan":
                                CrystalHollowsScan.runCommand();
                                break;
                            case "stop":
                                CrystalHollowsScan.stop();
                                break;
                            default:
                                UChat.chat("&dThis command does not exist!");
                                UChat.chat("&dUsage: /kc ch (scan/stop)");
                                break;
                        }
                    } else {
                        UChat.chat("&cYou are missing some arguments!");
                    }
                    break;
                default:
                    UChat.chat("&dThis command does not exist!");
                    UChat.chat("&dUsage: /kc (ch)");
                    break;
            }
        }
    }
}