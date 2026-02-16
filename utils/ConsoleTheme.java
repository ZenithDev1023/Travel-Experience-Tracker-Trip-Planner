package utils;

public final class ConsoleTheme {
    private ConsoleTheme() {}

    public static final String TITLE = 
    ConsoleColors.UNDERLINE.getCode() +
    ConsoleColors.BRIGHT_CYAN.getCode();
    public static final ConsoleColors SUCCESS = ConsoleColors.GREEN;
    public static final ConsoleColors ERROR = ConsoleColors.BRIGHT_RED;
    public static final ConsoleColors WARNING = ConsoleColors.YELLOW;
    public static final ConsoleColors INFO = ConsoleColors.BRIGHT_BLUE;
    public static final ConsoleColors INPUT = ConsoleColors.CYAN;

    public static final String RESET = ConsoleColors.RESET.getCode();

    public static final String format(String style, String text) {
        return style + text + RESET;
    }
}