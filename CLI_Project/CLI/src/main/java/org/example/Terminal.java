package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.*;
import java.util.*;

public class Terminal
{
    static Parser parser = new Parser();

    //   private static Path currentDirectory = Paths.get(System.getProperty("user.home"));
    public static Path currentDirectory = Paths.get("").toAbsolutePath();

    static List<Path> directoryHistory = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        directoryHistory.addLast(currentDirectory);
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String userName = System.getProperty("user.name");

            String systemName = InetAddress.getLocalHost().getHostName();
            // System.out.print('['+userName+'@'+systemName+" "+currentDirectory+']' +" "+ '$'+": ");
            String[] curDirName = currentDirectory.toAbsolutePath().toString().split("/");

            System.out.print('[' + userName + '@' + systemName + " " + curDirName[curDirName.length - 1] + ']' + " " + '$' + " ");
            String input = scanner.nextLine();
            parser.parse(input);

            if (!parser.parse(input))
            {
                System.out.println("Invalid command.");
                continue;
            }
            String command = parser.getCommandName();
            String[] commandArgs = parser.getArgs();
            switch (command)
            {
                case "exit":
                    System.out.println("Exiting...");
                    return;
                case "echo":
                    echo(commandArgs);
                    break;
                case "pwd":
                    pwd();
                    break;
                case "cd":
                    cd(commandArgs);
                    break;
                case "ls":
                    ls(commandArgs);
                    break;
                case "mkdir":
                    mkdir(commandArgs);
                    break;
                case "rmdir":
                    rmdir(commandArgs);
                    break;
                case "touch":
                    touch(commandArgs);
                    break;
                case "rm":
                    rm(commandArgs);
                    break;
                case "cat":
                    cat(commandArgs);
                    break;
                case "mv":
                    mv(commandArgs);
                    break;
                case "Help":
                    Help();
                    break;
                default:
                {
                    System.out.println("Invalid Command");
                    break;
                }





            }




        }

    }

    public static Path getCurrentDirectory() {
        return currentDirectory.toAbsolutePath();
    }

    public String GoToEcho(String[] args)
    {
        return echo(args);
    }
    public static String echo(String[] args) {
        StringBuilder s = new StringBuilder();
        boolean isRedirect = false;
        boolean append = false;
        String fileName = null;

        // Check if redirection operators are present in args
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(">") || args[i].equals(">>")) {
                isRedirect = true;
                append = args[i].equals(">>");
                if (i + 1 < args.length) {
                    fileName = args[i + 1];
                } else {
                    System.out.println("Error: No file specified for redirection.");
                    return null;
                }
                break;
            } else {
                s.append(args[i]).append(" ");
            }
        }

        // If redirection is specified, write to the file
        if (isRedirect && fileName != null) {
            Path filePath = currentDirectory.resolve(fileName);

            try {
                // Write to the file based on whether we are appending or overwriting
                if (append) {
                    Files.writeString(filePath, s.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } else {
                    Files.writeString(filePath, s.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                }
                System.out.println("Redirected output to file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            // If no redirection, print to the console
            System.out.println(s.toString().trim());
        }

        return s.toString().trim();
    }



    public static String pwd()
    {
        System.out.println(currentDirectory.toAbsolutePath());
        return currentDirectory.toAbsolutePath().toString();
    }
    public static void cd(String[] args)
    {
        if (args.length == 0)
        {
            currentDirectory = Paths.get(System.getProperty("user.home"));
            directoryHistory.addLast(currentDirectory);

        }
        else if (Objects.equals(args[0], "-")) {
            if(!directoryHistory.isEmpty())
            {
                directoryHistory.removeLast();

            }
            if(!directoryHistory.isEmpty())
            {
                currentDirectory = directoryHistory.getLast();
            }
            else {
                currentDirectory = Paths.get("").toAbsolutePath();

            }



        }
        else if(args[0].equals("."))
        {
            return;
        }

        else if (args[0].equals(".."))
        {
            Path targetDirectory = Paths.get("C:\\");
            if(currentDirectory.equals(targetDirectory))
            {
                return;
            }
            currentDirectory = currentDirectory.getParent();
            directoryHistory.addLast(currentDirectory);

        }
        else
        {
            Path newPath = currentDirectory.resolve(args[0]);
            if (Files.isDirectory(newPath))
            {
                currentDirectory = newPath;
                directoryHistory.addLast(currentDirectory);

            }
            else {
                System.out.println("Error: Not a directory.");
            }
        }
    }


//    public static void ls(String[] args) throws IOException
//    {
//
//        Path directory = currentDirectory; // Default to current directory
//        String option = "";
//
//        if (args.length > 0) {
//
//            if ((args[0].equals("-a") || args[0].equals("-r"))) { // option
//
//                option = args[0];
//
//                if (args.length > 1) // option + dir ?
//                    directory = currentDirectory.resolve(args[1]); // Resolve the directory from the second argument
//
//            } else { // only dir
//                directory = currentDirectory.resolve(args[0]);
//            }
//
//        }
//        if(option.isEmpty())
//        {
//            listDirectoryWithOutOption(directory);
//        }
//        else
//        {
//            listDirectoryWithOption(directory, option);
//
//        }
//
//
//
//    }
//
//    public static void listDirectoryWithOutOption(Path directory)
//    {
//
//        File dir = directory.toFile();
//
//        if (!dir.isDirectory())
//        {
//            System.out.println("Error: " + directory + " is not a directory or cannot be accessed.");
//            return;
//        }
//
//        String[] dirContent = dir.list();
//        if (dirContent != null)
//        {
//            for (String item : dirContent)
//            {
//                System.out.println(item);
//            }
//        }
//        else
//        {
//            System.out.println("Error: Unable to list contents of " + directory);
//        }
//    }
//
//    public static void listDirectoryWithOption(Path directory, String option) throws IOException
//    {
//        // Get the directory contents as an array of strings
//        File dir = new File(directory.toString());
//        String[] dirContent = dir.list();
//
//        if (dirContent == null)
//        {
//            System.out.println("Error: Not a directory or unable to access.");
//            return;
//        }
//
//        for (String itemName : dirContent)
//        {
//            File item = new File(directory.toString(), itemName);
//
//            if (option.equals("-a") || !item.isHidden())
//            {
//                System.out.println(item.getPath());
//            }
//
//            // Handle the '-r' option for recursive listing of directories
//            if (option.equals("-r") && item.isDirectory()) {
//                System.out.println("\nEntering directory: " + item.getPath());
//                listDirectoryWithOption(item.toPath(), "-r");
//            }
//        }
//    }

    public static void ls(String[] args) throws IOException {
        Path directory = currentDirectory;
        String option = "";
        String outputPath = null;
        boolean append = false;


        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals(">") || args[i].equals(">>"))
            {
                if (i + 1 < args.length)
                {
                    outputPath = args[i + 1];

                    append = args[i].equals(">>");
                }
                break;
            } else if (args[i].equals("-a") || args[i].equals("-r"))
            {
                option = args[i];
            }
            else
            {
                directory = currentDirectory.resolve(args[i]);
            }
        }


        if (!Files.isDirectory(directory))
        {
            System.out.println("Error: " + directory + " is not a directory or cannot be accessed.");
            return;
        }


        List<String> outputContent = option.isEmpty() ?
                listDirectoryWithoutOption(directory) :
                listDirectoryWithOption(directory, option);



        if (outputPath != null)
        {
            writeToFile(outputPath, outputContent, append);
        } else {
            outputContent.forEach(System.out::println);
        }
    }


    public static List<String> listDirectoryWithoutOption(Path directory) {
        List<String> output = new ArrayList<>();
        File dir = directory.toFile();

        if (!dir.isDirectory()) {
            output.add("Error: " + directory + " is not a directory or cannot be accessed.");
            return output;
        }

        String[] dirContent = dir.list();
        if (dirContent != null) {
            Collections.addAll(output, dirContent);
        } else {
            output.add("Error: Unable to list contents of " + directory);
        }
        return output;
    }


    public static List<String> listDirectoryWithOption(Path directory, String option) throws IOException {
        List<String> output = new ArrayList<>();
        File dir = new File(directory.toString());
        String[] dirContent = dir.list();

        if (dirContent == null) {
            output.add("Error: Not a directory or unable to access.");
            return output;
        }

        for (String itemName : dirContent) {
            File item = new File(directory.toString(), itemName);
            if (option.equals("-a") || !item.isHidden()) {
                output.add(item.getPath());
            }

            // Handle recursive option for subdirectories
            if (option.equals("-r") && item.isDirectory()) {
                output.add("\nEntering directory: " + item.getPath());
                output.addAll(listDirectoryWithOption(item.toPath(), "-r"));
            }
        }
        return output;
    }


    private static void writeToFile(String outputPath, List<String> content, boolean append) {
        Path filePath = currentDirectory.resolve(outputPath);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public    static void mkdir(String[] args)
    {
        boolean createParents = false;
        if (args.length > 0)
        {
            if("-p".equals(args[0]))
            {
                createParents = true;
                args = Arrays.copyOfRange(args, 1, args.length);
            }

        }

        for (String dirName : args)
        {
            Path newDir = currentDirectory.resolve(dirName);
            try
            {
                if (createParents)
                {
                    Files.createDirectories(newDir);
                }
                else
                {

                    Files.createDirectory(newDir);
                }
                System.out.println("Directory created: " + newDir);
            }
            catch (IOException e)
            {
                System.out.println("Failed to create directory: " + e.getMessage());
            }
        }
    }


    public   static void mv(String[] args)
    {
        if (args.length < 2)
        {
            System.out.println("Usage: mv <source1> <source2> ... <destination>");
            return;
        }

        Path destinationPath = currentDirectory.resolve(args[args.length - 1]);

        boolean isDestinationDirectory = Files.isDirectory(destinationPath);

        for (int i = 0; i < args.length - 1; i++) {
            Path sourcePath = currentDirectory.resolve(args[i]);
            if(Files.isDirectory(sourcePath))
            {
                System.out.println("can not move directory....");
                break;
            }

            try
            {

                if (!Files.exists(sourcePath))
                {
                    System.out.println("Error: Source file does not exist: " + sourcePath);
                    continue;
                }

                Path finalDestinationPath;
                if (isDestinationDirectory)
                {

                    finalDestinationPath = destinationPath.resolve(sourcePath.getFileName());
                }
                else
                {

                    finalDestinationPath = destinationPath;
                }

                Files.move(sourcePath, finalDestinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved: " + sourcePath + " to " + finalDestinationPath);
            }
            catch (IOException e)
            {

                System.out.println("Error moving file: " + e.getMessage());
            }
        }
    }


    public static void rm(String[] args)
    {
        if (args.length == 0) {
            System.out.println("Usage: rm <file/directory>...");
            return;
        }

        boolean recursive = false;

        if ("-r".equals(args[0]))
        {
            recursive = true;
            args = Arrays.copyOfRange(args, 1, args.length);
        }

        for (String arg : args)
        {

            Path pathToRemove = currentDirectory.resolve(arg);

            try {
                if (Files.isDirectory(pathToRemove))
                {
                    if (recursive)
                    {
                        deleteDirectory(pathToRemove); // Delete the directory and contents recursively
                        System.out.println("Removed directory: " + pathToRemove);
                    }
                    else if (isEmpty(pathToRemove)) {
                        Files.delete(pathToRemove); // Remove empty directory without -r option
                        System.out.println("Removed empty directory: " + pathToRemove);
                    } else {
                        System.out.println("Error: Directory not empty. Use -r option to delete recursively.");
                    }
                } else if (Files.exists(pathToRemove)) {
                    Files.delete(pathToRemove); // Delete a single file
                    System.out.println("Removed file: " + pathToRemove);
                } else {
                    System.out.println("Error: File or directory does not exist: " + pathToRemove);
                }
            } catch (IOException e) {
                System.out.println("Error removing: " + e.getMessage());
            }
        }
    }

    // Helper method to recursively delete a directory and all its contents
    public static void deleteDirectory(Path directory) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    deleteDirectory(entry);
                }
                Files.delete(entry);
            }
        }
        Files.delete(directory);
    }
    public   static boolean isEmpty(Path path) throws IOException {

        try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
            return !directory.iterator().hasNext();
        }



    }



    public static void rmdir(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: rmdir <directory>...");
            return;
        }

        for (String arg : args) {

            Path dirToRemove = currentDirectory.resolve(arg);

            try {

                if (Files.isDirectory(dirToRemove)) {

                    if (isEmpty(dirToRemove)) {
                        Files.delete(dirToRemove);
                        System.out.println("Removed empty directory: " + dirToRemove);
                    } else {
                        System.out.println("Error: Directory not empty: " + dirToRemove);
                    }
                } else {
                    System.out.println("Error: Not a directory: " + dirToRemove);
                }
            } catch (IOException e) {
                System.out.println("Error removing directory: " + e.getMessage());
            }
        }
    }






    public  static void touch(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Usage: touch <file>...");
            return;
        }

        for (String fileName : args)
        {
            Path filePath = currentDirectory.resolve(fileName);

            try
            {

                Files.createFile(filePath);
                System.out.println("File created: " + filePath);
            }
            catch (IOException e)
            {

                if (Files.exists(filePath))
                {
                    System.out.println("File already exists: " + filePath);
                }
                else
                {

                    System.out.println("Error creating file: " + e.getMessage());
                }
            }
        }
    }


    public static void cat(String[] args) {
        if (args.length == 0)
        {
            System.out.println("Usage: cat <file>...");
            return;
        }

        for (String fileName : args)
        {
            Path filePath = currentDirectory.resolve(fileName); // Resolve the path

            if (!Files.exists(filePath))
            {
                System.out.println("File does not exist: " + filePath);
                continue;
            }

            if (Files.isDirectory(filePath))
            {
                System.out.println("Cannot read directory: " + filePath);
                continue;
            }

            try {

                List<String> lines = Files.readAllLines(filePath);
                System.out.println(fileName+": ");



                for (String line : lines)
                {
                    System.out.println(line);
                }

            }
            catch (IOException e)
            {
                System.out.println("Error reading file: " + filePath + " - " + e.getMessage());
            }
        }
    }


    public static void Help() {
        System.out.println("Available commands:");
        System.out.println("pwd - Print working directory");
        System.out.println("cd <directory_path> - Change directory");
        System.out.println("mkdir <directory_name> - Create a directory");
        System.out.println("ls [directory] - List directory contents");
        System.out.println("rm  -r <file_name> - Remove a file");
        System.out.println("touch <file_name> - Create a new file");
        System.out.println("cat <file_name> - Display file contents");
        System.out.println("> <file_name> <content> - Overwrite file with content");
        System.out.println(">> <file_name> <content> - Append content to file");
        System.out.println("exit - Exit the CLI");
        System.out.println("help - Show this help menu");
    }



}








