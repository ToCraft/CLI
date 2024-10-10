package dev.tocraft.crafted.cli.test;

import dev.tocraft.crafted.cli.CommandLine;
import dev.tocraft.crafted.cli.Option;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestCmdLine {
    private static final String EXPECTED_HELP =
            "usage: java -jar program.jar -i <arg> -o [-s <arg>] [-d]\n" +
            "Magic Tool doing something special.\n" +
            "-i,--input <arg>       Input file\n" +
            "-o,--output            Output if specified\n" +
            "-s,--second <arg>      Define a second input\n" +
            "-d,--directory,--dir   Do it in a directory\n" +
            "Copyright (c) 2024 To_Craft. Licensed under the Crafted License 1.0\n";

    private static CommandLine getTestCmdLine(boolean forceArgInput) {
        Option input = new Option("-i", true, true, "Input file", "--input");
        Option output = new Option("-o", true, false, "Output if specified", "--output");
        Option second = new Option("-s", false, true, "Define a second input", "--second");
        Option inDir = new Option("-d", false, false, "Do it in a directory", "--directory","--dir");
        return new CommandLine("java -jar program.jar", "Magic Tool doing something special.", "Copyright (c) 2024 To_Craft. Licensed under the Crafted License 1.0", forceArgInput, input, output, second, inDir);
    }

    @Test
    public void testHelpPage() {
        CommandLine cmdLine = getTestCmdLine(false);
        String helpPage = cmdLine.getHelpPage();

        Assert.assertEquals(
                EXPECTED_HELP,
                helpPage
        );
    }

    @Test
    public void testArgParsing() {
        // should work
        Map<Option, String> output = parseArg("-d","-i","input.txt","--output","-s","second.txt");
        Assert.assertNotNull(output);
        output = parseArg("-i","input.txt","--output","-s","second.txt");
        Assert.assertNotNull(output);
        output = parseArg("-s","second.txt","-i","input.txt","--output");
        Assert.assertNotNull(output);
        // should fail
        output = parseArg("-s","-i","input.txt");
        Assert.assertNull(output);
        output = parseArg("-s","second.txt","input.txt","--output");
        Assert.assertNull(output);
        output = parseArg("-i","--output","-s","second.txt");
        Assert.assertNull(output);
        // test strange things
        output = parseArg(true, "-i","--output","-s","second.txt","-o");
        Assert.assertNotNull(output);
    }

    private static Map<Option, String> parseArg(String... input) {
        return parseArg(false, input);
    }
    private static Map<Option, String> parseArg(boolean forceArgInput, String... input) {
        CommandLine cmdLine = getTestCmdLine(forceArgInput);
        return cmdLine.parseArgs(helpPage -> {}, input);
    }
}
