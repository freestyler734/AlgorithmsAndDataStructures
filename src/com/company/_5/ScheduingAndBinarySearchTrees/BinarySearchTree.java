package com.company._5.ScheduingAndBinarySearchTrees;


import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class BinarySearchTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int height = 0;

    BinarySearchTree() { }

    public void add(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        add(node);
    }

    private void add(Node<K, V> node) {
        int curHeight = 1;
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
                curHeight++;
            }
        }
        height = height > curHeight ? height : curHeight;
    }

    public void visualizeTree(){

        Node<K,V>[] heapStructure = new Node[(int) Math.pow(2, height) - 1];

        straightPassing(root, heapStructure, 0);

        for (int i = 0; i < heapStructure.length; i++) {
            int curLayer = (int) (Math.log(i + 1)/Math.log(2)) + 1;
            IntStream.range(0, (int) (Math.pow(2, height - curLayer)) - 1).forEach((e) -> System.out.print("    "));
            System.out.printf(" %s ", heapStructure[i] == null ? "  " : heapStructure[i].key);
            IntStream.range(0, (int) (Math.pow(2, height - curLayer))).forEach((e) -> System.out.print("    "));
            if (i == 0 || i + 1 == (Math.pow(2, curLayer) - 1)) {
                IntStream.range(0, height - curLayer).forEach(e -> System.out.print("\n"));
            }
        }
        System.out.println("\n" + IntStream.range(0, (int) Math.pow(2, height) + 1).mapToObj((e) -> "____").collect(Collectors.joining()) + "\n");

    }

    private void straightPassing(Node<K, V> node, Node<K,V>[] heapStructure, int index) {
        if (node == null) {
            return;
        }

        heapStructure[index] = node;
        straightPassing(node.leftChild, heapStructure, 2 * index + 1);
        straightPassing(node.rightChild,heapStructure, 2 * index + 2);
    }

    public V delete(K key) {
        Node<K, V> deleted = deleteNode(root, false, key);
        height = (root == null ? 0 : getHeight(root));
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

            Node<K, V> current = node.getSuccessor();
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

    /**
     * Устанавливает отношение между узлами parent и leftChild,
     * как родитель и левый потомок
     * @param parent
     * @param leftChild
     */
    private void setLeftRelation(Node parent, Node leftChild) {
        if (parent != null) parent.leftChild = leftChild;
        if (leftChild != null) leftChild.parent = parent;

    }

    /**
     * Устанавливает отношение между узлами parent и rightChild,
     * как родитель и правый потомок
     * @param parent
     * @param rightChild
     */
    private void setRightRelation(Node parent, Node rightChild) {
        if (parent != null) parent.rightChild = rightChild;
        if (rightChild != null) rightChild.parent = parent;

    }

    /**
     * Обнуляет ссылки узлов parent и leftChild, для удаления отношения родитель и левый потомок
     * @param parent
     * @param leftChild
     */
    private void deleteLeftReleation(Node parent, Node leftChild) {
        if (parent != null) parent.leftChild = null;
        if (leftChild != null) leftChild.parent = null;
    }

    /**
     * Обнуляет ссылки узлов parent и rightChild, для удаления отношения родитель и правый потомок
     * @param parent
     * @param rightChild
     */
    private void deleteRightReleation(Node parent, Node rightChild) {
        if (parent != null) parent.rightChild = null;
        if (rightChild != null) rightChild.parent = null;
    }

    /**
     * Исправляет значения rank в узле после удаления
     * @param node
     */
    private void normalizeRank(Node node) {
        while (node != null) {
            int sum = 1;
            if (node.leftChild != null) sum += node.leftChild.rank;
            if (node.rightChild != null) sum += node.rightChild.rank;
            node.rank = sum;
            node = node.parent;
        }

    }

    /**
     * Определяет высоту поддерева узла node
     * @return int
     */
    public int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
    }

    /**
     * Реализация узла для бинарного дерева поиска.
     * Узел содержит:
     *  1) parent - ссылка на родительский узел
     *  2) leftChild - ссылка на левый дочерний узел
     *  3) rightChild - ссылка на правый дочерний узел
     *  4) rank - количество элементов в поддереве текущего узла
     *  5) key - ключ узла, значение для соблюдения инвариант поддерева
     *  6) value - хранимое значение узла
     *
     * @param <K> extends Comparable<K> - обобщенный тип для key
     * @param <V> - тип для value
     */
    public static class Node<K extends Comparable<K>, V> {
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        private int rank = 1;
        private int height = 1;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Находит узел с минимальным ключом в поддереве текущего узла
         * @return Node<K, V> минимальный узел или null
         */
        public Node<K, V> findMin() {
            return this.leftChild == null ? this : this.leftChild.findMin();
        }

        /**
         * Находит узел с максимальным ключом в поддереве текущего узла
         * @return Node<K, V> максимальный узел или null
         */
        public Node<K, V> findMax() {
            return this.rightChild == null ? this : this.rightChild.findMax();
        }

        /**
         * Находит узел с указанным ключом key в поддереве текущего узла
         * @param key - ключ для поиска
         * @return Node<K, V> узел с ключом key или null
         */
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

        /**
         * Возвращает узел с меньшим ключом среди узлов с ключом больше текущего
         * (следущий больший)
         * @return Node<K, V> узел или null
         */
        public Node<K, V> getSuccessor() {
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

        /**
         * Возвращает узел с большим ключом среди узлов с ключом меньше текущего
         * (следущий меньший)
         * @return Node<K, V> узел или null
         */
        public Node<K, V> getPredecessor() {
            if (this.leftChild != null) {
                return this.leftChild.findMin();
            }
            Node<K, V> current = this;
            while (current.parent != null &&
                    current.parent.leftChild != null &&
                    current.key.compareTo((K) current.parent.leftChild.key) == 0) {
                current = current.parent;
            }
            return current.parent;
        }

        @Override
        public String toString() {
            return "{" + key + ", " + value + '}';
        }
    }
}
