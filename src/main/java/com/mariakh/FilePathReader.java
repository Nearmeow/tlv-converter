package com.mariakh;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathReader {

    private final BufferedReader reader;

    public FilePathReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String getSourceFilePath() {
        System.out.println("Enter the absolute path to the source file or 'exit' for exit");
        while (true) {
            try{
                String temp = reader.readLine();
                if (isValidPath(temp)) {
                    return temp;
                } else  {
                    isExit(temp);
                    System.out.println("The path you entered is incorrect or file is empty, try again");
                }
            } catch (IOException e) {
                System.out.println("The data could not be read");
            }
        }
    }

    public Path getOutputFile() {
        System.out.println("Enter the absolute path to the existing output file or to the file with extension '.json' to be created");
        while (true) {
            try {
                String temp = reader.readLine();
                Path filePath = Paths.get(temp);
                if (filePath.isAbsolute() && Files.isRegularFile(filePath)) {
                    return filePath;
                } else if (filePath.isAbsolute() && temp.endsWith(".json")) {
                    Files.createFile(filePath);
                    return filePath;
                } else {
                    isExit(temp);
                    System.out.println("The path you entered is incorrect, try again");
                }
            } catch (IOException e) {
                System.out.println("The data could not be read");
            }
        }
    }

    private boolean isValidPath(String fileName) {
        Path sourceFile = Paths.get(fileName);
        return Files.exists(sourceFile) && sourceFile.toFile().length() > 0;
    }

    private void isExit(String fileName) {
        if (fileName.equalsIgnoreCase("exit")) {
            System.out.println("Closing...");
            System.exit(0);
        }
    }
}
