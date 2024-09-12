package com.codezhangborui.velocityUtils.modules;

import com.codezhangborui.velocityUtils.Configuration;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.logging.Logger;

import static com.codezhangborui.velocityUtils.Utils.loggerName;
import static com.codezhangborui.velocityUtils.Utils.messageHeader;

public class ServerWhitelist {
    private final Logger logger = Logger.getLogger(loggerName);

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        if(Arrays.asList(Configuration.getStringList("whitelist.servers")).contains(event.getOriginalServer().getServerInfo().getName())) {
            if (!event.getPlayer().hasPermission("velocityutils.whitelist." + event.getOriginalServer().getServerInfo().getName())) {
                logger.info("Player " + event.getPlayer().getUsername() + " does not have permission to connect to server " + event.getOriginalServer().getServerInfo().getName());
                event.getPlayer().sendMessage(Component.text(messageHeader + "你没有权限连接到这个服务器！"));
                event.setResult(ServerPreConnectEvent.ServerResult.denied());
            }
        }
    }
}