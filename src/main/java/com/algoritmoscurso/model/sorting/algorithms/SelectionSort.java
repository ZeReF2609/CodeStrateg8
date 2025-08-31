package com.algoritmoscurso.model.sorting.algorithms;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.Arrays;

public class SelectionSort implements ISortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] a = Arrays.copyOf(input, input.length);
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) min = j;
            }
            int tmp = a[min]; a[min] = a[i]; a[i] = tmp;
        }
        return a;
    }

    @Override
    public String getName() { return "SelectionSort"; }

    @Override
    public String getTimeComplexity() { return "O(n^2)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }

    @Override
    public String getDescription() { return "Selection sort"; }
}
