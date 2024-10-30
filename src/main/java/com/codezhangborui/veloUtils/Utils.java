package com.codezhangborui.veloUtils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Utils {
    public final static String loggerName = "\033[95mVelocity\033[35mUtils\033[0m";
    public static TextComponent buildMessage(TextComponent message) {
        return Component.text()
            .append(Component.text("[", NamedTextColor.GRAY))
            .append(Component.text("V", NamedTextColor.LIGHT_PURPLE))
            .append(Component.text("U", NamedTextColor.DARK_PURPLE))
            .append(Component.text("] ", NamedTextColor.GRAY))
            .resetStyle()
            .append(message)
            .build();
    }
}
