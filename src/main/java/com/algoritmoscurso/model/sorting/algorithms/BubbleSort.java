package com.algoritmoscurso.model.sorting.algorithms;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.Arrays;

public class BubbleSort implements ISortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] a = Arrays.copyOf(input, input.length);
        int n = a.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j]; a[j] = a[j + 1]; a[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return a;
    }

    @Override
    public String getName() { return "BubbleSort"; }

    @Override
    public String getTimeComplexity() { return "O(n^2)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }

    @Override
    public String getDescription() { return "Simple bubble sort"; }
}
