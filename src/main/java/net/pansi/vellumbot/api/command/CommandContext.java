package net.pansi.vellumbot.api.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Properties;

public record CommandContext(String cmd, String[] args, Properties properties, SlashCommandInteractionEvent event) { }