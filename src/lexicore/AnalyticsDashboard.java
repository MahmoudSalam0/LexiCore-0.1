package lexicore;

import java.util.Map;
import java.util.TreeMap;
import lexicore.core.TextEngine;

/** Low-overhead consolidated analytics view. */
public final class AnalyticsDashboard {
    public void print(TextEngine engine) {
        System.out.println("\n========== LOCAL ANALYTICS ==========");
        System.out.println("Total tokens       : " + engine.getWords().size());
        System.out.println("Unique vocabulary  : " + engine.getVocabulary().size());
        System.out.println("Characters (no WS) : " + engine.getCharacterCountWithoutSpaces());
        System.out.println("Character frequency: " + new TreeMap<Character, Integer>(engine.getCharacterFrequency()));
        System.out.println("Top word frequencies:");
        engine.getWordFrequency().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(10)
                .forEach(entry -> System.out.println("  " + entry.getKey() + " -> " + entry.getValue()));
        System.out.println("=====================================\n");
    }
}
