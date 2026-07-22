package lexicore.core;

import lexicore.preprocessing.TextPreprocessor;

import java.util.*;

public class TextEngine {

    public static final class ReplacementResult {
        private final int mutations;
        private final long elapsedNanos;

        private ReplacementResult(int mutations, long elapsedNanos) {
            this.mutations = mutations;
            this.elapsedNanos = elapsedNanos;
        }

        public int getMutations() { return mutations; }
        public double getElapsedMilliseconds() { return elapsedNanos / 1_000_000.0; }
    }

    private String originalText;
    private String currentText;
    private String processedText;

    private final List<List<String>> sentences;
    private final List<String> words;

    private final Map<String, Integer> wordFrequency = new HashMap<>();
    private final Map<Character, Integer> characterFrequency = new HashMap<>();
    private final Set<String> vocabulary = new HashSet<>();
    private final Deque<String> undoStates = new ArrayDeque<>();

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

        undoStates.clear();
        this.originalText = text;
        this.currentText = text;
        clearProcessedData();
    }

    public void updateProcessedData(String processedText, List<List<String>> sentences, List<String> words,Set<String> vocabulary, Map<Character, Integer> characterFrequency, Map<String, Integer> wordFrequency) {
        if (processedText == null || sentences == null || words == null) {
            throw new IllegalArgumentException("Processed data cannot be null.");
        }

        this.processedText = processedText;

        this.sentences.clear();
        this.sentences.addAll(sentences);

        this.words.clear();
        this.words.addAll(words);

        this.wordFrequency.clear();
        this.wordFrequency.putAll(wordFrequency);

        this.characterFrequency.clear();
        this.characterFrequency.putAll(characterFrequency);

        this.vocabulary.clear();
        this.vocabulary.addAll(vocabulary);
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

    public List<List<String>> getSentences() {
        return Collections.unmodifiableList(sentences);
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public Set<String> getVocabulary() { return new HashSet<>(vocabulary); }
    public Map<String, Integer> getWordFrequency() { return new HashMap<>(wordFrequency); }
    public Map<Character, Integer> getCharacterFrequency() { return new HashMap<>(characterFrequency); }
    public int getCharacterCountWithoutSpaces() { return characterFrequency.values().stream().mapToInt(Integer::intValue).sum(); }

    public boolean rollback() {
        if (undoStates.isEmpty()) return false;
        currentText = undoStates.pop();
        new TextPreprocessor(this).processText();
        //rebuildIndexes();
        return true;
    }

    public ReplacementResult replaceWord(String rawTarget, String rawReplacement) {
        String target = normalizeSingleWord(rawTarget);
        String replacement = normalizeSingleWord(rawReplacement);
        if (target.isEmpty() || replacement.isEmpty()) throw new IllegalArgumentException("Words cannot be empty.");
        long start = System.nanoTime();
        int mutations = 0;
        StringBuilder rebuilt = new StringBuilder();
        String[] parts = currentText.split("(?<=\\s)|(?=\\s)|(?<=[.!?])|(?=[.!?])");
        for (String part : parts) {
            if (part.equals(target)) { rebuilt.append(replacement); mutations++; }
            else rebuilt.append(part);
        }
        if (mutations > 0) {
            undoStates.push(currentText);
            currentText = rebuilt.toString();
            new TextPreprocessor(this).processText();
        }
        return new ReplacementResult(mutations, System.nanoTime() - start);
    }

    private String normalizeSingleWord(String input) {
        return preprocess(input).replaceAll("[.!?\\s]", "");
    }

    public static String preprocess(String raw) {
        if (raw == null) return "";
        return raw.toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}\\s.!?]", " ")
                .replaceAll("[\\t\\x0B\\f\\r ]+", " ")
                .replaceAll(" *\\n+ *", " ")
                .trim();
    }
}