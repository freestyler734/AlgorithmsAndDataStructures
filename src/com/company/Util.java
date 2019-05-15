package com.company;

public class Util {

    /**
     * Генерирование данных
     * @return
     */
    public static int[] getArray(int size) {
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
