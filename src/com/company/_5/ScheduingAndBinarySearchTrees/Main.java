package com.company._5.ScheduingAndBinarySearchTrees;

public class Main {

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.add(49, "3");
        bst.add(46, "2");
        bst.add(79, "1");
        bst.add(43, "5");
        bst.add(47, "7");
        bst.add(64, "6");
        bst.add(83, "0");
        bst.add(88, "0");
        bst.add(61, "0");
        bst.add(60, "0");
        bst.add(48, "4");
        bst.add(47, "4");
        bst.add(41, "4");
        bst.add(43, "4");
        bst.add(43, "4");
        bst.add(62, "4");
        bst.add(65, "4");
        bst.add(65, "4");

       // bst.levelPassing();
//        bst.visualizeTree();
//        System.out.println("\n" + bst.delete(49));
//        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(88));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(43));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(64));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(61));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(49));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(48));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(46));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(47));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();
        System.out.println("\n" + bst.delete(47));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------");
        bst.visualizeTree();

    }
}
