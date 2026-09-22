import java.util.*;

public class SinglyLinkedList<E extends Comparable<E>> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    public SinglyLinkedList() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);

        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (isEmpty()) {
            tail = null;
        }
        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    // write your codes here
    // swap the sequence of all the elements whereby the biggest value will
    // be swapped with the lowest value, the second biggest value will be swapped
    // with the
    // second lowest value etc.
    public void swap() {

        if (size <= 1) {
            return;
        }

        int n = size;

        // Step 1: read out the current values, in list order
        List<E> originalValues = new ArrayList<>();
        Node<E> current = head;
        while (current != null) {
            originalValues.add(current.getElement());
            current = current.getNext();
        }

        // Step 2: find, for each position, its rank when sorted by value
        // (sortedIndices[0] = position of the smallest value, sortedIndices[n-1] =
        // position of the largest, etc.)
        Integer[] sortedIndices = new Integer[n];
        for (int k = 0; k < n; k++) {
            sortedIndices[k] = k;
        }
        Arrays.sort(sortedIndices, (a, b) -> originalValues.get(a).compareTo(originalValues.get(b)));

        // Step 3: build the new sequence of values, pairing smallest<->largest, etc.
        List<E> result = new ArrayList<>(Collections.nCopies(n, null));
        int i = 0;
        int j = n - 1;
        while (i < j) {
            int lowPos = sortedIndices[i];
            int highPos = sortedIndices[j];
            result.set(lowPos, originalValues.get(highPos));
            result.set(highPos, originalValues.get(lowPos));
            i++;
            j--;
        }
        if (i == j) {
            int midPos = sortedIndices[i];
            result.set(midPos, originalValues.get(midPos)); // odd count: middle value unchanged
        }

        // Step 4: rebuild the list using only the public API you already have
        while (!isEmpty()) {
            removeFirst();
        }
        for (E value : result) {
            addLast(value);
        }
    }
}
