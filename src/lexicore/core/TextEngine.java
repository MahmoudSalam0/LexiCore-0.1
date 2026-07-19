package lexicore.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextEngine {

    private String originalText;
    private String currentText;
    private String processedText;

    private final ArrayList<String> sentences;
    private final ArrayList<String> words;

    public TextEngine() {
        this.originalText = "";
        this.currentText = "";
        this.processedText = "";

        this.sentences = new ArrayList<>();
        this.words = new ArrayList<>();
    }

    public void loadText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }

        this.originalText = text;
        this.currentText = text;
        clearProcessedData();
    }

    public void updateProcessedData(
            String processedText,
            List<String> sentences,
            List<String> words
    )
    {
        if (processedText == null || sentences == null || words == null) {
            throw new IllegalArgumentException("Processed data cannot be null.");
        }

        this.processedText = processedText;

        this.sentences.clear();
        this.sentences.addAll(sentences);

        this.words.clear();
        this.words.addAll(words);
    }

    public void updateCurrentText(String newText) {
        if (newText == null) {
            throw new IllegalArgumentException("New text cannot be null.");
        }

        this.currentText = newText;
        clearProcessedData();
    }

    private void clearProcessedData() {
        this.processedText = "";
        this.sentences.clear();
        this.words.clear();
    }

    public boolean hasText() {
        return !currentText.isBlank();
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getCurrentText() {
        return currentText;
    }

    public String getProcessedText() {
        return processedText;
    }

    public List<String> getSentences() {
        return Collections.unmodifiableList(sentences);
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }
}