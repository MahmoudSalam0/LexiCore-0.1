package lexicore.app;

import lexicore.core.TextEngine;
import lexicore.input.InputManager;
import lexicore.preprocessing.TextPreprocessor;

import java.util.Scanner;

public class LexiCoreApp {

    public static void main(String[] args) {

        // Scanner واحد فقط طوال فترة تشغيل البرنامج.
        Scanner scanner = new Scanner(System.in);

        // المخزن المركزي المشترك للنص ونتائج معالجته.
        TextEngine textEngine = new TextEngine();

        // مسؤول عن قراءة النص من المستخدم أو من ملف.
        InputManager inputManager = new InputManager(scanner);

        // مسؤول عن تنظيف النص واستخراج الكلمات والجمل.
        TextPreprocessor textPreprocessor =
                new TextPreprocessor(textEngine);

        System.out.println("=================================");
        System.out.println("       Welcome to LexiCore");
        System.out.println(" Mobile Text Processing Engine");
        System.out.println("=================================");

        System.out.println("Choose text input method:");
        System.out.println("1. Enter text directly");
        System.out.println("2. Read text from file");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        String inputText;

        switch (choice) {
            case "1":
                inputText = inputManager.readDirectText();
                break;

            case "2":
                inputText = inputManager.readTextFromFile();
                break;

            default:
                System.out.println("Invalid choice.");
                scanner.close();
                return;
        }

        if (inputText == null || inputText.isBlank()) {
            System.out.println("No valid text was loaded.");
            scanner.close();
            return;
        }

        // تحميل النص داخل المخزن المركزي.
        textEngine.loadText(inputText);

        // تنظيف النص واستخراج الجمل والكلمات.
        textPreprocessor.processText();

        // طباعة مؤقتة لاختبار تدفق البيانات.
        printProcessingResults(textEngine);

        scanner.close();
    }

    private static void printProcessingResults(TextEngine textEngine) {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       Processing Results");
        System.out.println("=================================");

        System.out.println("\n1. Original Text:");
        System.out.println(textEngine.getOriginalText());

        System.out.println("\n2. Current Text:");
        System.out.println(textEngine.getCurrentText());

        System.out.println("\n3. Processed Text:");
        System.out.println(textEngine.getProcessedText());

        System.out.println("\n4. Sentences:");
        for (int i = 0; i < textEngine.getSentences().size(); i++) {
            System.out.println(
                    (i + 1) + ". " +
                            textEngine.getSentences().get(i)
            );
        }

        System.out.println("\n5. Words:");
        for (int i = 0; i < textEngine.getWords().size(); i++) {
            System.out.println(
                    (i + 1) + ". " +
                            textEngine.getWords().get(i)
            );
        }

        System.out.println();
        System.out.println("Total sentences: "
                + textEngine.getSentences().size());

        System.out.println("Total words: "
                + textEngine.getWords().size());
    }
}