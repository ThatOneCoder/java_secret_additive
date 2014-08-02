<?php
/**
 * Heath Dinkins
 * 2014-07-29
 *
 * Problem:
 * You are given a function 'secret()' that accepts a single integer parameter
 * and returns an integer. In PHP, write a program that determines if this function
 * is an additive function [ secret(x+y) = secret(x) + secret(y) ] for all prime numbers
 * under N where N is a given integer.
 *
 * Notes:
 * PHP is loosely typed so secret does not explicitly accept only integers. I do, however,
 * cast the return to integer.
 * To guard against a numeric string being falsely treated as a string, I attempt to cast
 * the given limit to an integer.
 *
 * Prime Numbers:
 * A prime number (or a prime) is a natural number greater than 1 that has no positive divisors other than 1 and itself.
 *
 * Sieve of Eratosthenes:
 * In mathematics, the sieve of Eratosthenes, one of a number of prime number sieves, is a simple,
 * ancient algorithm for finding all prime numbers up to any given limit.
 *
 */

$limit = 0;
$input = '';

if (isset($argv[1])) {
    $input = $argv[1];
} else {
    $input = $_GET['limit'];
}

$limit = (int) $input; // strings equate to an integer value 0

if ($limit < 2) {
    echo "Error: $input is not a valid integer. A single integer value greater than 1 must be entered.";
    exit;
}

$primes = sieveOfEratosthenes($limit);

if (testSecret($primes)) {
    echo "The function secret() is an additive function for all prime number under $limit.";
} else {
    echo "The function secret() is not an additive function for all prime number under $limit.";
}


/**
 * The function secret() accepts a single parameter and returns an integer.
 *
 * @param $number
 * @return int
 */
function secret($number) {
    return (int) $number;
}


/**
 * The function sieveOfEratosthenes() accepts a single integer that is the given limit
 * to the Sieve of Eratosthenes sieve. This sieve returns all prime numbers
 * that are less than the given limit in an array.
 *
 * @param $limit
 * @return array
 */
function sieveOfEratosthenes($limit) {
    $intList = array_fill(2, $limit, 1); // initially assume all are primes
    $primes = array();
    $i = 2;

    while ($i < $limit) {
        if ($intList[$i] == 1) {
            $primes[] = $i;

            $j = $i;
            while ($j < $limit) {
                $intList[$j] = 0; // number fails prime test
                $j += $i;
            }
        }
        $i++;
    }
    return $primes;
}


/**
 * The function testSecret() accepts an array of prime numbers
 * and checks if the additive function [ secret(x+y) = secret(x) + secret(y) ]
 * remains true for each prime number. It returns true if all primes pass the
 * comparison. It returns false if any set of prime numbers fails the comparison.
 *
 * @param $primes
 * @return bool
 */
function testSecret($primes) {
    $passed = array();

    foreach ($primes as $x) {
        foreach ($primes as $y) {
            if ( ! in_array($y, $passed)) {
                $sumLeft = ($x + $y);
                $secretLeft = secret($sumLeft); // left side - secret(x+y)

                $secretX = secret($x);
                $secretY = secret($y);
                $secretRight = ($secretX + $secretY); // right side - secret(x) + secret(y)

                if ($secretLeft != $secretRight) {
                    return false;
                }
            }
        }
        $passed[] = $x;
    }
    return true;
}
