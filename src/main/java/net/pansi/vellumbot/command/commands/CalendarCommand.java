package net.pansi.vellumbot.command.commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.pansi.vellumbot.command.Command;

public class CalendarCommand extends Command {

    public CalendarCommand() {
        super("calendar", Commands.slash("calendar", "Convert Vellum to Reality Calendar!")); //idk

        build();
    }

    private void build() {

        //TODO
        setNativeAction(commandContext -> commandContext.event().reply("Hey it works").queue());
    }
}
