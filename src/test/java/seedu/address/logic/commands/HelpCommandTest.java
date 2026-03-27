package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_helpLockedMode_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.GENERAL_MANUAL_LOCKED);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpUnlockedMode_success() throws Exception {
        CommandResult commandResult = new HelpCommand().execute(new CommandContext(model, AppMode.UNLOCKED));

        assertTrue(commandResult.getFeedbackToUser().contains("COMMANDS:"));
        assertTrue(commandResult.getFeedbackToUser().contains("lock"));
        assertTrue(commandResult.getFeedbackToUser().contains("unlock"));
        assertTrue(commandResult.getFeedbackToUser().contains("setup"));
    }

    @Test
    public void execute_helpSpecificCommand_success() throws Exception {
        CommandResult commandResult = new HelpCommand("add")
                .execute(new CommandContext(model, AppMode.LOCKED));

        assertTrue(commandResult.getFeedbackToUser().contains("PURPOSE: Add a new contact."));
        assertTrue(commandResult.getFeedbackToUser().contains("USAGE:"));
        assertTrue(commandResult.getFeedbackToUser().contains("EXAMPLE:"));
    }

    @Test
    public void execute_unknownManual_throwsCommandException() {
        assertThrows(CommandException.class,
                String.format(HelpCommand.MESSAGE_UNKNOWN_MANUAL, "unknown"), ()
                -> new HelpCommand("unknown").execute(new CommandContext(model, AppMode.LOCKED)));
    }

    @Test
    public void execute_hiddenManualInLockedMode_throwsCommandException() {
        assertThrows(CommandException.class,
                String.format(HelpCommand.MESSAGE_UNKNOWN_MANUAL, "unlock"), ()
                -> new HelpCommand("unlock").execute(new CommandContext(model, AppMode.LOCKED)));
    }

    @Test
    public void execute_hiddenManualInUnlockedMode_success() throws Exception {
        CommandResult commandResult = new HelpCommand("unlock")
                .execute(new CommandContext(model, AppMode.UNLOCKED));

        assertTrue(commandResult.getFeedbackToUser().contains("PURPOSE: Switch to unlocked mode using your"
                + " password."));
    }
}
