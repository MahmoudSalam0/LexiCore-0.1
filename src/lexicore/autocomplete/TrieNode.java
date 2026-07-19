package lexicore.autocomplete;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    private final Map<Character, TrieNode> children;
    private boolean endOfWord;
    private int frequency;

    public TrieNode() {
        this.children = new HashMap<>();
        this.endOfWord = false;
        this.frequency = 0;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public TrieNode getChild(char character) {
        return children.get(character);
    }

    public void addChild(char character, TrieNode node) {
        if (node == null) {
            throw new IllegalArgumentException(
                    "Child TrieNode cannot be null."
            );
        }

        children.put(character, node);
    }

    public boolean hasChild(char character) {
        return children.containsKey(character);
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        frequency++;
    }
}