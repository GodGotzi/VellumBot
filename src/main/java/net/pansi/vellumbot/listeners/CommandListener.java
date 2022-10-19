package net.pansi.vellumbot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.pansi.vellumbot.command.CommandHandler;

public class CommandListener extends ListenerAdapter {

    private final CommandHandler commandHandler;

    public CommandListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String msg = event.getCommandString();
        if (commandHandler.isCommand(msg)) {
            this.commandHandler.executeCommand(msg, event);
        }
    }
}
