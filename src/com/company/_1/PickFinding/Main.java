package com.company._1.PickFinding;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            // testPeakFinding1D();
            testPeakFinding2D(5, 5);
        }
    }

    /**
     * Проверка работы алгоритма для поиска локального максимума в 1-D массиве
     */
    static void testPeakFinding1D() {
        int[] arr = new int[15];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10);
            System.out.print(arr[i] + " ");
        }
        System.out.println("");
        System.out.println("findPeakIn1Dim1: " + findPeakIn1Dim1(arr));
        System.out.println("findPeakIn1Dim2: " + findPeakIn1Dim2(arr));
    }

    static void testPeakFinding2D(int n, int m) {
        int arr[][] = {{9, 8, 2, 7, 2 },
                {2, 8, 5, 9, 2 },
                {9, 1, 3, 1, 5 },
                {7, 8, 0, 1, 4 },
                {3, 0, 2, 9, 8 }};//new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = (int) (Math.random() * 10);
                System.out.printf("(%d, %d):%d ", i, j, arr[i][j]);
            }
            System.out.println("");
        }
        int peak = findPeakIn2Dim(arr);
        System.out.println(peak);
    }

    /**
     *  Алгорим нахождения первого peak в массиве путем перебора и проверки всех элементов поочереди.
     *  Ходший вариант O(n) => Сложность - O(n).
     */
    public static int findPeakIn1Dim1(int[] arr) {
        if (arr[0] >= arr[1]) return arr[0];
        //6 7 3 0 8 2 0 8 6 3 3 0 1 4 2 6 7 1 3 7
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i-1] && arr[i] >= arr[i+1]) return arr[i];
        }

        return arr[arr.length - 1];
    }

    /**
     *  Алгорим нахождения случайного peak в массиве путем бинарного поиска.
     *  Сложность - O(log2(n)).
     */
    public static int findPeakIn1Dim2(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        int midI = (end - start) / 2;
        while (midI != arr.length - 1 && midI != 0) {
            if (arr[midI] < arr[midI + 1]) {
                start = midI + 1;
                midI = start + (end - start) / 2;
            } else if (arr[midI] < arr[midI - 1]) {
                end = midI - 1;
                midI = start + (end - start) / 2;
            } else {
                System.out.println(midI);
                return arr[midI];
            }
        }
        System.out.println(midI);
        return arr[midI];
    }

    /**
     * Алгоритм поиска локального максимума в матрице N x M
     * Сложность - O(nlog(m))
     * @param arr
     * @return
     */
    public static int findPeakIn2Dim(int[][] arr) {

        int start = 0;
        int end = arr[0].length - 1;
        int curMiddle = (end - start) / 2;

        // Пока не достигли крайних колонок
        // Берем колонку в середине находим в ней абсолютный максимум
        // Сравниваем его с соседями и повторяем алгоритм для той половины колонок,
        // чей сосед больше теущего максимума колонки (неважно какой фактически больше берем первый попавшийся)
        // Если больших соседей нет, то текущий элемент локальный максимум в матрице.
        while (curMiddle != 0 && curMiddle != arr[0].length - 1) {
            int colMaxIndex = absMaxIndex(arr, curMiddle);
            if (arr[colMaxIndex][curMiddle - 1] > arr[colMaxIndex][curMiddle]) {
                end = curMiddle - 1;
                curMiddle = start + (end - start) / 2;
            } else if (arr[colMaxIndex][curMiddle + 1] > arr[colMaxIndex][curMiddle]) {
                start = curMiddle + 1;
                curMiddle = start + (end - start) / 2;
            } else {
                System.out.printf("(%d, %d): ", colMaxIndex, curMiddle);
                return arr[colMaxIndex][curMiddle];
            }
        }

        // Если достигли крайней колонки то находим в нем абсолютный максимум,
        // который и является локальным максмумом в матрице
        int colMaxIndex = absMaxIndex(arr, curMiddle);
        System.out.printf("(%d, %d): ", colMaxIndex, curMiddle);
        return arr[colMaxIndex][curMiddle];
    }

    /**
     * Вспомогательный метод для findPeakIn2Dim.
     * Ищет абсолютный максимум в колонке индексом index массива arr
     * @param arr
     * @return индекс максимума
     */
    private static int absMaxIndex(int[][] arr, int index) {
        int maxIndex = 0;
        int max = arr[maxIndex][index];

        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i][index]) {
                maxIndex = i;
                max = arr[maxIndex][index];
            }
        }

        return maxIndex;
    }
}
