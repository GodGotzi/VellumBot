package net.pansi.vellumbot.resource;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Set;

public class CommandProperties extends VellumProperties {

    private Set<CommandData> commands;

    public CommandProperties() {
        super("command.properties");
    }
}
