package net.pansi.vellumbot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.internal.utils.JDALogger;
import net.pansi.vellumbot.api.command.CommandContext;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CommandHandler {
    private final static Logger LOGGER = JDALogger.getLog("command");

    private final Properties properties = new Properties();
    private final Map<String, Command> commandMap = new LinkedHashMap<>();
    private final char commandChar;

    public CommandHandler(char commandChar) {
        this.commandChar = commandChar;

        InputStream in = CommandHandler.class.getClassLoader().getResourceAsStream("command-handler.properties");

        try {
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError();
        }
    }

    /**
     * It takes a GCommand object and adds it to the commandMap HashMap
     *
     * @param command The command you want to register.
     */
    public synchronized void registerCommand(Command command) {
        commandMap.put(command.getLabel(), command);
    }

    public boolean isCommand(String line) {
        if (line.charAt(0) == commandChar || commandChar == ' ') {
            if (commandChar != ' ') line = line.substring(1);
            String[] cmdSplit = line.split(" ", 2);

            if (!commandMap.containsKey(cmdSplit[0])) {
                LOGGER.info(properties.getProperty("commandNotExists"), cmdSplit[0]);
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * It takes a string, splits it into a command and arguments, and then executes the command with the arguments
     *
     * @param line The line of text that was sent to the bot.
     */
    public synchronized void executeCommand(String line, SlashCommandInteractionEvent event) {
        if (commandChar != ' ') line = line.substring(1);
        String[] cmdSplit = line.split(" ", 2);
        if (cmdSplit.length < 2) executeCommand(cmdSplit[0], new String[]{}, event);
        else executeCommand(cmdSplit[0], cmdSplit[1].split(" "), event);
    }

    /**
     * It executes a command
     *
     * @param cmd The command name
     * @param args The arguments of the command.
     */
    public void executeCommand(String cmd, String[] args, SlashCommandInteractionEvent event) {
        commandMap.get(cmd).execute(new CommandContext(cmd, args, properties, event));
    }

    protected static Logger getCommandLogger() {
        return LOGGER;
    }

    public Collection<Command> getCommands() {
        return commandMap.values();
    }

    public Properties getProperties() {
        return properties;
    }
}
