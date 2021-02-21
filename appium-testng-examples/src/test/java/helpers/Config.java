package helpers;

public class Config {
    public static final String region = System.getProperty("region", "eu");
    public static final String host = System.getProperty("host", "saucelabs");
    public static final String rdc = System.getProperty("rdc", "true");

}
