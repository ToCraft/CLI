package dev.tocraft.crafted.cli;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class CommandLine {
    private final String helpPage;
    private final boolean forceArgInput;
    private final Option[] options;

    public CommandLine(String cmdBase, String header, String footer, boolean forceArgInput, Option... options) {
        this.options = options;
        this.forceArgInput = forceArgInput;
        this.helpPage = makeHelpPage(cmdBase, header, footer);
    }

    @SuppressWarnings("unused")
    @Nullable
    public Map<Option, String> parseArgs(String[] args) {
        return parseArgs(System.out::println, args);
    }

    @Nullable
    public Map<Option, String> parseArgs(Consumer<String> printHelp, String[] args) {
        List<String> parsedArgs = new ArrayList<>();
        Map<Option, String> optionInput = new HashMap<>();

        try {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];

                if (!parsedArgs.contains(arg)) {
                    for (Option option : options) {
                        if (option.isArg(arg)) {
                            parsedArgs.add(arg);
                            if (option.takesInput()) {
                                String input = args[i + 1];
                                if (forceArgInput || Arrays.stream(options).noneMatch(o -> o.isArg(input))) {
                                    optionInput.put(option, input);
                                    parsedArgs.add(input);
                                }
                            } else {
                                optionInput.put(option, null);
                            }
                            break;
                        }
                    }
                }
            }

            if (new ArrayList<>(Arrays.asList(args)).retainAll(parsedArgs)) {
                List<String> list = new ArrayList<>(Arrays.asList(args));
                list.removeIf(parsedArgs::contains);

                throw new Exception(list.toString());
            }

            for (Option option : options) {
                if ((option.isRequired() && !optionInput.containsKey(option)) || (option.takesInput() && optionInput.get(option) == null)) {
                    throw new Exception();
                }
            }

        } catch (Exception e) {
            printHelp.accept(helpPage);
            return null;
        }

        return optionInput;
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

        int indentation = 0;
        for (Option option : options) {
            String optionUse = option.getUseHelp(true);

            int i = optionUse.length();
            if (i > indentation) {
                indentation = i;
            }
            useToDesc.put(optionUse, option.getDescription());
        }

        indentation += 3;

        for (Map.Entry<String, String> entry : useToDesc.entrySet()) {
            StringBuilder use = new StringBuilder(entry.getKey());
            String optionDesc = entry.getValue();
            while (use.length() < indentation) {
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
