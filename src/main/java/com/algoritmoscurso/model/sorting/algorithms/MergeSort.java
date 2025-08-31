package com.algoritmoscurso.model.sorting.algorithms;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.Arrays;

public class MergeSort implements ISortingAlgorithm {
    @Override
    public int[] sort(int[] input) {
        int[] a = Arrays.copyOf(input, input.length);
        mergeSort(a, 0, a.length - 1);
        return a;
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++]; else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    @Override
    public String getName() { return "MergeSort"; }

    @Override
    public String getTimeComplexity() { return "O(n log n)"; }

    @Override
    public String getSpaceComplexity() { return "O(n)"; }

    @Override
    public String getDescription() { return "Merge sort"; }
}
