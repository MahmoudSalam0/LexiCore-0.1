package lexicore.preprocessing;

import lexicore.core.TextEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextPreprocessor {

    private final TextEngine textEngine;

    public TextPreprocessor(TextEngine textEngine) {
        if (textEngine == null) {
            throw new IllegalArgumentException("TextEngine cannot be null.");
        }

        this.textEngine = textEngine;
    }

    public void processText() {
        String currentText = textEngine.getCurrentText();

        if (currentText.isBlank()) {
            textEngine.updateProcessedData(
                    "",
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            return;
        }

        List<String> sentences = extractSentences(currentText);
        List<String> words = extractWords(currentText);
        String processedText = String.join(" ", words);

        textEngine.updateProcessedData(
                processedText,
                sentences,
                words
        );
    }

    private List<String> extractSentences(String text) {
        List<String> sentences = new ArrayList<>();

        String[] sentenceParts = text.split("[.!?]+");

        for (String sentence : sentenceParts) {
            String cleanedSentence = cleanText(sentence);

            if (!cleanedSentence.isBlank()) {
                sentences.add(cleanedSentence);
            }
        }

        return sentences;
    }

    private List<String> extractWords(String text) {
        List<String> words = new ArrayList<>();

        String cleanedText = cleanText(text);

        if (cleanedText.isBlank()) {
            return words;
        }

        String[] wordParts = cleanedText.split("\\s+");

        for (String word : wordParts) {
            if (!word.isBlank()) {
                words.add(word);
            }
        }

        return words;
    }

    private String cleanText(String text) {
        return text
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}