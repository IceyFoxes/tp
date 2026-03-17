package seedu.address.logic.parser;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnlockCommand object.
 * This parser is designed to be discreet, throwing a generic unknown command error
 * if arguments are missing to avoid leaking information about the security mechanism.
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments and the current {@code AppMode}
     * to create an UnlockCommand.
     */
    public UnlockCommand parse(String args, AppMode mode) throws ParseException {
        String trimmedArgs = args.trim();

        return new UnlockCommand(trimmedArgs, mode);
    }

    /**
     * This method should not be called
     */
    @Override
    public UnlockCommand parse(String args) throws ParseException {
        throw new UnsupportedOperationException("UnlockCommand requires AppMode context.");
    }
}