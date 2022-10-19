package net.pansi.vellumbot.command;

import net.pansi.vellumbot.api.command.CommandAction;

public class CommandArgumentValue extends CommandArgument {

    public CommandArgumentValue(int index, CommandArgument[] subArguments, CommandAction commandAction) {
        super("", index, subArguments, commandAction);
    }

    public CommandArgumentValue(int index, CommandAction commandAction) {
        super("", index, commandAction);
    }
}
