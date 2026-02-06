package ketchup;

public class KetchupResult {

    private String response;
    private boolean shouldExit;

    public KetchupResult(String response, boolean shouldExit) {
        this.response = response;
        this.shouldExit = shouldExit;
    }

    public String getResponse() {
        return this.response;
    }

    public boolean isShouldExit() {
        return this.shouldExit;
    }
}
