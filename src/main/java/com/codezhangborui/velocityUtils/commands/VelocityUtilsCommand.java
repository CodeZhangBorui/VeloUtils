package com.codezhangborui.velocityUtils.commands;

import com.codezhangborui.velocityUtils.Configuration;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.List;

import static com.codezhangborui.velocityUtils.Utils.messageHeader;

public class VelocityUtilsCommand implements SimpleCommand {

    private static final List<String> COMMANDS = Arrays.asList("reload");

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (source instanceof ConsoleCommandSource || source.hasPermission("velocityutils.admin")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                Configuration.reload();
                source.sendMessage(Component.text(messageHeader + "Configuration of VelocityUtils reloaded successfully."));
            } else {
                source.sendMessage(Component.text(messageHeader + "Usage: /velocityutils reload"));
            }
        } else {
            source.sendMessage(Component.text(messageHeader + "You do not have permission to execute this command."));
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