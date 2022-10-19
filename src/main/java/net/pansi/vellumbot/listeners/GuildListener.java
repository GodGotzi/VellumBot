package net.pansi.vellumbot.listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.pansi.vellumbot.command.Command;
import net.pansi.vellumbot.command.CommandHandler;

import java.util.stream.Collectors;

public class GuildListener extends ListenerAdapter {

    private final CommandHandler commandHandler;

    public GuildListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
                commandHandler
                        .getCommands()
                        .stream()
                        .map(Command::getCommandData)
                        .collect(Collectors.toList())
        ).queue();
    }
}
