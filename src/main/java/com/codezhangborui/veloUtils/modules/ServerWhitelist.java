package com.codezhangborui.veloUtils.modules;

import com.codezhangborui.veloUtils.Configuration;
import com.codezhangborui.veloUtils.Messages;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Arrays;
import java.util.logging.Logger;

import static com.codezhangborui.veloUtils.Utils.buildMessage;
import static com.codezhangborui.veloUtils.Utils.loggerName;

public class ServerWhitelist {
    private final Logger logger = Logger.getLogger(loggerName);

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        if(Arrays.asList(Configuration.getStringList("whitelist.servers")).contains(event.getOriginalServer().getServerInfo().getName())) {
            if (!event.getPlayer().hasPermission("veloutils.whitelist." + event.getOriginalServer().getServerInfo().getName())) {
                logger.info("Player " + event.getPlayer().getUsername() + " does not have permission to connect to server " + event.getOriginalServer().getServerInfo().getName());
                event.getPlayer().sendMessage(buildMessage(
                        (TextComponent) MiniMessage.miniMessage().deserialize(Messages.getString("whitelist-no-permission"))
                ));
                event.setResult(ServerPreConnectEvent.ServerResult.denied());
            }
        }
    }
}
