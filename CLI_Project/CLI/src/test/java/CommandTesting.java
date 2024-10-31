import org.example.Terminal;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CommandTesting
{

    private Terminal terminal = new Terminal();

    @Test
    public void TestEcho() throws IOException
    {
        String t =  terminal.GoToEcho(new String[]{"Hello","World"});
        Assert.assertEquals("Hello World",t);
    }
    @Test
    public void TestEchoWithOverwrite() throws IOException {
        Path outputFile = Terminal.currentDirectory.resolve("echoOutput.txt");

        terminal.GoToEcho(new String[]{"Hello", "World", ">", outputFile.toString()});

        String content = Files.readString(outputFile);
        Assert.assertEquals("Hello World", content.trim());
    }
    @Test
    public void TestEchoWithAppend() throws IOException {
        Path outputFile = Terminal.currentDirectory.resolve("echoOutput.txt");
        Files.writeString(outputFile, "Initial Content\n");
        terminal.GoToEcho(new String[]{"Appended Text", ">>", outputFile.toString()});
        List<String> lines = Files.readAllLines(outputFile);
        Assert.assertEquals("Initial Content", lines.get(0).trim());
        Assert.assertEquals("Appended Text", lines.get(1).trim());
    }

    @Test
    public void TestMkdir() throws IOException
    {
        Terminal.mkdir(new String[]{"makeNewDirectory"});
        Path pathFromArg = Terminal.currentDirectory.resolve("makeNewDirectory");
        Assert.assertTrue(Files.isDirectory(pathFromArg));
    }
    @Test
    public void TestMv() throws IOException {
        Path source = Files.createFile(Terminal.currentDirectory.resolve("sourceFile.txt"));
        Path dest = Terminal.currentDirectory.resolve("destinationFile.txt");
        Terminal.mv(new String[]{source.toString(), dest.toString()});
        Assert.assertFalse(Files.exists(source));
        Assert.assertTrue(Files.exists(dest));
    }
    @Test
    public void TestRm() throws IOException {
        Path file = Files.createFile(Terminal.currentDirectory.resolve("fileToRemove.txt"));
        Terminal.rm(new String[]{file.toString()});
        Assert.assertFalse(Files.exists(file));
    }
    @Test
    public void TestTouch() {
        String[] args = {Terminal.currentDirectory.resolve("newFile.txt").toString()};
        Terminal.touch(args);
        Assert.assertTrue(Files.exists(Terminal.currentDirectory.resolve("newFile.txt")));
    }
    @Test
    public void TestPwd() {
        Assert.assertEquals(Terminal.currentDirectory.toAbsolutePath().toString(), Terminal.pwd());

    }
    @Test
    public void TestRmdir() throws IOException {
        Path dir = Files.createDirectory(Terminal.currentDirectory.resolve("toRemove"));
        Terminal.rmdir(new String[]{dir.toString()});
        Assert.assertFalse(Files.exists(dir));
    }
    @Test
    public void TestCd() {
        Path newDir = Terminal.currentDirectory.resolve("E:\\");
        String[] args = {Terminal.currentDirectory.toString()};
        Terminal.cd(args);
        Assert.assertEquals(Terminal.currentDirectory.toAbsolutePath(), Terminal.getCurrentDirectory());
    }

    @Test
    public void TestLs() throws IOException {
        Path file = Files.createFile(Terminal.currentDirectory.resolve("NewDirectory"));
        Terminal.ls(new String[]{Terminal.currentDirectory.toString()});
        Assert.assertTrue(Files.exists(file));
    }

    @Test
    public void TestLsWithAppend() throws IOException {
        Path outputFile = Terminal.currentDirectory.resolve("lsOutput.txt");
        Files.writeString(outputFile, "Initial Directory Content\n");

        Files.createFile(Terminal.currentDirectory.resolve("testFileForLsAppend.txt"));
        Terminal.ls(new String[]{">>", outputFile.toString()});
        List<String> lines = Files.readAllLines(outputFile);
        Assert.assertEquals("Initial Directory Content", lines.get(0).trim());
        Assert.assertTrue(lines.stream().anyMatch(line -> line.contains("testFileForLsAppend.txt")));
    }
    @Test
    public void TestLsWithOverwrite() throws IOException {
        Path outputFile = Terminal.currentDirectory.resolve("lsOutput.txt");
        Files.createFile(Terminal.currentDirectory.resolve("testFileForLs.txt"));
        Terminal.ls(new String[]{">", outputFile.toString()});
        String content = Files.readString(outputFile);
        Assert.assertTrue(content.contains("testFileForLs.txt"));
    }
    @Test
    public void TestCat() throws IOException {
        Path file = Files.createFile(Terminal.currentDirectory.resolve("fileToRead.txt"));
        Files.write(file, Arrays.asList("Hello World"));
        Terminal.cat(new String[]{file.toString()});
    }
}









