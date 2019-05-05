package com.company._2.DocumentDistanceProblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Реализация алгоритма 1 определения document distance
 * из лекций курса введение в алгоритмы и структуры данных.
 * Алгоритм реализован таким образом чтобы иметь такую же сложность
 * как и оригинальная реализация на питоне, поэтому некоторые методы
 * реализованы не самым лучшим образом
 *
 */
public class Algorithm1 {

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
     * Общая сложность = O((Z1)^2 + (Z2)^2), т.к. Z >= P
     * Самый долгий метод - wordFrequenciesForFile !!!
     */
    public static void main(String[] args) {
        Instant start = Instant.now();
        List<Map.Entry<String,Integer>> sortedWordList1 = wordFrequenciesForFile("t2.bobsey.txt"); // 1 - раз, сложность - O(k * (l1)^2 + (Z1)^2 + (P1)^2) = O((Z1)^2)
        List<Map.Entry<String,Integer>> sortedWordList2 = wordFrequenciesForFile("t3.lewis.txt");  // 1 - раз, сложность - O(k * (l2)^2 + (Z2)^2 + (P2)^2) = O((Z2)^2)
        double distance = vectorAngle(sortedWordList1, sortedWordList2);                                    // 1 - раз, сложность - O((P1)^2 + (P2)^2 + P1 * P2) = O((P1)^2 + (P2)^2), т.к. если P1, P2 > 0 => (P1)^2 + (P2)^2 > P1 * P2
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
     * Сложность - O(Z^2 + Z * P + P^2)
     * @param fileName
     * @return ArrayList пар (Слово, кол-во совпадений) отсортированных по словам
     */
    private static List<Map.Entry<String, Integer>>  wordFrequenciesForFile(String fileName) {

        List<String> lineList = readFileLines(fileName);
        System.out.printf("lines count at %s: %d\n",new File(fileName).getName(), lineList.size());

        List<String> wordList = getWordsFromLineList(lineList);                     // 1 - раз,(Z^2) - сложность
        System.out.printf("word count at %s: %d\n",new File(fileName).getName(), wordList.size());

        List<Map.Entry<String, Integer>> freqMapping = countFrequency(wordList);    // 1 - раз Z * P - сложность

        insertionSort(freqMapping);                                                 // 1 - раз P^2 - сложность
        return freqMapping;
    }

    /**
     * Возвращает ArrayList со словами строк из списка lines
     *
     * Сложность = O(1 + l + (l * k * w) + (k * l^2) + kl + l + 1) = O(k * l^2) = O(Z^2),
     * т.к. в каждой строке кол-ве строк примерно одинаково и равно k, но кол-во строк возрастает квадратично =>
     * => Z = k * l возрастает квадратично
     * @param lines
     * @return
     */
    private static  List<String> getWordsFromLineList(List<String> lines) {
        ArrayList<String> wordList = new ArrayList<>();                         // 1 - раз, 1 - стоимость
        for (String line: lines) {                                              // l - раз, 1 - стоимость
            List<String> wordsInLine = getWordsFromString(line);                // l - раз, n - стоимость

            // Т.к. на каждой итерации мы перемещаем элементы списков wordList и wordsInLine в новый список,
            // то стоимость одной операции = (l * k + k + 1), k - слов в строке и l * k слов в документе
            wordList = Stream.concat(wordList.stream(), wordsInLine.stream())   // l - раз, стоимость = l * k + k + 1
                    .collect(Collectors.toCollection(() -> new ArrayList<String>()));
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
        ArrayList<Character> characterList = new ArrayList<>();     // 1 - раз, 1 - сложность
        for (int i = 0; i < line.length(); i++) {                   // n - раз, 1 - сложность
            char c = line.charAt(i);                                // n - раз, 1 - сложность
            if (Character.isLetterOrDigit(c)) {                     // n - раз, 1 - сложность
                characterList.add(c);                               // n * w /(w + 1) - раз, где w - кол-во числобуквенных символов(длина слова в тексте), 1 - пробел
            } else if (characterList.size() > 0) {                  // Встретили пробел и если есть символы значит это слова и пмещаем их в список слов  n * (1 - w /(w + 1)) - раз = n/(w+1)

                String word = characterList.stream()                // w - стоимость операции, т.к. помещаем w символов в строку, n/(w+1) - раз
                        .collect(() -> new StringBuilder(),
                                (s, character) -> s.append(character),
                                (s1, s2) -> s1.append(s2)).toString();
                word = word.toLowerCase();                          // w - стоимость операции, N/(w+1) - раз
                wordList.add(word);                                 // 1 - стоимость операции, N/(w+1) - раз
                characterList = new ArrayList<>();                  // 1 - стоимость операции, N/(w+1) - раз
            }
        }

        if (characterList.size() > 0) {                             // 1 - стоимость операции, 1 - раз
            String word = characterList.stream()                    // 1 - раз, w - стоимость операции
                    .collect(() -> new StringBuilder(),
                            (s, character) -> s.append(character),
                            (s1, s2) -> s1.append(s2)).toString();
            word = word.toLowerCase();
            wordList.add(word);
        }

        return wordList;                                            // 1 - стоимость операции, 1 - раз
    }

    /**
     * Возвращает список ArrayList с парами (слово, кол-во в тексте)
     *
     * Сложность - O(Z * P)
     * @param wordList
     * @return
     */
    private static List<Map.Entry<String, Integer>> countFrequency(List<String> wordList) {
        List<Map.Entry<String, Integer>> L = new ArrayList<>();     // 1 - раз, 1 - стоимость

        MAIN_LOOP: for (String newWord: wordList) {                 // Z - раз, 1 - стоимость

            for (Map.Entry<String, Integer> entry: L) {             // P - раз, 1 - стоимость
                if (entry.getKey().equals(newWord)) {               // Z * P - раз, w - стоимость
                    entry.setValue(entry.getValue() + 1);           // Z - P = Z - раз, т.к. операция в среднем раз за Z шагов, 1 - стоимость
                    continue MAIN_LOOP;                             // Z - P = Z - раз, т.к. операция в среднем раз за Z шагов, 1 - стоимость
                }
            }

            L.add(new AbstractMap.SimpleEntry(newWord, 1));         // P - раз, 1 - стоимость
        }

        return L;                                                   // 1 - раз, 1 - стоимость
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
     * Сложность - O((P1)^2 + (P2)^2 + P1 * P2)
     * @param L1
     * @param L2
     * @return
     */
    private static double vectorAngle(List<Map.Entry<String,Integer>> L1, List<Map.Entry<String,Integer>> L2) {
        long numerator = innerProduct(L1, L2);                                     // 1 - раз, сложность - O(P1 * P2)
        double denominator = Math.sqrt(innerProduct(L1,L1) * innerProduct(L2,L2)); // 1 - раз, сложность - O((P1)^2 + (P2)^2 + 1)
        return Math.acos(numerator / denominator);
    }


    /**
     * Возвращает скалаярное произведение векторов
     *
     * Сложность - O(P1 * P2)
     * @param L1
     * @param L2
     * @return
     */
    private static long innerProduct(List<Map.Entry<String,Integer>> L1, List<Map.Entry<String,Integer>> L2) {
        long sum = 0;

        for (Map.Entry<String, Integer> entry1: L1) {
            for (Map.Entry<String, Integer> entry2: L2) {
                if (entry1.getKey().equals(entry2.getKey())) {
                    sum += entry1.getValue() * entry2.getValue();
                }
            }
        }
        return sum;
    }

}
