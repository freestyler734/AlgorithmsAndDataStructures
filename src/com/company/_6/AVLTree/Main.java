package com.company._6.AVLTree;

import com.company.Util;

public class Main {

    public static void main(String[] args) {
        int[] randomArray = Util.getArray(9); // {86, 67, 44, 68, 45, 15, 52, 16, 3};//
        AVLTree<Integer, String> avl = makeRandomAVL(randomArray);//new AVLTree<>();//

//        avl.add(40,"");
//        avl.add(58,"");
//        avl.add(51,"");
//        avl.add(50,"");
//        avl.add(59,"");
//        avl.add(49,"");
//        avl.add(52,"");


       // avl.visualizeTree();
//        avl.testLeftRotation();
       // avl.testRightRotation();
       // avl.visualizeTree();

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
