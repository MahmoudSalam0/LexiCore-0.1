package lexicore.app;

import lexicore.core.TextEngine;
import lexicore.input.InputManager;
import lexicore.preprocessing.TextPreprocessor;

import java.util.Scanner;

public class LexiCoreApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TextEngine textEngine = new TextEngine();

        InputManager inputManager =
                new InputManager(scanner);

        TextPreprocessor textPreprocessor = new TextPreprocessor(textEngine);

        MenuManager menuManager = new MenuManager(
                scanner,
                textEngine,
                inputManager,
                textPreprocessor
        );

        menuManager.start();

        scanner.close();
    }
}