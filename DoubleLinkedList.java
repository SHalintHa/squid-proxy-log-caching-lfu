class DoubleLinkedList {
    private int n;
    private Node head;
    private Node tail;

    public void add(Node node) {
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        n++;
    }

    public void remove(Node node) {

        if (node.next == null) tail = node.prev;
        else node.next.prev = node.prev;

        if (head.key == node.key) head = node.next;
        else node.prev.next = node.next;

        n--;
    }

    public Node head() {
        return head;
    }

    public int size() {
        return n;
    }
}