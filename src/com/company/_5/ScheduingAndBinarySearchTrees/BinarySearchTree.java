package com.company._5.ScheduingAndBinarySearchTrees;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class BinarySearchTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int size = 0;
    private int maxDepth = 0;

    BinarySearchTree() { }

    public void add(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        add(node);
        size++;
    }


    private void add(Node<K, V> node) {
        int curDepth = 1;
        if (root == null) {
            root = node;
        } else {
            Node<K, V> current = root;

            while (current != null) {
                current.rank++;
                node.parent = current;
                if (node.key.compareTo(current.key) < 0) {
                    if (current.leftChild == null) {
                        current.leftChild = node;
                        current = null;
                    } else {
                        current = current.leftChild;
                    }

                } else if (node.key.compareTo(current.key) > 0) {
                    if (current.rightChild == null) {
                        current.rightChild = node;
                        current = null;
                    } else {
                        current = current.rightChild;
                    }
                } else {
                    normalizeRank(current);
                    return;
                }
                curDepth++;
            }
        }
        maxDepth = maxDepth > curDepth ? maxDepth : curDepth;
    }

    public void print() {
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.add(root);
        levelRecursivePassing(queue);
        System.out.println("Max depth: " + getMaxDepth());
    }

    private void symmetricPrint(Node<K, V> node) {
        if (node == null) {
            return;
        }

        symmetricPrint(node.leftChild);
        System.out.println(node);
        symmetricPrint(node.rightChild);
    }

    private void straightPrint(Node<K, V> node) {
        if (node == null) {
            return;
        }

        System.out.println(node);
        symmetricPrint(node.leftChild);
        symmetricPrint(node.rightChild);
    }

    public void levelPassing() {
        // System.out.println(node);
        Queue<Node<K, V>> queue = new LinkedList<>();

        queue.add(root);

        while (queue.size() != 0) {
            Node current = queue.poll();

            if (current.leftChild != null) {
                queue.add(current.leftChild);
            }
            if (current.rightChild != null) {
                queue.add(current.rightChild);
            }
        }
    }

    private void levelRecursivePassing(Queue<Node<K,V>> queue) {
        if (queue.size() == 0) {
            return;
        }

        Node current = queue.poll();
        System.out.println(current+ ", ");

        if (current.leftChild != null) {
            queue.add(current.leftChild);
        }
        if (current.rightChild != null) {
            queue.add(current.rightChild);
        }
        levelRecursivePassing(queue);
    }

    public int getMaxDepth() {
        return maxDepth;
    }


    public void visualizeTree(){

        Node<K,V>[] heapStructure = new Node[(int) Math.pow(2, maxDepth) - 1];

        straightPassing(root, heapStructure, 0);

        for (int i = 0; i < heapStructure.length; i++) {
            int curLayer = (int) (Math.log(i + 1)/Math.log(2)) + 1;
            IntStream.range(0, (int) (Math.pow(2, maxDepth - curLayer)) - 1).forEach((e) -> System.out.print("    "));
            System.out.printf(" %s ", heapStructure[i] == null ? "  " : heapStructure[i].key);
            IntStream.range(0, (int) (Math.pow(2, maxDepth - curLayer))).forEach((e) -> System.out.print("    "));
            if (i == 0 || i + 1 == (Math.pow(2, curLayer) - 1)) {
                IntStream.range(0, maxDepth - curLayer).forEach(e -> System.out.print("\n"));
            }
        }
        System.out.println("\n" + IntStream.range(0, (int) Math.pow(2, maxDepth) + 1).mapToObj((e) -> "____").collect(Collectors.joining()) + "\n");

    }

    private void straightPassing(Node<K, V> node, Node<K,V>[] heapStructure, int index) {
        if (node == null) {
            return;
        }

        heapStructure[index] = node;
        straightPassing(node.leftChild, heapStructure, 2 * index + 1);
        straightPassing(node.rightChild,heapStructure, 2 * index + 2);
    }

    public static class Node<K extends Comparable<K>, V> {
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        private int rank = 1;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node<K, V> findMin() {
            return this.leftChild == null ? this : this.leftChild.findMin();
        }

        public Node<K, V> findMax() {
            return this.rightChild == null ? this : this.rightChild.findMax();
        }

        public Node<K, V> find(K key) {
            if (this.key.compareTo(key) == 0) {
                return this;
            } else if (key.compareTo(this.key) < 0) {
                Node<K, V> left = this.leftChild;
                return left == null ? null : left.find(key);
            } else {
                Node<K, V> right = this.rightChild;
                return right == null ? null : right.find(key);
            }
        }

        public int getLevel() {
            Node<K, V> current = this;
            int counter = 1;
            while (current.parent != null) {
                counter++;
                current = current.parent;
            }

            return counter;
        }

        public int getMaxDepth() {
            int left = leftChild == null ? 0 : leftChild.getMaxDepth();
            int right = rightChild == null ? 0 : rightChild.getMaxDepth();

            return Math.max(left, right) + 1;
        }

        public Node<K, V> getNextLarger() {
            if (this.rightChild != null) {
                return this.rightChild.findMin();
            }
            Node<K, V> current = this;
            while (current.parent != null &&
                   current.parent.rightChild != null &&
                   current.key.compareTo((K) current.parent.rightChild.key) == 0) {
                current = current.parent;
            }
            return current.parent;
        }

        @Override
        public String toString() {
            return "{" + key + ", " + value + '}';
        }
    }

    private Node<K, V> getMinNode(Node<K, V> node) {
        if (node.leftChild == null) {
            return node;
        }
        return getMinNode(node.leftChild);
    }

    private Node<K, V> getMaxNode(Node<K, V> node) {
        if (node.rightChild == null) {
            return node;
        }
        return getMaxNode(node.rightChild);
    }

    public V delete(K key) {
        Node<K, V> deleted = deleteNode(root, false, key);
        maxDepth = (root == null ? 0 : root.getMaxDepth());
        return deleted == null ? null : deleted.value;
    }

    private Node<K, V> deleteNode(Node<K, V> current, boolean isLeft, K key) {
        if (current == null) {
            return current;
        }

        Node<K, V> found = null;
        if (key.compareTo(current.key) < 0) {
            found = deleteNode(current.leftChild,true, key);
        } else if(key.compareTo(current.key) > 0) {
            found = deleteNode(current.rightChild,false, key);
        } else {
            deleteNodeRelations(current, isLeft);
            return current;
        }

        return found;
    }

    private void deleteNodeRelations(Node<K, V> node, boolean isLeft) {
        Node<K, V> parent = node.parent;
        Node<K, V> newNode = null;

        if (node.leftChild == null && node.rightChild == null) {
            if (isLeft) {
                deleteLeftReleation(parent, node);
            } else {
                deleteRightReleation(parent, node);
            }
        } else if (node.leftChild != null && node.rightChild != null) {

            Node<K, V> current = node.getNextLarger();
            Node<K, V> curParent = current.parent;
            if (curParent.equals(node)) {
                deleteRightReleation(curParent, current);
                setLeftRelation(current, node.leftChild);
            } else {
                setLeftRelation(curParent, current.rightChild);
                setLeftRelation(current, node.leftChild);
                setRightRelation(current, node.rightChild);
            }

            newNode = current;
        } else if (node.leftChild != null) {
            newNode = node.leftChild;
        } else {
            newNode = node.rightChild;
        }

        if (parent != null) {
            if (isLeft) {
                setLeftRelation(parent, newNode);
            } else {
                setRightRelation(parent, newNode);
            }
        } else {
            root = newNode;
            if (newNode != null) {
                newNode.parent = null;
            }
        }

        node.parent = null;
        node.leftChild = null;
        node.rightChild = null;

        normalizeRank((newNode == null ? parent : newNode));

    }

    private void setLeftRelation(Node parent, Node leftChild) {
        if (parent != null) parent.leftChild = leftChild;
        if (leftChild != null) leftChild.parent = parent;

    }

    private void setRightRelation(Node parent, Node rightChild) {
        if (parent != null) parent.rightChild = rightChild;
        if (rightChild != null) rightChild.parent = parent;

    }

    private void deleteLeftReleation(Node parent, Node leftChild) {
        if (parent != null) parent.leftChild = null;
        if (leftChild != null) leftChild.parent = null;
    }

    private void deleteRightReleation(Node parent, Node rightChild) {
        if (parent != null) parent.rightChild = null;
        if (rightChild != null) rightChild.parent = null;
    }

    private void normalizeRank(Node node) {
        while (node != null) {
            int sum = 1;
            if (node.leftChild != null) sum += node.leftChild.rank;
            if (node.rightChild != null) sum += node.rightChild.rank;
            node.rank = sum;
            node = node.parent;
        }

    }

    public V find(K key) {
        Node<K, V> found = root.find(key);
        return found == null ? null: found.value;
    }

    public V findMin() {
        return root == null ? null : root.findMin().value;
    }

    public V findMax() {
        return root == null ? null : root.findMax().value;
    }

    public void testNextLarger() {

        Queue<Node<K, V>> queue = new LinkedList<>();

        queue.add(root);

        while (queue.size() != 0) {
            Node current = queue.poll();
            System.out.printf("node: %s, next larger: %s \n", current, current.getNextLarger());
            if (current.leftChild != null) {
                queue.add(current.leftChild);
            }
            if (current.rightChild != null) {
                queue.add(current.rightChild);
            }
        }
    }
}
