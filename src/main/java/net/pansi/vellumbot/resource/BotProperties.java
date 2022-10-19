package net.pansi.vellumbot.resource;

public class BotProperties extends VellumProperties {

    public BotProperties() {
        super("bot.properties");
    }

    public String getToken() {
        return getProperty("token");
    }
}
