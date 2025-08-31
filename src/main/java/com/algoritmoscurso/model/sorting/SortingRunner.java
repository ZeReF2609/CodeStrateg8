package com.algoritmoscurso.model.sorting;

import com.algoritmoscurso.interfaces.ISortingAlgorithm;
import java.util.*;

public class SortingRunner {
    public static class Result {
        public final String name;
        public final int[] sorted;
        public final long timeNs;
        public Result(String name, int[] sorted, long timeNs) { this.name = name; this.sorted = sorted; this.timeNs = timeNs; }
    }

    public static List<Result> runAll(List<ISortingAlgorithm> algs, int[] data) {
        List<Result> out = new ArrayList<>();
        for (ISortingAlgorithm alg : algs) {
            int[] copy = Arrays.copyOf(data, data.length);
            long t0 = System.nanoTime();
            int[] sorted = alg.sort(copy);
            long t1 = System.nanoTime();
            out.add(new Result(alg.getName(), sorted, t1 - t0));
        }
        return out;
    }
}
