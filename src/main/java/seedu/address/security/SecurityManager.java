package seedu.address.security;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.Logic;
import seedu.address.security.util.PasswordUtil;
import seedu.address.ui.PasswordWindow;

/**
 * Manages the security and authentication state of the application.
 * The {@code SecurityManager} handles the lifecycle of application access,
 * including initial password setup and persistent authentication state.
 */
public class SecurityManager implements Security {

    /** The path where the application password is stored. */
    private static final Path PASSWORD_FILE_PATH = Paths.get("data", "password.txt");

    private static final Logger logger = LogsCenter.getLogger(SecurityManager.class);

    private final Logic logic;

    /**
     * Constructs a {@code SecurityManager} with the given {@code Logic} component.
     *
     * @param logic The logic component used to retrieve application settings for the UI.
     */
    public SecurityManager(Logic logic) {
        this.logic = logic;
    }

    /**
     * Checks if the application is authenticated.
     * If the password file exists on the local filesystem, authentication is considered successful.
     * Otherwise, it initiates the first-time setup process.
     *
     * @return true if authenticated or setup is successful; false if setup is cancelled.
     */
    @Override
    public boolean isAuthenticated() {
        if (FileUtil.isFileExists(PASSWORD_FILE_PATH)) {
            logger.info("Authentication successful: Password file detected.");
            return true;
        }
        logger.info("Authentication required: Starting first-time password setup.");
        return showPasswordSetupDialog();
    }

    /**
     * Orchestrates the password setup UI and persists the user's input to disk.
     * @return true if a password was successfully set and saved; false if the user aborted the process.
     */
    private boolean showPasswordSetupDialog() {
        PasswordWindow passwordWindow = new PasswordWindow(logic.getGuiSettings());
        passwordWindow.show();

        Optional<String> result = passwordWindow.getPassword();
        if (result.isPresent()) {
            try {
                String hashedPassword = PasswordUtil.hashPassword(result.get());

                FileUtil.createParentDirsOfFile(PASSWORD_FILE_PATH);
                FileUtil.writeToFile(PASSWORD_FILE_PATH, hashedPassword);

                logger.info("Security setup complete: Password saved.");
                return true;
            } catch (IOException e) {
                logger.severe("Security setup failed: Could not save password file. " + e.getMessage());
            }
        } else {
            logger.warning("Security setup aborted: User closed the setup window.");
        }
        return false;
    }
}
