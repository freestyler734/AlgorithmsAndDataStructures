package com.company._2.DocumentDistanceProblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация алгоритма 6 определения document distance
 * из лекций курса введение в алгоритмы и структуры данных.
 * Алгоритм реализован таким образом чтобы иметь такую же сложность
 * как и оригинальная реализация на питоне, поэтому некоторые методы
 * реализованы не самым лучшим образом
 *
 * В данном варианте в методе getWordsFromLineList заменяем слияние списков в один, расширением первого.
 * Изменен алгоритм подсчета скалярного произведения векторов методом 2-х указателей
 * Изменен способ определения векторов с помощью хеш-таблицы
 * Изменен алгоритм разбивки строки на слова
 * Изменен алгоритм сортировки с Сортировки вставками сортировкой слиянием
 */
public class Algorithm6 {

    /*
     * Определение сложности алгоритма O не считая считывания текста из файла, где
     * Z - кол-во слов в тексте
     * w - кол-во букв в слове (считаем константой)
     * n - количество символов в строке
     * l - кол-во строк в документе (l1 - для 1-го, l2 - для второго)
     * k - кол-во слов в строке = n / w
     * P - кол-во уникальных слов в тексте
     *
     *
     * Старая Общая сложность = O(Z1 + (P1)^2 + Z2 + (P2)^2)
     * Новая Общая сложность = O(Z1 + P1 * log(P1) + Z2 + P2 * log(P2)), т.к. P < P * log(P)
     * Самый долгий метод - wordFrequenciesForFile !!!
     */
    public static void main(String[] args) {
        Instant start = Instant.now();
        List<Map.Entry<String,Integer>> sortedWordList1 = wordFrequenciesForFile("t2.bobsey.txt"); // 1 - раз, сложность - O(Z1 + P1 * log(P1))
        List<Map.Entry<String,Integer>> sortedWordList2 = wordFrequenciesForFile("t3.lewis.txt");  // 1 - раз, сложность - O(Z2 + P2 * log(P2))
        double distance = vectorAngle(sortedWordList1, sortedWordList2);                                    // 1 - раз, сложность - O((P1) + (P2))
        System.out.println("distance: " + distance);
        Instant end = Instant.now();
        System.out.println("execution time: " + Duration.between(start, end));
    }


