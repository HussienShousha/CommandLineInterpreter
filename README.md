# Command Line Interpreter

A Java-based command-line interpreter that emulates common Unix/Linux shell commands with support for file operations, directory navigation, and I/O redirection.

> **Author:** HussienShousha

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Commands Reference](#commands-reference)
- [Examples](#examples)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Overview

This project is a fully functional command-line interpreter built in Java that provides a shell-like interface for file system operations. It demonstrates core concepts in file I/O, path manipulation, command parsing, and system interaction through a clean, extensible architecture.

The interpreter provides both interactive command execution and programmatic access through well-tested APIs, making it suitable for educational purposes and as a foundation for more complex CLI applications.

## Features

### Core Commands
- **Navigation**: `pwd`, `cd` with support for relative paths, absolute paths, `..`, and directory history
- **File Operations**: `touch`, `cat`, `rm` (with recursive option), `mv`
- **Directory Operations**: `mkdir` (with parent creation), `rmdir`, `ls` (with options)
- **Utilities**: `echo`, `Help`, `exit`

### Advanced Capabilities
- **I/O Redirection**: Output redirection with `>` (overwrite) and `>>` (append)
- **Listing Options**: 
  - `-a` flag for showing hidden files
  - `-r` flag for recursive directory listing
- **Directory History**: Navigate backward through visited directories with `cd -`
- **Batch Operations**: Multiple file/directory operations in a single command

### System Integration
- Custom prompt showing `[username@hostname directory] $`
- Cross-platform path handling
- Comprehensive error handling and user feedback

## Requirements

- **Java**: JDK 21 or higher
- **Build Tool**: Apache Maven 3.6+
- **Operating System**: Cross-platform (Windows, Linux, macOS)

## Installation

### Clone the Repository

```bash
git clone https://github.com/HussienShousha/CommandLineInterpreter.git
cd CommandLineInterpreter/CLI_Project/CLI
```

### Build with Maven

```bash
mvn clean install
```

### Run the Application

```bash
mvn exec:java -Dexec.mainClass="org.example.Terminal"
```

Alternatively, after building:

```bash
java -cp target/CLI-1.0-SNAPSHOT.jar org.example.Terminal
```

## Usage

Upon starting the application, you'll see a prompt similar to:

```
[username@hostname directory] $
```

Type commands as you would in a standard Unix/Linux shell. The interpreter will parse and execute them accordingly.

### Quick Start

```bash
# Display current directory
pwd

# Create a new directory
mkdir projects

# Navigate to the directory
cd projects

# Create a new file
touch README.md

# List directory contents
ls

# Write content to a file
echo Hello World > greeting.txt

# Display file contents
cat greeting.txt

# Exit the CLI
exit
```

## Commands Reference

### Navigation Commands

| Command | Description | Example |
|---------|-------------|---------|
| `pwd` | Print working directory | `pwd` |
| `cd [path]` | Change directory | `cd /home/user` |
| `cd ..` | Move to parent directory | `cd ..` |
| `cd -` | Return to previous directory | `cd -` |
| `cd` | Go to home directory | `cd` |

### File Operations

| Command | Description | Example |
|---------|-------------|---------|
| `touch <file>...` | Create new file(s) | `touch file1.txt file2.txt` |
| `cat <file>...` | Display file contents | `cat file.txt` |
| `rm <file>...` | Remove file(s) | `rm file.txt` |
| `rm -r <dir>` | Remove directory recursively | `rm -r folder/` |
| `mv <source> <dest>` | Move/rename file(s) | `mv old.txt new.txt` |

### Directory Operations

| Command | Description | Example |
|---------|-------------|---------|
| `mkdir <dir>...` | Create directory | `mkdir folder` |
| `mkdir -p <path>` | Create nested directories | `mkdir -p path/to/dir` |
| `rmdir <dir>...` | Remove empty directory | `rmdir folder` |
| `ls [options] [path]` | List directory contents | `ls -a` |
| `ls -a` | List all files (including hidden) | `ls -a` |
| `ls -r` | List recursively | `ls -r` |

### Utility Commands

| Command | Description | Example |
|---------|-------------|---------|
| `echo <text>` | Print text to console | `echo Hello World` |
| `echo <text> > <file>` | Redirect output to file (overwrite) | `echo Hello > file.txt` |
| `echo <text> >> <file>` | Redirect output to file (append) | `echo Hello >> file.txt` |
| `Help` | Display help information | `Help` |
| `exit` | Exit the CLI | `exit` |

## Examples

### File Management Workflow

```bash
# Create project structure
mkdir -p project/src/main
cd project

# Create source files
touch src/main/App.java
touch README.md

# Write to README
echo "# My Project" > README.md
echo "A Java application" >> README.md

# View the file
cat README.md

# List all files recursively
ls -r

# Navigate back
cd -
```

### I/O Redirection

```bash
# Create a file with content
echo "Line 1" > output.txt
echo "Line 2" >> output.txt
echo "Line 3" >> output.txt

# Display the content
cat output.txt

# Save directory listing to file
ls > directory_contents.txt
cat directory_contents.txt
```

### Batch Operations

```bash
# Create multiple files at once
touch file1.txt file2.txt file3.txt

# Create nested directory structure
mkdir -p docs/images docs/styles docs/scripts

# Remove multiple files
rm file1.txt file2.txt file3.txt
```

## Project Structure

```
CLI_Project/
├── CLI/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── org/
│   │   │           └── example/
│   │   │               ├── Terminal.java      # Main CLI logic and commands
│   │   │               └── Parser.java        # Command parsing
│   │   └── test/
│   │       └── java/
│   │           └── CommandTesting.java        # Unit tests
│   └── pom.xml                                # Maven configuration
└── README.md
```

### Key Components

- **Terminal.java**: Core application containing all command implementations, I/O handling, and the main execution loop
- **Parser.java**: Responsible for parsing user input into command names and arguments
- **CommandTesting.java**: Comprehensive JUnit test suite covering all commands

## Testing

The project includes a comprehensive test suite using JUnit 4.

### Run All Tests

```bash
mvn test
```

### Test Coverage

The test suite covers:
- ✅ Echo command with and without redirection
- ✅ File operations (touch, rm, mv, cat)
- ✅ Directory operations (mkdir, rmdir, ls)
- ✅ Navigation (pwd, cd)
- ✅ I/O redirection (overwrite and append modes)
- ✅ Edge cases and error conditions

### Example Test

```java
@Test
public void TestEcho() throws IOException {
    String result = terminal.GoToEcho(new String[]{"Hello", "World"});
    Assert.assertEquals("Hello World", result);
}
```

## Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes**
   - Write clean, documented code
   - Add tests for new functionality
   - Follow existing code style
4. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```
5. **Push to your branch**
   ```bash
   git push origin feature/amazing-feature
   ```
6. **Open a Pull Request**

### Development Guidelines

- Maintain Java 21 compatibility
- Write unit tests for new features
- Update documentation for new commands
- Follow consistent naming conventions
- Handle errors gracefully with user-friendly messages

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

**GitHub**: [@HussienShousha](https://github.com/HussienShousha)

For questions, suggestions, or bug reports, please [open an issue](https://github.com/HussienShousha/CommandLineInterpreter/issues).

---

**Note**: This CLI interpreter is designed for educational purposes and may not include all features of production Unix/Linux shells. It demonstrates fundamental concepts in command-line interface design, file system operations, and Java application development.
