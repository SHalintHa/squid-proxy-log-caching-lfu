package lfu_java;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LFUCache {

    private Map<String, Node> values = new HashMap<>();
    private Map<String, Integer> counts = new HashMap<>();
    private TreeMap<Integer, DoubleLinkedList> frequencies = new TreeMap<>();
    private final int MAX_CAPACITY;

    public LFUCache(int capacity) {
        MAX_CAPACITY = capacity;
    }
    
    public void write_cache(BufferedWriter writer) {
    	
    	this.values.forEach((k, v) -> {
			try {
				writer.write(
							"key: " + k + " " +
							"item: "+ v.value + " " +
							"timestamp: " + LocalDateTime.now() + "\n"
							
						);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }

    public String get(String key) {
        if (!values.containsKey(key)) {
            return null;
        }

        Node node = values.get(key);

        // Move item from one frequency list to next. O(1) this time.
        int frequency = counts.get(key);
        frequencies.get(frequency).remove(node);
        removeIfListEmpty(frequency);
        frequencies.computeIfAbsent(frequency + 1, k -> new DoubleLinkedList()).add(node);

        counts.put(key, frequency + 1);
        return values.get(key).value;
    }

    public void set(String key, String value) {
        if (!values.containsKey(key)) {

            Node node = new Node(key, value);

            if (values.size() == MAX_CAPACITY) {

                int lowestCount = frequencies.firstKey();   // smallest frequency
                Node nodeTodelete = frequencies.get(lowestCount).head(); // first item (LRU)
                frequencies.get(lowestCount).remove(nodeTodelete);

                String keyToDelete = nodeTodelete.key();
                removeIfListEmpty(lowestCount);
                values.remove(keyToDelete);
                counts.remove(keyToDelete);
            }

            values.put(key, node);
            counts.put(key, 1);
            frequencies.computeIfAbsent(1, k -> new DoubleLinkedList()).add(node); // starting frequency = 1
        }
    }

    private void removeIfListEmpty(int frequency) {
        if (frequencies.get(frequency).size() == 0) {
            frequencies.remove(frequency);  // remove from map if list is empty
        }
    }
    
}