package co.com.bdb.automation.utilities;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentValuesTask {

    private final String github = "GITHUB_ACTIONS";
    private Dotenv dotenv;

    public EnvironmentValuesTask() {
        if (System.getenv(github) == null) {
            dotenv = Dotenv.configure().load();
        }
    }

    public String getenv(String key) {
        if (System.getenv(github) == null) {
            return dotenv.get(key);
        } else {
            return System.getenv(key);
        }
    }
}
