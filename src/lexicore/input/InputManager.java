package lexicore.input;

import java.util.Scanner;

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
}