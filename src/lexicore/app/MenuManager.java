package lexicore.app;

import lexicore.core.TextEngine;
import lexicore.input.InputManager;
import lexicore.preprocessing.TextPreprocessor;

import java.util.List;
import java.util.Scanner;

public class MenuManager {

    private final Scanner scanner;
    private final TextEngine textEngine;
    private final InputManager inputManager;
    private final TextPreprocessor textPreprocessor;

    public MenuManager(
            Scanner scanner,
            TextEngine textEngine,
            InputManager inputManager,
            TextPreprocessor textPreprocessor
    ) {
        if (scanner == null
                || textEngine == null
                || inputManager == null
                || textPreprocessor == null) {

            throw new IllegalArgumentException(
                    "MenuManager dependencies cannot be null."
            );
        }

        this.scanner = scanner;
        this.textEngine = textEngine;
        this.inputManager = inputManager;
        this.textPreprocessor = textPreprocessor;
    }

    public void start() {

        boolean running = true;

        printWelcomeMessage();

        while (running) {

            printMenu();

            int choice = readMenuChoice();

            switch (choice) {

                case 1:
                    loadDirectText();
                    break;

                case 2:
                    loadTextFromFile();
                    break;

                case 3:
                    displayProcessedText();
                    break;

                case 4:
                    displaySentences();
                    break;

                case 5:
                    displayWords();
                    break;

                case 6:
                    showAutocompleteComingSoon();
                    break;

                case 7:
                    showTeamFeatureComingSoon(
                            "Local Analytics Dashboard"
                    );
                    break;

                case 8:
                    showTeamFeatureComingSoon(
                            "Positional Search Engine"
                    );
                    break;

                case 9:
                    showTeamFeatureComingSoon(
                            "Atomic Word Replacement"
                    );
                    break;

                case 10:
                    showTeamFeatureComingSoon(
                            "On-Device Similarity Detector"
                    );
                    break;

                case 11:
                    showTeamFeatureComingSoon(
                            "Auto-Correction Spell Core"
                    );
                    break;

                case 12:
                    showTeamFeatureComingSoon(
                            "Cellular Text Compression"
                    );
                    break;

                case 0:
                    running = false;
                    System.out.println();
                    System.out.println("LexiCore is shutting down...");
                    System.out.println("Thank you for using LexiCore.");
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "Invalid option. Please choose a number from the menu."
                    );
            }
        }
    }

    private void printWelcomeMessage() {

        System.out.println("==========================================");
        System.out.println("               LexiCore");
        System.out.println("      Mobile Text Processing Engine");
        System.out.println("==========================================");
    }

    private void printMenu() {

        System.out.println();
        System.out.println("--------------- Main Menu ----------------");
        System.out.println("1. Enter text directly");
        System.out.println("2. Load text from file");
        System.out.println("3. Display processed text");
        System.out.println("4. Display extracted sentences");
        System.out.println("5. Display extracted words");
        System.out.println("6. Smart Autocompletion");
        System.out.println("7. Local Analytics Dashboard");
        System.out.println("8. Positional Search");
        System.out.println("9. Atomic Word Replacement");
        System.out.println("10. Similarity Detector");
        System.out.println("11. Auto-Correction");
        System.out.println("12. Cellular Text Compression");
        System.out.println("0. Shutdown");
        System.out.println("------------------------------------------");
        System.out.print("Choose an option: ");
    }

    private int readMenuChoice() {

        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);

        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private void loadDirectText() {

        String text = inputManager.readDirectText();

        loadAndProcessText(text);
    }

    private void loadTextFromFile() {

        String text = inputManager.readTextFromFile();

        loadAndProcessText(text);
    }

    private void loadAndProcessText(String text) {

        if (text == null || text.isBlank()) {
            System.out.println();
            System.out.println("No valid text was loaded.");
            return;
        }

        textEngine.loadText(text);
        textPreprocessor.processText();

        System.out.println();
        System.out.println("Text loaded and processed successfully.");
        System.out.println(
                "Words extracted: " + textEngine.getWords().size()
        );
        System.out.println(
                "Sentences extracted: " + textEngine.getSentences().size()
        );
    }

    private void displayProcessedText() {

        if (!validateTextLoaded()) {
            return;
        }

        System.out.println();
        System.out.println("Processed Text:");
        System.out.println("------------------------------------------");
        System.out.println(textEngine.getProcessedText());
    }

    private void displaySentences() {

        if (!validateTextLoaded()) {
            return;
        }

        List<String> sentences = textEngine.getSentences();

        System.out.println();
        System.out.println("Extracted Sentences:");
        System.out.println("------------------------------------------");

        if (sentences.isEmpty()) {
            System.out.println("No sentences were extracted.");
            return;
        }

        for (int i = 0; i < sentences.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + sentences.get(i)
            );
        }

        System.out.println(
                "Total sentences: " + sentences.size()
        );
    }

    private void displayWords() {

        if (!validateTextLoaded()) {
            return;
        }

        List<String> words = textEngine.getWords();

        System.out.println();
        System.out.println("Extracted Words:");
        System.out.println("------------------------------------------");

        if (words.isEmpty()) {
            System.out.println("No words were extracted.");
            return;
        }

        for (int i = 0; i < words.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + words.get(i)
            );
        }

        System.out.println(
                "Total words: " + words.size()
        );
    }

    private boolean validateTextLoaded() {

        if (!textEngine.hasText()) {
            System.out.println();
            System.out.println(
                    "No text is currently loaded."
            );
            System.out.println(
                    "Please choose option 1 or 2 first."
            );

            return false;
        }

        return true;
    }

    private void showAutocompleteComingSoon() {

        if (!validateTextLoaded()) {
            return;
        }

        System.out.println();
        System.out.println(
                "Smart Autocompletion will be implemented next."
        );
    }

    private void showTeamFeatureComingSoon(String featureName) {

        System.out.println();
        System.out.println(
                featureName + " is not connected yet."
        );
        System.out.println(
                "This feature will be implemented by another team member."
        );
    }
}