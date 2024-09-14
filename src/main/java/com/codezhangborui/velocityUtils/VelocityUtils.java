package com.codezhangborui.velocityUtils;

import com.codezhangborui.velocityUtils.commands.VelocityUtilsCommand;
import com.codezhangborui.velocityUtils.modules.ServerWhitelist;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;
import java.util.logging.Logger;

import static com.codezhangborui.velocityUtils.Utils.loggerName;

@Plugin(id = "velocityutils", name = "VelocityUtils", version = BuildConstants.VERSION, authors = {"CodeZhangborui"})
public class VelocityUtils {

    private final Logger logger = Logger.getLogger(loggerName);

    @Inject
    private ProxyServer server;

    @Inject
    @DataDirectory
    private Path dataDirectory;

    private boolean loadConfig() {
        try {
            Configuration.init(dataDirectory);
        } catch (Exception e) {
            logger.severe("Could not load configuration file.");
            e.printStackTrace();
            return false;
        }
        Configuration.setDefault(
                "whitelist.enable",
                false,
                "Whitelist permission control for specific sub-servers");
        Configuration.setDefault(
                "whitelist.servers",
                new String[]{"server"},
                "List of sub-servers that will be controlled by white list");
        try {
            Messages.init(dataDirectory);
        } catch (Exception e) {
            logger.severe("Could not load messages file.");
            e.printStackTrace();
            return false;
        }
        Messages.setDefault(
                "whitelist-no-permission",
                "<red>You do not have permission to connect to this server!",
                "Displayed when a player is not whitelisted on the specific sub-server.");
        return true;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Initializing VelocityUtils Version " + BuildConstants.VERSION + " by CodeZhangborui");
        logger.info("Loading Configuration...");
        if (!loadConfig()) {
            logger.severe("Failed to load configuration. Disabling plugin.");
            return;
        }
        if (Configuration.getBoolean("whitelist.enable")) {
            server.getEventManager().register(this, new ServerWhitelist());
            logger.info("Module ServerWhitelist enabled");
        }
        CommandManager commandManager = server.getCommandManager();
        commandManager.register(commandManager.metaBuilder("vutils").build(), new VelocityUtilsCommand());
        logger.info("Initialization complete. VelocityUtils is now active.");
    }
}
