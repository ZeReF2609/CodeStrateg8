package com.algoritmoscurso.model.sorting.algorithms;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.Arrays;

public class HeapSort implements ISortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] a = Arrays.copyOf(input, input.length);
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(a, n, i);
        for (int i = n - 1; i > 0; i--) {
            int tmp = a[0]; a[0] = a[i]; a[i] = tmp;
            heapify(a, i, 0);
        }
        return a;
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest]) largest = l;
        if (r < n && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            int tmp = arr[i]; arr[i] = arr[largest]; arr[largest] = tmp;
            heapify(arr, n, largest);
        }
    }

    @Override
    public String getName() { return "HeapSort"; }

    @Override
    public String getTimeComplexity() { return "O(n log n)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }

    @Override
    public String getDescription() { return "Heap sort"; }
}
