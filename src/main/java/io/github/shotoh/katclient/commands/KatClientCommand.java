package io.github.shotoh.katclient.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import gg.essential.api.EssentialAPI;
import gg.essential.universal.UChat;
import gg.essential.universal.wrappers.message.UMessage;
import io.github.shotoh.katclient.KatClient;
import io.github.shotoh.katclient.core.DataHandler;
import io.github.shotoh.katclient.core.KatClientData;
import io.github.shotoh.katclient.features.general.CrystalHollowsScan;
import io.github.shotoh.katclient.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            KatClientData data = KatClient.data;
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
                case "nickhider":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "add":
                                if (args.length > 3) {
                                    Matcher matcher = Pattern.compile("[a-zA-Z0-9&_]+").matcher(args[3]);
                                    if (matcher.matches()) {
                                        args[3] = Utils.translateAlternateColorCodes('&', args[3] + "&r");
                                        if (!ChatFormatting.stripFormatting(args[3]).contains(args[2])) {
                                            data.getNickHiderMap().put(args[2], args[3]);
                                            UChat.chat("&dData saved!");
                                        } else {
                                            UChat.chat("&cYou cannot have the username inside the nickname!");
                                        }
                                    } else {
                                        UChat.chat("&cSpecial characters are not supported in this feature!");
                                    }
                                } else {
                                    UChat.chat("&cYou are missing some arguments!");
                                }
                                break;
                            case "remove":
                                if (args.length > 2) {
                                    data.getNickHiderMap().remove(args[2]);
                                    UChat.chat("&dData saved!");
                                } else {
                                    UChat.chat("&cYou are missing some arguments!");
                                }
                                break;
                            case "clear":
                                data.getNickHiderMap().clear();
                                UChat.chat("&dCleared the nick hider list!");
                                break;
                            default:
                                UChat.chat("&dThis command does not exist!");
                                UChat.chat("&dUsage: /kc nickhider (add/remove/clear)");
                                break;
                        }
                    } else {
                        UChat.chat("&cYou are missing some arguments!");
                    }
                    break;
                default:
                    UChat.chat("&dThis command does not exist!");
                    UChat.chat("&dUsage: /kc (ch/nickhider)");
                    break;
            }
            DataHandler.save();
        }
    }
}