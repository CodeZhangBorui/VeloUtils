package com.codezhangborui.veloUtils.commands;

import com.codezhangborui.veloUtils.Configuration;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;
import java.util.List;

import static com.codezhangborui.veloUtils.Utils.buildMessage;

public class VeloUtilsCommand implements SimpleCommand {

    private static final List<String> COMMANDS = Arrays.asList("reload");

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (source instanceof ConsoleCommandSource || source.hasPermission("veloutils.admin")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                Configuration.reload();
                source.sendMessage(buildMessage(
                        Component.text("Configuration has been successfully reloaded.", NamedTextColor.GREEN)
                ));
            } else if(args.length > 0 && args[0].equalsIgnoreCase("help")) {
                source.sendMessage(buildMessage(
                        Component.text("Usage: /vutils <reload>")
                ));
            } else {
                source.sendMessage(buildMessage(
                        Component.text()
                                .append(Component.text("Invalid command. ", NamedTextColor.RED))
                                .append(Component.text("Use \"/vutils help\" for help", NamedTextColor.AQUA))
                                .build()
                ));
            }
        } else {
            source.sendMessage(buildMessage(
                        Component.text("You do not have permission to execute this command.", NamedTextColor.RED)
                ));
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 1) {
            return COMMANDS.stream()
                    .filter(cmd -> cmd.startsWith(args[0].toLowerCase()))
                    .toList();
        }
        return List.of();
    }
}