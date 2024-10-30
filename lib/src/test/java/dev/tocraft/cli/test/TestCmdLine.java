package dev.tocraft.cli.test;

import dev.tocraft.cli.CommandLine;
import dev.tocraft.cli.CmdLineBuilder;
import dev.tocraft.cli.Option;
import dev.tocraft.cli.OptionBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestCmdLine {
    private static final String EXPECTED_HELP =
            "usage: java -jar program.jar -i <arg> -o [-s <arg>] [-d] [-b <arg>]\n" +
            "Magic Tool doing something special.\n" +
            "-i,--input <arg>       Input file\n" +
            "-o,--output            Output if specified\n" +
            "-s,--second <arg>      Define a second input\n" +
            "-d,--directory,--dir   Do it in a directory\n" +
            "-b <arg>               Do it vise-versa\n" +
            "Copyright (c) 2024 To_Craft. Licensed under the Crafted License 1.0\n";

    private static CommandLine getTestCmdLine(boolean forceArgInput) {
        Option input = new OptionBuilder().setAbbreviation("-i").setRequired(true).setTakesInput(true).setDescription("Input file").addAliases("--input").create();
        Option output = new OptionBuilder().setAbbreviation("-o").setRequired(true).setTakesInput(false).setDescription("Output if specified").addAliases("--output").create();
        Option second = new OptionBuilder().setAbbreviation("-s").setRequired(false).setTakesInput(true).setDescription("Define a second input").addAliases("--second").create();
        Option inDir = new OptionBuilder().setAbbreviation("-d").setRequired(false).setTakesInput(false).setDescription("Do it in a directory").addAliases("--directory", "--dir").create();
        Option back = new OptionBuilder().setAbbreviation("-b").setRequired(false).setTakesInput(true).setDescription("Do it vise-versa").create();
        return new CmdLineBuilder().setCmdBase("java -jar program.jar").setHeader("Magic Tool doing something special.").setFooter("Copyright (c) 2024 To_Craft. Licensed under the Crafted License 1.0").setForceArgInput(forceArgInput).addOptions(input, output, second, inDir, back).create();
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
        output = parseArg("-i","input.txt","--output","-s","second.txt","-b","back.txt");
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
        return cmdLine.parseArgs(input);
    }
}