    /**
     * Чтение строк из файла
     * @param fileName - имя файла
     * @return List со строками
     */
    private static List<String> readFileLines(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Подсчет слов в файле fileName
     *
     * Старая Сложность - O(Z + P^2)
     * Новая Сложность - O(Z + Z + P * log(P)) = O(Z + P * log(P))
     * @param fileName
     * @return ArrayList пар (Слово, кол-во совпадений) отсортированных по словам
     */
    private static List<Map.Entry<String, Integer>>  wordFrequenciesForFile(String fileName) {

        List<String> lineList = readFileLines(fileName);
        System.out.printf("lines count at %s: %d\n",new File(fileName).getName(), lineList.size());

        List<String> wordList = getWordsFromLineList(lineList);                     // 1 - раз,(Z) - сложность
        System.out.printf("word count at %s: %d\n",new File(fileName).getName(), wordList.size());

        List<Map.Entry<String, Integer>> freqMapping = countFrequency(wordList);    // 1 - раз, Z  - сложность

        return mergeSort(freqMapping);                                              // 1 - раз, P * log(P)
    }

    /**
     * Возвращает ArrayList со словами строк из списка lines
     *
     * Cтарая Сложность = O(Z^2)
     * Новая Сложность = O(1 + l + (l * k * w) + (k * l) + kl + l + 1) = O(k * l) = O(Z)
     * @param lines
     * @return
     */
    private static  List<String> getWordsFromLineList(List<String> lines) {
        ArrayList<String> wordList = new ArrayList<>();                         // 1 - раз, 1 - стоимость
        for (String line: lines) {                                              // l - раз, 1 - стоимость
            List<String> wordsInLine = getWordsFromString(line);                // l' - раз, n - стоимость
            // Заменяем метод по слиянию списков в 3-й, на метод
            wordList.addAll(wordsInLine);                                       // l' - раз, стоимость = k
        }
        return wordList;                                                        // 1 - раз, 1 - стоимость
    }

    /**
     * Разбивает строку на слова и возвращает ArrayList со словами в строке,
     * где слово - буквенно-числовое значение длины w
     *
     * Сложность - O(n).
     * @param line
     * @return ArrayList со словами
     */
    private static List<String> getWordsFromString(String line) {
        ArrayList<String> wordList = new ArrayList<>();             // 1 - раз, 1 - сложность
        StringBuilder characterList = new StringBuilder();          // 1 - раз, 1 - сложность
        for (int i = 0; i < line.length(); i++) {                   // n - раз, 1 - сложность
            char c = line.charAt(i);                                // n - раз, 1 - сложность
            if (Character.isLetterOrDigit(c)) {                     // n - раз, 1 - сложность
                characterList.append(Character.toLowerCase(c));     // n * w /(w + 1) - раз, где w - кол-во числобуквенных символов(длина слова в тексте), 1 - пробел, 1 - сложность

            } else if (characterList.length() > 0) {                // Встретили пробел и если есть символы значит это слова и пмещаем их в список слов  n * (1 - w /(w + 1)) - раз = n/(w+1)
                String word = characterList.toString();             // 1 - стоимость операции, N/(w+1) - раз
                wordList.add(word);                                 // 1 - стоимость операции, N/(w+1) - раз
                characterList.setLength(0);                         // 1 - стоимость операции, N/(w+1) - раз
            }
        }

        if (characterList.length() > 0) {                           // 1 - стоимость операции, 1 - раз
            String word = characterList.toString();                 // 1 - стоимость операции, 1 - раз
            wordList.add(word);                                     // 1 - стоимость операции, 1 - раз
            characterList.setLength(0);                             // 1 - стоимость операции, 1 - раз
        }


        return wordList;                                            // 1 - стоимость операции, 1 - раз
    }

    /**
     * Возвращает список ArrayList с парами (слово, кол-во в тексте)
     *
     * Старая Сложность - O(Z * P)
     * Новая Сложность - O(Z)
     * @param wordList
     * @return
     */
    private static List<Map.Entry<String, Integer>> countFrequency(List<String> wordList) {
        // Меняем алгоритм составления вектора, путем замены списка Хэш-таблицей
        HashMap<String, Integer> L = new HashMap<>();               // 1 - раз, 1 - стоимость
        for (String newWord: wordList) {                            // Z - раз, 1 - стоимость
            if (L.containsKey(newWord)) {                           // Z - раз, 1 - стоимость (containsKey - O(1))
                L.put(newWord, L.get(newWord) + 1);                 // Z - раз, 1 - стоимость (put - O(1) в основном)
            } else {
                L.put(newWord, 1);                                  // Z - раз, 1 - стоимость (put - O(1) в основном)
            }
        }

        return new ArrayList<>(L.entrySet());                       // 1 - раз, Z - стоимость
    }

    /**
     * Сортировка вставками списка пар по ключу
     *
     * Сложность - O(P^2)
     * @param A
     */
    private static void insertionSort(List<Map.Entry<String, Integer>> A) {
        for (int j = 1; j < A.size(); j++) {
            Map.Entry<String, Integer>  key = A.get(j);
            int i = j - 1;
            while (i >= 0 && key.getKey().compareTo(A.get(i).getKey()) < 0) {
                A.set(i + 1, A.get(i));
                i--;
            }
            A.set(i + 1, key);
        }
    }

    /**
     * Возвращает угол между векторами в радианах
     *
     * Старая Сложность - O((P1)^2 + (P2)^2 + P1 * P2)
     * Новая Сложность - O((P1) + (P2) + P) = O((P1) + (P2))
     * @param L1
     * @param L2
     * @return
     */
    private static double vectorAngle(List<Map.Entry<String,Integer>> L1, List<Map.Entry<String,Integer>> L2) {
        long numerator = innerProduct(L1, L2);                                     // 1 - раз, сложность - O(P)
        double denominator = Math.sqrt(innerProduct(L1,L1) * innerProduct(L2,L2)); // 1 - раз, сложность - O((P1) + (P2) + 1)
        return Math.acos(numerator / denominator);
    }


    /**
     * Возвращает скалаярное произведение векторов
     *
     * Старая Сложность - O(P1 * P2)
     * Новая Сложность - O(P)
     * @param L1
     * @param L2
     * @return
     */
    private static long innerProduct(List<Map.Entry<String,Integer>> L1, List<Map.Entry<String,Integer>> L2) {
        long sum = 0;                                                           // 1 - раз, 1 - стоимость

        int i = 0;                                                              // 1 - раз, 1 - стоимость
        int j = 0;                                                              // 1 - раз, 1 - стоимость

        // Заменяем алгоритм подсчета скаларного произведения методом 2-х указателей,
        // т.к. массивы отсортированы и все значения уникальны
        while (i < L1.size() && j < L2.size()) {                                // P - раз, 1 - стоимость
            if (L1.get(i).getKey().compareTo(L2.get(j).getKey()) < 0) {
                i++;
            } else if (L1.get(i).getKey().compareTo(L2.get(j).getKey()) > 0) {
                j++;
            } else {
                sum += L1.get(i).getValue() * L2.get(j).getValue();
                i++;
                j++;
            }
        }
        return sum;
    }

    /**
     * Сортировка слиянием
     *
     * Сложность - Сложность O(P * log(P))
     * @param list
     * @return
     */
    public static List<Map.Entry<String, Integer>> mergeSort(List<Map.Entry<String, Integer>> list) {
        if (list.size() < 2) {
            return list;
        }

        int middle = ((list.size()) / 2);
        List<Map.Entry<String, Integer>> left = list.subList(0, middle);
        List<Map.Entry<String, Integer>> right = list.subList(middle, list.size());

        left = mergeSort(left);
        right = mergeSort(right);

        List<Map.Entry<String, Integer>> merged = new ArrayList<>();

        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            Map.Entry<String, Integer> leftEntry = left.get(i);
            Map.Entry<String, Integer> rightEntry = right.get(j);

            if (leftEntry.getKey().compareTo(rightEntry.getKey()) < 0) {
                merged.add(leftEntry);
                i++;
            } else {
                merged.add(rightEntry);
                j++;
            }
        }
        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));
        return merged;
    }

}
