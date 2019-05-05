package com.company._4.HeapAndHeapSort;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Heap {

    public static void main(String[] args) {
        int [] array = getArray();
        // testHeapMaking(array);
        testHeapSorting(array);
    }

    /**
     * Алгоритм построения кучи из массива array
     *
     * Сложность - O(N)
     * @param array
     */
    public static void makeHeap(int array[]) {
        for (int i = (array.length / 2) - 1; i >= 0; i--) {
            maxHeapify(array, i);
        }
    }

    /**
     * Проверка работы метода построения кучи
     * @param array
     */
    private static void testHeapMaking(int[] array) {
        makeHeap(array);
        System.out.print("\nafter heap building: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("\nHeap visualization:\n");
        visualizeHeap(array);
    }

    /**
     * Вспомогательный метод для нормализации элемента узла индекса index
     * @param array
     * @param index
     */
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

    /**
     * Генерирование данных
     * @return
     */
    private static int[] getArray() {
        int[] array = new int[20];
        System.out.print("input array:         ");
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100);
            System.out.print(array[i] + " ");
        }
        System.out.println("\n");
        return array;
    }

    /**
     * Вывод кучи в консоль
     * @param array
     */
    public static void visualizeHeap(int[] array) {
        int layerCount = (int) (Math.log(array.length)/Math.log(2)) + 1;
        for (int i = 0; i < array.length; i++) {
            int curLayer = (int) (Math.log(i + 1)/Math.log(2)) + 1;
            IntStream.range(0, (int) (Math.pow(2, layerCount - curLayer) - 1)).forEach((e) -> System.out.print("   "));
            System.out.printf(" %d ", array[i]);
            IntStream.range(0, (int) (Math.pow(2, layerCount - curLayer))).forEach((e) -> System.out.print("   "));
            if (i == 0 || i + 1 == (Math.pow(2, curLayer) - 1)) {
                System.out.println("\n");
            }
        }
        System.out.println();
    }

    /**
     * Сортировка кучей
     *
     * Сложность - O(N * log(N))
     * @param array
     * @return
     */
    public static int[] heapSort(int[] array) {
        int[] sorted = new int[array.length];
        makeHeap(array);
        visualizeHeap(array);
        for (int i = array.length - 1; i >= 0; i--) {
            int max = array[0];
            sorted[i] = max;
            array[0] = array[i];
            array[i] = -1;
            maxHeapify(array, 0);
        }

        return sorted;
    }

    /**
     * Тестирование сортировки кучей
     * @param array
     */
    private static void testHeapSorting(int[] array) {
        int[] sorted = heapSort(array);
        System.out.print("\nafter heap sorting:  ");
        for (int i = 0; i < sorted.length; i++) {
            System.out.print(sorted[i] + " ");
        }
        System.out.println("\n");
    }
}
