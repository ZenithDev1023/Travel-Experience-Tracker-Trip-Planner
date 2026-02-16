package enums;

public enum MemoryVividness {
    VAGUE("Vague", "Barely remember"),
    CLEAR("CLear", "Remember well"),
    VIVID("Vivid", "Detailed memories"),
    LIFE_CHANGING("Life Changing", "Profound impact"),
    DOCUMENTED("Documented", "Photos/journals help");

    private final String type;
    private final String description;

    MemoryVividness(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static MemoryVividness fromType(String name) {
        for (MemoryVividness m : values()) {
            if (m.type.equalsIgnoreCase(name)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unknown memory type: " + name);
    }

    public String getType() { return type; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return type + " - " + description;
    }
}
