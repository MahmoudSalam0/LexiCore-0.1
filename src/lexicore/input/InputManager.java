package lexicore.input;

import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InputManager {

    private final Scanner scanner;

    public InputManager(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }

        this.scanner = scanner;
    }

    public String readDirectText() {
        System.out.println();
        System.out.println("Enter or paste your text.");
        System.out.println("Type $$END_TEXT$$ on a new line to finish:");

        StringBuilder textBuilder = new StringBuilder();

        while (true) {
            String line = scanner.nextLine();

            if (line.equals("$$END_TEXT$$")) {
                break;
            }

            textBuilder.append(line);
            textBuilder.append(System.lineSeparator());
        }

        return textBuilder.toString().trim();
    }

    public String readTextFromFile() {
        System.out.println();
        System.out.print("Enter the absolute path of the text file: ");

        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("File path cannot be empty.");
            return "";
        }

        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            System.out.println("File does not exist.");
            return "";
        }

        if (!Files.isRegularFile(path)) {
            System.out.println("The provided path is not a regular file.");
            return "";
        }

        try {
            String text = Files.readString(path).trim();

            if (text.isEmpty()) {
                System.out.println("The file is empty.");
                return "";
            }

            return text;

        } catch (IOException exception) {
            System.out.println("Unable to read the file.");
            System.out.println("Reason: " + exception.getMessage());
            return "";
        }
    }
}