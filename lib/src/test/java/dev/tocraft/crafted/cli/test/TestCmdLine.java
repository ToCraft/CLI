package dev.tocraft.crafted.cli.test;

import dev.tocraft.crafted.cli.CommandLine;
import dev.tocraft.crafted.cli.Option;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("ExtractMethodRecommender")
public class TestCmdLine {
    @Test
    public void testHelpPage() {
        Option input = new Option("-i", true, true, "Input file", "--input");
        Option output = new Option("-o", true, false, "Output if specified", "--output");
        Option second = new Option("-s", false, true, "Define a second input", "--second");
        CommandLine cmdLine = new CommandLine("java -jar program.jar", "Magic Tool doing something special.", "Copyright (c) 2024 To_Craft. Licensed under the Crafted License 1.0",  input, output, second);
        String helpPage = cmdLine.getHelpPage();

        Assert.assertEquals(
                "usage: java -jar program.jar -i <arg> -o [-s <arg>]\n" +
                "Magic Tool doing something special.\n" +
                "-i,--input <arg>    Input file\n" +
                "-o,--output         Output if specified\n" +
                "-s,--second <arg>   Define a second input\n" +
                "Copyright (c) 2024 To_Craft. Licensed under the Crafted License 1.0\n",
                helpPage
        );
    }
}
