package com.algoritmoscurso.model.sorting.algorithms;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.Arrays;

public class InsertionSort implements ISortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] a = Arrays.copyOf(input, input.length);
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
        return a;
    }

    @Override
    public String getName() { return "InsertionSort"; }

    @Override
    public String getTimeComplexity() { return "O(n^2)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }

    @Override
    public String getDescription() { return "Insertion sort"; }
}
