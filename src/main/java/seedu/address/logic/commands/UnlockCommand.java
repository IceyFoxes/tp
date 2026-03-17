package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Switches the app to the Unlocked interface.
 */

public class UnlockCommand extends Command {
    public static final String COMMAND_WORD = "unlock";
    public static final String MESSAGE_SUCCESS = "Switched to Unlocked Interface.";
    public static final String MESSAGE_ALREADY_UNLOCKED = "The application is already unlocked.";

    private final String providedPassword;
    private final AppMode currentMode;

    public UnlockCommand(String password, AppMode currentMode) {
        this.providedPassword = password;
        this.currentMode = currentMode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Check if its unlocked
        if (currentMode == AppMode.UNLOCKED) {
            throw new CommandException(MESSAGE_ALREADY_UNLOCKED);
        }

        // Don't reveal that command exists
        if (!model.getAddressBookPassword().equals(providedPassword)) {
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, AppMode.UNLOCKED);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof UnlockCommand
                && providedPassword.equals(((UnlockCommand) other).providedPassword));
    }
}
