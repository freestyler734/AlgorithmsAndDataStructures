package com.company._5.ScheduingAndBinarySearchTrees;

public class Main {

    public static void main(String[] args) {

        int[] randomArray = getArray(9); // {86, 67, 44, 68, 45, 15, 52, 16, 3};//
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

    /**
     * Генерирование данных
     * @return
     */
    private static int[] getArray(int size) {
        int[] array = new int[size];
        System.out.print("input array:         ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100);
            System.out.print(array[i] + " ");
        }
        System.out.println("\n");
        return array;
    }
}
