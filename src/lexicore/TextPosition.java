package lexicore;

/** Immutable structural location of a query match. */
public final class TextPosition {
    private final int sentenceIndex;
    private final int wordIndex;

    public TextPosition(int sentenceIndex, int wordIndex) {
        this.sentenceIndex = sentenceIndex;
        this.wordIndex = wordIndex;
    }

    public int getSentenceIndex() { return sentenceIndex; }
    public int getWordIndex() { return wordIndex; }

    @Override
    public String toString() {
        return "sentence=" + sentenceIndex + ", word=" + wordIndex;
    }
}
