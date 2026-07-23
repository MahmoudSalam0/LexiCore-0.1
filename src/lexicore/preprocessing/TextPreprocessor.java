package lexicore.preprocessing;

import lexicore.core.TextEngine;

import java.util.*;

public class TextPreprocessor {

    private final TextEngine textEngine;

    private final List<List<String>> sentences = new ArrayList<>();
    private final List<String> words = new ArrayList<>();
    private final Map<String, Integer> wordFrequency = new HashMap<>();
    private final Map<Character, Integer> characterFrequency = new HashMap<>();
    private final Set<String> vocabulary = new HashSet<>();
    private final Deque<String> undoStates = new ArrayDeque<>();

    public TextPreprocessor(TextEngine textEngine) {
        if (textEngine == null) {
            throw new IllegalArgumentException("TextEngine cannot be null.");
        }

        this.textEngine = textEngine;
    }

    public void processText() {
        String currentText = textEngine.getCurrentText();

        /*if (currentText.isBlank()) {
            textEngine.updateProcessedData(
                    "",
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            return;
        }*/

        preprocess(currentText);
        String processedText = String.join(" ", words);

        textEngine.updateProcessedData(
                processedText,
                sentences,
                words,
                vocabulary,
                characterFrequency,
                wordFrequency
        );
    }

    private void preprocess(String text) {
        String[] sentenceParts = text.split("[.!?]+");

        for (String rawSentence : sentenceParts) {

            String cleanedSentence = cleanText(rawSentence);
            List<String> sentence = new ArrayList<>();

            if (!cleanedSentence.isBlank()){
                for (String word : cleanedSentence.split("\\s+")) {
                    sentence.add(word);
                    words.add(word);
                    vocabulary.add(word);
                    wordFrequency.merge(word, 1, Integer::sum);
                }
            }
            if (!sentence.isEmpty()) sentences.add(sentence);
        }

        for (char ch : text.toCharArray()) {
            if (!Character.isWhitespace(ch)) characterFrequency.merge(ch, 1, Integer::sum);
        }

    }

    private String cleanText(String text) {
        return text
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}