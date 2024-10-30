package dev.tocraft.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class CmdLineBuilder {
    private String cmdBase;
    private String header;
    private String footer;
    private boolean forceArgInput = false;
    private final List<Option> options = new ArrayList<>();

    public CmdLineBuilder setCmdBase(String cmdBase) {
        this.cmdBase = cmdBase;
        return this;
    }

    public CmdLineBuilder setHeader(String header) {
        this.header = header;
        return this;
    }

    public CmdLineBuilder setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public CmdLineBuilder setForceArgInput(boolean forceArgInput) {
        this.forceArgInput = forceArgInput;
        return this;
    }

    public CmdLineBuilder addOptions(Option... options) {
        this.options.addAll(Arrays.asList(options));
        return this;
    }

    public CmdLineBuilder addOption(Option option) {
        this.options.add(option);
        return this;
    }

    public CommandLine create() {
        return new CommandLine(cmdBase, header, footer, forceArgInput, options.toArray(new Option[0]));
    }
}