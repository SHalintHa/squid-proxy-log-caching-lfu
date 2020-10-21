package lfu_java;


class Node {
    String key;
    String value;
    Node next;
    Node prev;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }

	public String key() {
        return key;
    }

    public String value() {
        return value;
    }
}