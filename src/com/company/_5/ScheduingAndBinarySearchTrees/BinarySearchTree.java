package com.company._5.ScheduingAndBinarySearchTrees;


import java.util.*;
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

                } else {
                    if (current.rightChild == null) {
                        current.rightChild = node;
                        current = null;
                    } else {
                        current = current.rightChild;

                    }
                }
                curDepth++;
            }
        }
        maxDepth = maxDepth > curDepth ? maxDepth :curDepth;
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

    }

    private void straightPassing(Node<K, V> node, Node<K,V>[] heapStructure, int index) {
        if (node == null) {
            return;
        }

        heapStructure[index] = node;
        straightPassing(node.leftChild, heapStructure, 2 * index + 1);
        straightPassing(node.rightChild,heapStructure, 2 * index + 2);
    }

    public static class Node<K, V> {
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        private int rank;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
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
        return (V) deleteNode(root, key).value;
    }

    private Node<K, V> deleteNode(Node<K, V> current, K key) {
        if (current == null) {
            return current;
        }

        Node<K, V> found = null;
        boolean isLeft = false;
        if (key.compareTo(current.key) < 0) {
            found = deleteNode(current.leftChild, key);
            isLeft = true;
        } else if(key.compareTo(current.key) > 0) {
            found = deleteNode(current.rightChild, key);
        } else {
            deleteNodeRelations(current, false);
            return current;
        }

        return found;
    }

    private void deleteNodeRelations(Node<K, V> node, boolean isLeft) {
        Node<K, V> parent = node.parent;
        if (parent != null) {

            if (node.leftChild == null && node.rightChild == null) {
                if (isLeft) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            } else if (node.leftChild != null && node.rightChild != null) {

                Node<K, V> current = node.rightChild;
                while (current.leftChild != null) {
                    current = current.leftChild;
                }

                if (isLeft) {
                    parent.leftChild = current;
                } else {
                    parent.rightChild = current;
                }
                current.parent.leftChild = null;
                current.leftChild = node.leftChild;

            } else if (node.leftChild != null) {
                if (isLeft) {
                    parent.leftChild = node.leftChild;
                } else {
                    parent.rightChild = node.leftChild;
                }

            } else {
                if (isLeft) {
                    parent.leftChild = node.rightChild;
                } else {
                    parent.rightChild = node.rightChild;
                }
            }

            node.parent = null;
        } if (node.key.compareTo(root.key) == 0) {
            Node<K, V> current = node.rightChild;
            while (current.leftChild != null) {
                current = current.leftChild;
            }

            root = current;
            current.parent.leftChild = null;
            current.leftChild = node.leftChild;
            current.rightChild = node.rightChild;

        }
    }

}
