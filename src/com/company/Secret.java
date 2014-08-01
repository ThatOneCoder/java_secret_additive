package com.company;
import org.apache.commons.lang.math.NumberUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Heath Dinkins
 * 2014-07-29
 *
 * Problem:
 * You are given a function 'secret()' that accepts a single integer parameter
 * and returns an integer. In Java, write a program that determines if this function
 * is an additive function [ secret(x+y) = secret(x) + secret(y) ] for all prime numbers
 * under N where N is a given integer.
 *
 * Prime Numbers:
 * A prime number (or a prime) is a natural number greater than 1 that has no positive divisors other than 1 and itself.
 *
 * Sieve of Eratosthenes:
 * In mathematics, the sieve of Eratosthenes, one of a number of prime number sieves, is a simple,
 * ancient algorithm for finding all prime numbers up to any given limit.
 *
 */

public class Secret {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a single integer value greater than 1: ");
        int limit = 0;
        String input;

        input = scanner.nextLine();
        boolean isInputNum = NumberUtils.isDigits(input);
        if (isInputNum) { limit = Integer.parseInt(input);}

        if (limit < 2) {
            System.out.println("Error: " + input + " is invalid input. A single integer value greater than 1 must be entered.");
            System.exit(0);
        }

        Integer[] primes = sieveOfEratosthenes(limit);

        System.out.println("\nPrimes under " + limit + ": " + Arrays.toString(primes));

        if (testSecret(primes)) {
            System.out.println("The function secret() is an additive function for all prime number under " + limit + ".");
        } else {
            System.out.println("The function secret() is not an additive function for all prime number under " + limit + ".");
        }
    }


    /**
     * The function secret() accepts a single integer as a parameter and returns an integer.
     *
     * @param number
     * @return
     */
    public static int secret(int number) {
        return number;
    }


    /**
     * The function sieveOfEratosthenes() accepts a single integer that is the given limit
     * to the Sieve of Eratosthenes sieve. This sieve returns all prime numbers
     * that are less than the given limit in an array.
     *
     * @param limit
     * @return
     */
    public static Integer[] sieveOfEratosthenes(int limit) {
        int[] intList = new int[limit];
        ArrayList<Integer> primes = new ArrayList<>();
        Arrays.fill(intList, 1); // initially assume all are primes
        int i = 2;

        while (i < limit) {
            if (intList[i] == 1) {
                primes.add(i);

                int j = i;
                while (j < limit) {
                    intList[j] = 0; // number fails prime test
                    j += i;
                }
            }
            i++;
        }
        return primes.toArray(new Integer[primes.size()]);
    }


    /**
     * The function testSecret() accepts an array of prime numbers
     * and checks if the additive function [ secret(x+y) = secret(x) + secret(y) ]
     * remains true for each prime number. It returns true if all primes pass the
     * comparison. It returns false if any set of prime numbers fails the comparison.
     *
     * @param primes
     * @return
     */
    private static boolean testSecret(Integer[] primes) {
        ArrayList<Integer> passed = new ArrayList<>();

        for (Integer x : primes) {
            for (Integer y : primes) {
                int sumLeft = (x + y);
                int secretLeft = secret(sumLeft); // left side - secret(x+y)

                int secretX = secret(x);
                int secretY = secret(y);
                int secretRight = (secretX + secretY); // right side - secret(x) + secret(y)

                if (secretLeft != secretRight) {
                    return false;
                }
            }
            passed.add(x);
        }
        return true;
    }
}
