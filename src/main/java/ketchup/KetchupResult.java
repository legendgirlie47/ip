package ketchup;

/**
 * Represents the result of processing a user command in the Ketchup application.
 * Encapsulates the response message to be displayed and whether the application
 * should terminate.
 */
public class KetchupResult {

    /** Message to be displayed to the user. */
    private String response;

    /** Indicates whether the application should exit after this result. */
    private boolean shouldExit;

    /**
     * Constructs a KetchupResult with the given response message and exit flag.
     *
     * @param response   the message to be shown to the user
     * @param shouldExit true if the application should terminate, false otherwise
     */
    public KetchupResult(String response, boolean shouldExit) {
        this.response = response;
        this.shouldExit = shouldExit;
    }

    /**
     * Returns the response message associated with this result.
     *
     * @return the user-facing response message
     */
    public String getResponse() {
        return this.response;
    }

    /**
     * Returns whether the application should exit.
     *
     * @return true if the application should terminate, false otherwise
     */
    public boolean isShouldExit() {
        return this.shouldExit;
    }
}
