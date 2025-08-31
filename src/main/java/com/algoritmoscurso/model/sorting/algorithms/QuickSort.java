package com.algoritmoscurso.model.sorting.algorithms;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.Arrays;

public class QuickSort implements ISortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] a = Arrays.copyOf(input, input.length);
        quickSort(a, 0, a.length - 1);
        return a;
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
        }
        int tmp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }

    @Override
    public String getName() { return "QuickSort"; }

    @Override
    public String getTimeComplexity() { return "O(n log n)"; }

    @Override
    public String getSpaceComplexity() { return "O(log n)"; }

    @Override
    public String getDescription() { return "Quick sort"; }
}
