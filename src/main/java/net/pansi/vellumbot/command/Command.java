package net.pansi.vellumbot.command;


import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.pansi.vellumbot.api.command.CommandAction;
import net.pansi.vellumbot.api.command.CommandContext;
import org.slf4j.Logger;

import java.util.*;

public class Command {

    private final SortedSet<CommandArgument> arguments = new TreeSet<>();
    private final String label;
    private final CommandData commandData;

    private CommandAction nativeAction = null;

    public Command(String label, CommandData commandData) {
        this.label = label;
        this.commandData = commandData;
    }

    public Command(String label, CommandData commandData, CommandAction nativeAction) {
        this.label = label;
        this.commandData = commandData;
        this.nativeAction = nativeAction;
    }

    /**
     * If the command has no arguments, run the nullAction. If the command has arguments, check if the first argument is
     * valid. If it is, check if the next argument is valid. If it is, check if the next argument is valid. If it is, run
     * the command action
     *
     * @param gotziCommandContext The context of the command.
     */
    public void execute(CommandContext gotziCommandContext) {
        if (gotziCommandContext.args().length == 0 && nativeAction != null) {
            nativeAction.run(gotziCommandContext);
            return;
        }

        CommandArgument gArgument =
                arguments.stream().filter(arg -> filterGotziArguments(arg, gotziCommandContext))
                        .findFirst().orElse(null);

        if (gArgument == null) {
            getCommandLogger().info(gotziCommandContext.properties().getProperty("onFalseSyntax"),
                    gotziCommandContext.cmd());
            return;
        } else if (gotziCommandContext.args().length == 1) {
            gArgument.getCommandAction().run(gotziCommandContext);
        }

        for (int i = 0; i < gotziCommandContext.args().length-1; i++) {
            gArgument = getNextArgument(gArgument, gotziCommandContext);
            if (gArgument == null) {
                getCommandLogger().info(gotziCommandContext.properties().getProperty("onFalseSyntax"),
                        gotziCommandContext.cmd());
                return;
            }

            if (gArgument.getIndex() == (gotziCommandContext.args().length-1) && gArgument.getCommandAction() != null) {
                gArgument.getCommandAction().run(gotziCommandContext);
            }
        }
    }

    public List<CharSequence> completeArgument(String[] prefixArgs, String prefix) {
        List<CharSequence> candidates = new LinkedList<>();
        SortedSet<CommandArgument> nextArguments = arguments;

        for (String argPrefix : prefixArgs) {
            CommandArgument commandArgument = nextArguments.stream()
                    .filter(arg ->
                            arg.getLabel().equals(argPrefix) || arg instanceof CommandArgumentValue
                    ).findFirst().orElse(null);
            if (commandArgument == null) return candidates;

            nextArguments = commandArgument.getSubCommands();
        }

        for (CommandArgument argument : nextArguments) {
            if (argument.getLabel().startsWith(prefix)) {
                StringBuilder stringBuilder = new StringBuilder();

                for (String prefixArg : prefixArgs)
                    stringBuilder.append(prefixArg).append(" ");

                stringBuilder.append(argument.getLabel());

                candidates.add(stringBuilder.toString().trim());
            }
        }

        return candidates;
    }

    /**
     * It returns the next argument in the command chain
     *
     * @param gArgument The current argument that is being processed.
     * @param gotziCommandContext The context of the command.
     * @return The next argument in the command.
     */
    private CommandArgument getNextArgument(CommandArgument gArgument, CommandContext gotziCommandContext) {
        if (gArgument.getSubCommands() == null) return null;
        return gArgument.getSubCommands().stream().filter(arg ->
                filterGotziArguments(arg, gotziCommandContext)
        ).findFirst().orElse(null);
    }

    /**
     * "If the argument's index is less than the number of arguments in the command context, and the argument's label is
     * equal to the argument at the index in the command context, or the argument is a value argument, return true."
     *
     * The first part of the function checks if the argument's index is less than the number of arguments in the command
     * context. This is to make sure that the argument is not out of bounds
     *
     * @param gArgument The argument that is being checked.
     * @param gotziCommandContext The context of the command.
     * @return A boolean value.
     */
    private boolean filterGotziArguments(CommandArgument gArgument, CommandContext gotziCommandContext) {
        return gArgument.getIndex() < gotziCommandContext.args().length && (gArgument.getLabel().equals(gotziCommandContext.args()[gArgument.getIndex()]) || gArgument instanceof CommandArgumentValue);
    }

    /**
     * If the argument doesn't already exist, add it to the list
     *
     * @param gArgument The argument to add to the list of arguments.
     */
    public void addArgument(CommandArgument gArgument) {
        if (arguments.stream().noneMatch(argument -> argument.getLabel().equals(gArgument.getLabel())))
            arguments.add(gArgument);
    }

    public String getLabel() {
        return label;
    }

    public SortedSet<CommandArgument> getArguments() {
        return Collections.unmodifiableSortedSet(arguments);
    }

    public void setNativeAction(CommandAction nativeAction) {
        this.nativeAction = nativeAction;
    }

    public Logger getCommandLogger() {
        return CommandHandler.getCommandLogger();
    }

    public CommandData getCommandData() {
        return commandData;
    }
}