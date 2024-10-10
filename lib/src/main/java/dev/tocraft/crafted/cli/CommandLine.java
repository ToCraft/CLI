package dev.tocraft.crafted.cli;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandLine {
    private final String helpPage;
    private final Option[] options;

    public CommandLine(String cmdBase, String header, String footer, Option... options) {
        this.options = options;
        this.helpPage = makeHelpPage(cmdBase, header, footer);
    }

    public String getHelpPage() {
        return helpPage;
    }

    private String makeHelpPage(String cmdBase, String header, String footer) {
        StringBuilder help = new StringBuilder();
        String head = makeHead(cmdBase, options);
        help.append(head).append("\n");
        help.append(header).append("\n");
        String desc = makeDesc(options);
        help.append(desc);
        help.append(footer).append("\n");

        return help.toString();
    }

    private String makeDesc(Option[] options) {
        StringBuilder desc = new StringBuilder();
        Map<String, String> useToDesc = new LinkedHashMap<>();

        int intendation = 0;
        for (Option option : options) {
            String optionUse = option.getUseHelp(true);

            int i = optionUse.length();
            if (i > intendation) {
                intendation = i;
            }
            useToDesc.put(optionUse, option.getDescription());
        }

        intendation += 3;

        for (Map.Entry<String, String> entry : useToDesc.entrySet()) {
            StringBuilder use = new StringBuilder(entry.getKey());
            String optionDesc = entry.getValue();
            while (use.length() < intendation) {
                use.append(" ");
            }

            desc.append(use).append(optionDesc).append("\n");
        }

        return desc.toString();
    }

    private static String makeHead(String cmdBase, Option[] options) {
        StringBuilder head = new StringBuilder();
        head.append("usage: ");
        head.append(cmdBase);
        for (Option option : options) {
            head.append(" ").append(option.getUseHelp(false));
        }

        return head.toString();
    }
}
