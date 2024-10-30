package dev.tocraft.cli;

import java.util.Objects;

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

    public boolean isArg(String arg) {
        if (Objects.equals(abbreviation, arg)) {
            return true;
        }
        for (String alias : aliases) {
            if (Objects.equals(alias, arg)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean takesInput() {
        return takesInput;
    }

    public String getDescription() {
        return description;
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

    @Override
    public String toString() {
        return abbreviation;
    }
}
