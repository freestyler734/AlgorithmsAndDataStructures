package com.company._3.InsertionSortAndMergeSort;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // insertionSortTest();
        mergeSortTest();
    }

    /**
     * Сортировка вставками
     * Сложность - O(N^2)
     * @param array
     */
    private static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];

            int j = i - 1;
            while (j >= 0 && key < array[j]) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static int[] getArray() {
        int[] array = new int[5];
        System.out.print("input array:          ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10);
            System.out.print(array[i] + " ");
        }
        return array;
    }

    private static void insertionSortTest() {
        int[] array = getArray();
        insertionSort(array);
        System.out.print("\nafter insertion sort: ");
        Arrays.stream(array).forEach(e -> System.out.print(e + " "));
        System.out.println("\n---");
    }

    private static void mergeSortTest() {
        int[] array = getArray();
        array = mergeSort(array);
        System.out.print("\nafter merge sort:     ");
        Arrays.stream(array).forEach(e -> System.out.print(e + " "));
        System.out.println("\n---");
    }

    /**
     * Сортировка слиянием
     * Сложность O(N * log(N))
     * @param array
     * @return
     */
    private static int[] mergeSort(int[] array) {
        if (array.length < 2) {
            return array;
        }

        int middle = (array.length) / 2;

        int[] left = new int[middle];
        for (int i = 0; i < middle; i++) {
            left[i] = array[i];
        }

        int[] right = new int[array.length - middle];
        for (int i = middle; i < array.length; i++) {
            right[i - middle] = array[i];
        }

        left = mergeSort(left);
        right = mergeSort(right);

        int i = 0;
        int j = 0;
        int c = 0;
        int[] sorted = new int[array.length];
        while (i < left.length && j < right.length) {
            int l = left[i];
            int r = right[j];

            if (l < r) {
                sorted[c++] = l;
                i++;
            } else if (l > r) {
                sorted[c++] = r;
                j++;
            } else {
                sorted[c++] = l;
                sorted[c++] = r;
                i++;
                j++;
            }
        }

        while (i < left.length) {
            sorted[c++] = left[i++];
        }

        while (j < right.length) {
            sorted[c++] = right[j++];
        }

        return sorted;
    }

}
