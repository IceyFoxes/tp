package seedu.address.security;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.security.util.PasswordUtil;

/**
 * Manages the security and authentication state of the application.
 * The {@code SecurityManager} handles the lifecycle of application access.
 * It coordinates between the UI components and the data model via {@code Logic}.
 */
public class SecurityManager implements Security {

    private static final Logger logger = LogsCenter.getLogger(SecurityManager.class);

    private final Logic logic;

    /**
     * Constructs a {@code SecurityManager} with custom dependencies.
     *
     * @param logic The logic component.
     */
    public SecurityManager(Logic logic) {
        this.logic = logic;
    }

    /**
     * Checks if the application has a valid password stored and is ready for use.
     *
     * @return True if a valid password exists in storage; false otherwise.
     */
    @Override
    public boolean isAuthenticated() {
        String storedPassword = logic.getAddressBookPassword();

        if (storedPassword == null) {
            logger.info("No password found in storage. Setup required.");
            return false;
        }

        if (PasswordUtil.isValidPassword(storedPassword)) {
            return true;
        }

        logger.warning("Stored password is invalid or corrupted. Setup required.");
        return false;
    }

    /**
     * Validates and saves the provided password to the model via logic.
     *
     * @param password The plain text password to be saved.
     * @throws IllegalArgumentException if the password format is invalid.
     */
    @Override
    public void savePassword(String password) {
        if (!PasswordUtil.isValidPassword(password)) {
            logger.warning("Attempted to save an invalid password.");
            throw new IllegalArgumentException("Invalid password format.");
        }

        try {
            logic.setAddressBookPassword(password);
            logic.saveAddressBook();
            logger.info("Password saved to data file.");

        } catch (IOException e) {
            logger.severe("Failed to save address book after password update.");
            throw new RuntimeException("Data storage failure during setup", e);
        }
    }
}
