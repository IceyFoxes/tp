package seedu.address.security.util;

/**
 * Utility methods for password validation and security requirements.
 * This class provides static methods to ensure passwords meet application policies.
 * Note: Leading, trailing, and internal whitespace are all considered invalid.
 */
public class PasswordUtil {

    public static final String MESSAGE_EMPTY = "Password cannot be empty!";
    public static final String MESSAGE_NO_SPACES = "Password must not contain spaces!";
    public static final String MESSAGE_ONLY_ASCII = "Password must only have "
            + "alphanumeric characters and symbols!";

    /**
     * Checks if the password is valid.
     * A valid password must not be empty (after trimming) and must contain no whitespace.
     *
     * @param password The password string to validate.
     * @return True if the password meets all requirements; false otherwise.
     */
    public static boolean isValidPassword(String password) {
        return getValidationError(password) == null;
    }

    /**
     * Returns a specific error message describing why a password is invalid.
     * This method checks for empty content (ignoring whitespace-only entries) and
     * strictly forbids any whitespace characters within the string.
     *
     * @param password The password string to evaluate.
     * @return A {@code String} constant representing the error, or null if the password is valid.
     */
    public static String getValidationError(String password) {
        // Handles null, empty strings and strings consisting only of whitespace/tabs
        if (password == null || password.trim().isEmpty()) {
            return MESSAGE_EMPTY;
        }

        // Checks for any whitespace (leading, trailing, or internal)
        if (password.matches(".*\\s.*")) {
            return MESSAGE_NO_SPACES;
        }

        // Check for ASCII alphanumeric and symbols
        if (!password.matches("^[\\x20-\\x7E]*$")) {
            return MESSAGE_ONLY_ASCII;
        }

        return null;
    }
}
