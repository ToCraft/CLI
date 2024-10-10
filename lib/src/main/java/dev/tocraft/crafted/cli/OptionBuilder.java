package dev.tocraft.crafted.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class OptionBuilder {
    private String abbreviation;
    private boolean required;
    private boolean takesInput;
    private String description;
    private final List<String> aliases = new ArrayList<>();

    public OptionBuilder setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public OptionBuilder setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public OptionBuilder setTakesInput(boolean takesInput) {
        this.takesInput = takesInput;
        return this;
    }

    public OptionBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public OptionBuilder addAliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    public OptionBuilder addAlias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    public Option create() {
        return new Option(abbreviation, required, takesInput, description, aliases.toArray(new String[0]));
    }
}