package utils;

public enum ConsoleColors {

    RESET("\u001B[30"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),

    BRIGHT_RED("\u001B[91m"),
    BRIGHT_GREEN("\u001B[92m"),
    BRIGHT_YELLOW("\u001B[93m"),
    BRIGHT_BLUE("\u001B[94m"),
    BRIGHT_PURPLE("\u001B[95m"),
    BRIGHT_CYAN("\u001B[96m"),
    BRIGHT_WHITE("\u001B[97m"),

    BOLD("\u001B[1m"),
    UNDERLINE("\u001B[4m");

    private final String code;

    ConsoleColors(String code) {
        this.code = code;
    }

    public String format(String text) {
        return code + text + RESET.code;
    }

    public String getCode() { 
        return code;
    }
}
