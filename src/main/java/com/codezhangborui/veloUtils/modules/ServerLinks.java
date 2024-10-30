package com.codezhangborui.veloUtils.modules;

import com.codezhangborui.veloUtils.Configuration;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.util.ServerLink;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.codezhangborui.veloUtils.Utils.loggerName;

public class ServerLinks {
    private static final Logger logger = Logger.getLogger(loggerName);
    public List<ServerLink> ServerLinkList = new ArrayList<>();

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        if (Configuration.getBoolean("serverlinks.enable")) {
            if(event.getPlayer().getProtocolVersion().getProtocol() < 767) return;
            this.ServerLinkList.clear();
            List<Map<String, Object>> links = Configuration.getMapList("serverlinks.links");
            links.forEach(link -> {
                try {
                    this.ServerLinkList.add(ServerLink.serverLink(ServerLink.Type.valueOf(link.get("display").toString()), link.get("url").toString()));
                } catch (IllegalArgumentException e) {
                    this.ServerLinkList.add(ServerLink.serverLink(MiniMessage.miniMessage().deserialize(link.get("display").toString()), link.get("url").toString()));
                }
            });
            event.getPlayer().setServerLinks(this.ServerLinkList);
        }
    }
}
