package com.algoritmoscurso.model.dp;

import java.util.Collections;
import java.util.List;

public class DPResult {
    private final int bestValue;
    private final List<int[]> snapshots; // flattened matrices or tables
    private final List<Integer> selectedItems; // for knapsack: indices

    public DPResult(int bestValue, List<int[]> snapshots, List<Integer> selectedItems) {
        this.bestValue = bestValue;
        this.snapshots = snapshots == null ? Collections.<int[]>emptyList() : snapshots;
        this.selectedItems = selectedItems == null ? Collections.<Integer>emptyList() : selectedItems;
    }

    public int getBestValue() { return bestValue; }
    public List<int[]> getSnapshots() { return snapshots; }
    public List<Integer> getSelectedItems() { return selectedItems; }
}
