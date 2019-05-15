package com.company._5.ScheduingAndBinarySearchTrees;

import com.company.Util;

public class Main {

    public static void main(String[] args) {

        int[] randomArray = Util.getArray(9); // {86, 67, 44, 68, 45, 15, 52, 16, 3};//
        BinarySearchTree<Integer, String> bst = makeRandomBST(randomArray);
        bst.visualizeTree();
        //bst.testNextLarger();
        deleteNodes(bst, randomArray);
    }

    private static BinarySearchTree<Integer, String> makeRandomBST(int[] array) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        for (int i = 0; i < array.length; i++) {
            bst.add(array[i], ""+i);
            //bst.visualizeTree();
        }
        return bst;
    }

    private static BinarySearchTree<Integer, String> deleteNodes(BinarySearchTree<Integer, String> bst, int[] array) {
        System.out.println("DELETING");
        for (int i = 0; i < array.length; i++) {
            bst.delete(array[i]);
            bst.visualizeTree();
        }
        return bst;
    }

}
