package dev.tocraft.crafted.cli;

public class Option {
    private final String abbreviation;
    private final boolean required;
    private final boolean takesInput;
    private final String description;
    private final String[] aliases;

    public Option(String abbreviation, boolean required, boolean takesInput, String description, String... aliases) {
        this.abbreviation = abbreviation;
        this.required = required;
        this.takesInput = takesInput;
        this.description = description;
        this.aliases = aliases;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isTakesInput() {
        return takesInput;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getUseHelp(boolean detailed) {
        StringBuilder useHelp = new StringBuilder();
        if (!required && !detailed) {
            useHelp.append("[");
        }
        useHelp.append(abbreviation);

        if (detailed) {
            for (String alias : aliases) {
                useHelp.append(",").append(alias);
            }
        }
        if (takesInput) {
            useHelp.append(" <arg>");
        }
        if (!required && !detailed) {
            useHelp.append("]");
        }

        return useHelp.toString();
    }
}
