package com.company;

import com.company._2.DocumentDistanceProblem.Algorithm6;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonTest {

    public static void main(String[] args) {

        List<Map.Entry<String, Integer>> sorted = Algorithm6.mergeSort(getData(15));
        System.out.println(sorted);
    }

    private static List<Map.Entry<String, Integer>> getData(int length) {
        List<Map.Entry<String, Integer>> data = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            int random = (int) (Math.random() * 100);
            System.out.print(random + " ");
            data.add(new AbstractMap.SimpleEntry<>("" + random, random));
        }
        System.out.println("\n");
        return data;
    }
}
