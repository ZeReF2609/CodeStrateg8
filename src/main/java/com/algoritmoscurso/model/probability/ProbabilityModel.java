package com.algoritmoscurso.model.probability;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

/**
 * Small model providing probabilistic algorithms for Semana 6.
 */
public class ProbabilityModel {
    private final Random rng = new Random();

    // Estimate Pi using Monte Carlo: ratio of points inside quarter circle
    public double estimatePi(int samples) {
        long inside = 0;
        for (int i = 0; i < samples; i++) {
            double x = rng.nextDouble();
            double y = rng.nextDouble();
            if (x * x + y * y <= 1.0) inside++;
        }
        return 4.0 * ((double) inside / (double) samples);
    }

    /**
     * Parse a simple polynomial string like "x^3+2x-3" into coefficients array where
     * coeffs[i] is the coefficient for x^i. Supports terms separated by + or -, implicit
     * coefficients like "x" and powers with ^.
     */
    public double[] parsePolynomial(String expr) {
        if (expr == null) throw new IllegalArgumentException("Expresión vacía");
        String s = expr.replace(" ", "");
        // normalize: replace leading - with 0- and then replace - with +-
        if (s.startsWith("-")) s = "0" + s;
        s = s.replace("-", "+-");
        String[] parts = s.split("\\+");
        Map<Integer, Double> terms = new HashMap<>();
        for (String p : parts) {
            if (p == null || p.isEmpty()) continue;
            // token can be like "-x^3", "2x", "5", "x"
            if (p.indexOf('x') >= 0) {
                int idx = p.indexOf('x');
                String coeffStr = p.substring(0, idx);
                double coeff;
                if (coeffStr.equals("") || coeffStr.equals("+")) coeff = 1.0;
                else if (coeffStr.equals("-")) coeff = -1.0;
                else coeff = Double.parseDouble(coeffStr);
                int power = 1;
                if (idx + 1 < p.length() && p.charAt(idx + 1) == '^') {
                    String powStr = p.substring(idx + 2);
                    power = Integer.parseInt(powStr);
                }
                Double cur = terms.get(power);
                terms.put(power, (cur == null ? 0.0 : cur) + coeff);
            } else {
                // constant term
                double c = Double.parseDouble(p);
                Double cur = terms.get(0);
                terms.put(0, (cur == null ? 0.0 : cur) + c);
            }
        }
        int maxPow = 0;
        for (Integer k : terms.keySet()) if (k > maxPow) maxPow = k;
        double[] coeffs = new double[maxPow + 1];
        for (Map.Entry<Integer, Double> e : terms.entrySet()) {
            coeffs[e.getKey()] = e.getValue();
        }
        return coeffs;
    }

    public double evaluatePolynomial(double[] coeffs, double x) {
        double sum = 0.0;
        double xp = 1.0;
        for (int i = 0; i < coeffs.length; i++) {
            sum += coeffs[i] * xp;
            xp *= x;
        }
        return sum;
    }

    /** Monte Carlo integration for polynomials represented by coefficients (coeff[i] for x^i). */
    public double monteCarloIntegratePolynomial(double[] coeffs, double a, double b, int samples) {
        if (samples <= 0) throw new IllegalArgumentException("El número de muestras debe ser > 0");
        double sum = 0.0;
        for (int i = 0; i < samples; i++) {
            double x = a + rng.nextDouble() * (b - a);
            sum += evaluatePolynomial(coeffs, x);
        }
        return (b - a) * (sum / (double) samples);
    }

    /** Analytic definite integral for a polynomial given by coeffs on [a,b]. */
    public double analyticIntegralPolynomial(double[] coeffs, double a, double b) {
        double sum = 0.0;
        for (int i = 0; i < coeffs.length; i++) {
            double c = coeffs[i] / (i + 1);
            double term = c * (Math.pow(b, i + 1) - Math.pow(a, i + 1));
            sum += term;
        }
        return sum;
    }

    // Miller-Rabin primality test (deterministic for 64-bit using a few bases could be implemented, we'll use probabilistic)
    public boolean isProbablePrime(long n, int rounds) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) { d >>= 1; s++; }

        for (int i = 0; i < rounds; i++) {
            long a = 2 + (Math.abs(rng.nextLong()) % (n - 3));
            if (!millerRabinCheck(a, s, d, n)) return false;
        }
        return true;
    }

    private boolean millerRabinCheck(long a, int s, long d, long n) {
        long x = modPow(a, d, n);
        if (x == 1 || x == n - 1) return true;
        for (int r = 1; r < s; r++) {
            x = mulMod(x, x, n);
            if (x == n - 1) return true;
        }
        return false;
    }

    private long mulMod(long a, long b, long mod) {
        // avoid overflow using java builtins
        return (a * b) % mod;
    }

    private long modPow(long base, long exp, long mod) {
        long result = 1 % mod;
        long b = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = mulMod(result, b, mod);
            b = mulMod(b, b, mod);
            exp >>= 1;
        }
        return result;
    }
}
