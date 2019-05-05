package com.company._4.HeapAndHeapSort;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Heap {

    public static void main(String[] args) {
        int [] array = getArray();

        madeHeap(array);
        System.out.print("\nafter heap building: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        visualizeHeap(array);
    }

    public static void madeHeap(int array[]) {
        for (int i = (array.length / 2) - 1; i >= 0; i--) {
            maxHeapify(array, i);
        }
    }

    private static void maxHeapify(int[] array, int index) {

        while (index < array.length / 2) {
            int current = array[index];
            int left = array[2*index + 1];
            if (2*index + 2 <= array.length - 1) {
                int right = array[2*index + 2];
                if (right > current) {
                    if (right >= left) {
                        array[index] = right;
                        array[2 * index + 2] = current;
                        index = 2*index + 2;
                        continue;
                    }
                }
            }
            if (left > current) {
                array[index] = left;
                array[2 * index + 1] = current;
                index = 2 * index + 1;
                continue;
            } else {
                break;
            }
        }
    }

    private static int[] getArray() {
        int[] array = new int[15];
        System.out.print("input array:         ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10);
            System.out.print(array[i] + " ");
        }
        return array;
    }

    public static void visualizeHeap(int[] array) {
        int layerCount = (int) (Math.log(array.length)/Math.log(2)) + 1;
        for (int i = 0; i < array.length; i++) {
            int curLayer = (int) (Math.log(i + 1)/Math.log(2)) + 1;
            IntStream.range(0, (int) (Math.pow(2, layerCount - curLayer) / 2)).forEach((e) -> System.out.print("   "));
            System.out.printf(" %d ", array[i]);
            if (i == 0 || i == (Math.pow(2, i - 1) + 1)) {
                System.out.println("");
            }
        }
    }
}
