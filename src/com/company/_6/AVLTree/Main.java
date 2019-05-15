package com.company._6.AVLTree;

public class Main {

    public static void main(String[] args) {
        int[] randomArray = getArray(9); // {86, 67, 44, 68, 45, 15, 52, 16, 3};//
        AVLTree<Integer, String> avl = new AVLTree<>();// makeRandomAVL(randomArray);;

        avl.add(58,"");
        avl.add(51,"");
        avl.add(50,"");


        avl.visualizeTree();
//        avl.testLeftRotation();
//        avl.visualizeTree();

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

    private static AVLTree<Integer, String> makeRandomAVL(int[] array) {
        AVLTree<Integer, String> bst = new AVLTree<>();
        for (int i = 0; i < array.length; i++) {
            bst.add(array[i], ""+i);
            //bst.visualizeTree();
        }
        return bst;
    }
}
