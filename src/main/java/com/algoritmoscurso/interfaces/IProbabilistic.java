package com.algoritmoscurso.interfaces;

/**
 * Interface for probabilistic algorithms provided by the model.
 */
public interface IProbabilistic {
    /** Estimate Pi using Monte Carlo with given samples */
    double estimatePi(int samples);

    /** Miller-Rabin probabilistic primality test */
    boolean isProbablePrime(long n, int rounds);
}
