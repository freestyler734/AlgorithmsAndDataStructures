package com.company._6.AVLTree;

import com.company._5.ScheduingAndBinarySearchTrees.BinarySearchTree;

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    @Override
    public void add(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        super.add(node);
        rebalance(node);
        visualizeTree();
        //node.updateHeight();
    }


    @Override
    public void visualizeTree() {
        super.visualizeTree();
    }

    private Node<K, V> getRoot() {
        return (Node<K, V>) super.root;
    }

    public void leftRotation(Node node) {
        Node parent = node.getParent();
        Node right = node.getRightChild();
        if (parent != null) {
            if (parent.getLeftChild() == node) {
                setLeftRelation(parent, right);
            } else {
                setRightRelation(parent, right);
            }
        } else {
            root = right;
            deleteRightReleation(node, right);
        }
        setRightRelation(node, right.getLeftChild());
        setLeftRelation(right, node);
        node.updateHeight();
        right.updateHeight();
    }

    public void rightRotation(Node node) {
        Node parent = node.getParent();
        Node left = node.getLeftChild();
        if (parent != null) {
            if (parent.getLeftChild() == node) {
                setLeftRelation(parent, left);
            } else {
                setRightRelation(parent, left);
            }
        } else {
            root = left;
            deleteLeftReleation(node, left);
        }
        setLeftRelation(node, left.getRightChild());
        setRightRelation(left, node);
        node.updateHeight();
        left.updateHeight();
    }

    public void rebalance(Node node) {
        while (node != null) {
            node.updateHeight();
            if (node.getLeftHeight() - node.getRightHeight() >= 2) {
                if (node.getLeftChild().getLeftHeight() > node.getLeftChild().getRightHeight()) {
                    rightRotation(node);
                } else {
                    leftRotation(node.getLeftChild());
                    rightRotation(node);
                }
            } else if (node.getRightHeight() - node.getLeftHeight() >= 2) {
                if (node.getRightChild().getRightHeight() > node.getRightChild().getLeftHeight()) {
                    leftRotation(node);
                } else {
                    rightRotation(node.getRightChild());
                    leftRotation(node);
                }
            }
            node = node.getParent();
        }
    }

    public void testLeftRotation() {
        Node node = getRoot().getLeftChild();
        leftRotation(node);
        //node.updateHeight();
    }

    public void testRightRotation() {
        Node node = getRoot();
        rightRotation(node.getRightChild());
        //node.updateHeight();
    }

    public static class Node<K extends Comparable<K>, V> extends BinarySearchTree.Node<K, V> {
        private int height = 0;

        private Node(K key, V value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return this.key + "," + this.height;
        }

        private void updateHeight() {
            Node current = this;
            while (current != null) {
                int leftHeight = current.getLeftHeight();
                int rightHeight = current.getRightHeight();
                current.height = Math.max(leftHeight, rightHeight) + 1;
                current = (Node) current.parent;
            }
        }

        private int getLeftHeight() {
            return this.leftChild == null ? -1 : getLeftChild().height;
        }

        private int getRightHeight() {
            return this.rightChild == null ? -1 : getRightChild().height;
        }

        private Node<K, V> getParent() {
            return (Node<K, V>) this.parent;
        }

        private Node<K, V> getLeftChild() {
            return (Node<K, V>) this.leftChild;
        }

        private Node<K, V> getRightChild() {
            return (Node<K, V>) this.rightChild;
        }

    }
}
