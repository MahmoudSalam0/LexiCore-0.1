package lexicore;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/** Feature 4: builds a Huffman binary tree using a min-heap. */
public final class HuffmanCodec {
    private static final class Node implements Comparable<Node> {
        private final Character symbol;
        private final int frequency;
        private final Node left;
        private final Node right;

        private Node(Character symbol, int frequency, Node left, Node right) {
            this.symbol = symbol;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() { return symbol != null; }

        @Override
        public int compareTo(Node other) { return Integer.compare(frequency, other.frequency); }
    }

    public static final class CompressionResult {
        private final int originalBytes;
        private final long compressedBits;
        private final int compressedBytes;
        private final Map<Character, String> codes;

        private CompressionResult(int originalBytes, long compressedBits,
                                  int compressedBytes, Map<Character, String> codes) {
            this.originalBytes = originalBytes;
            this.compressedBits = compressedBits;
            this.compressedBytes = compressedBytes;
            this.codes = codes;
        }

        public int getOriginalBytes() { return originalBytes; }
        public long getCompressedBits() { return compressedBits; }
        public int getCompressedBytes() { return compressedBytes; }
        public Map<Character, String> getCodes() { return new HashMap<>(codes); }
        public double getSavingPercent() {
            return originalBytes == 0 ? 0 : 100.0 * (originalBytes - compressedBytes) / originalBytes;
        }
    }

    public CompressionResult compress(String text) {
        int original = text.getBytes(StandardCharsets.UTF_8).length;
        if (text.isEmpty()) return new CompressionResult(0, 0, 0, new HashMap<>());

        Map<Character, Integer> frequencies = new HashMap<>();
        for (char ch : text.toCharArray()) frequencies.merge(ch, 1, Integer::sum);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        frequencies.forEach((ch, count) -> queue.add(new Node(ch, count, null, null)));
        while (queue.size() > 1) {
            Node first = queue.remove();
            Node second = queue.remove();
            queue.add(new Node(null, first.frequency + second.frequency, first, second));
        }

        Map<Character, String> codes = new HashMap<>();
        buildCodes(queue.remove(), "", codes);
        long bits = 0;
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            bits += (long) entry.getValue() * codes.get(entry.getKey()).length();
        }
        return new CompressionResult(original, bits, (int) ((bits + 7) / 8), codes);
    }

    private void buildCodes(Node node, String prefix, Map<Character, String> codes) {
        if (node.isLeaf()) {
            codes.put(node.symbol, prefix.isEmpty() ? "0" : prefix);
            return;
        }
        buildCodes(node.left, prefix + '0', codes);
        buildCodes(node.right, prefix + '1', codes);
    }
}
