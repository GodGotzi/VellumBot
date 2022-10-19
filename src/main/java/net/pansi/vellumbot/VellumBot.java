package net.pansi.vellumbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.internal.utils.JDALogger;
import net.pansi.vellumbot.command.CommandHandler;
import net.pansi.vellumbot.command.commands.CalendarCommand;
import net.pansi.vellumbot.listeners.CommandListener;
import net.pansi.vellumbot.listeners.GuildListener;
import net.pansi.vellumbot.resource.BotProperties;
import net.pansi.vellumbot.resource.CommandProperties;

public class VellumBot {

    private final BotProperties botProperties;
    private final CommandProperties commandProperties;
    private final CommandHandler commandHandler;
    private JDA jda;

    public VellumBot() {
        this.botProperties = new BotProperties();
        this.botProperties.load();

        this.commandProperties = new CommandProperties();
        this.commandProperties.load();

        this.commandHandler = new CommandHandler('/');
    }

    public void registerCommands() {
        this.commandHandler.registerCommand(new CalendarCommand());
    }

    public void registerListeners() {
        this.jda.addEventListener(new CommandListener(this.commandHandler));
        this.jda.addEventListener(new GuildListener(this.commandHandler));
    }

    public void launch(String[] args) {
        this.jda = JDABuilder
                .createDefault(botProperties.getToken())
                .build();
        this.registerCommands();
        this.registerListeners();
    }

    public CommandProperties getCommandProperties() {
        return commandProperties;
    }

    public JDA getJda() {
        return jda;
    }
}
