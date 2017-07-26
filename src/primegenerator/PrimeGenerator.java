package primegenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author drewneely
 */
public class PrimeGenerator extends Thread {

    static String fileName = "../primes.txt";
    static HashMap<Integer, ArrayList<Integer>> primesToAdd = new HashMap<Integer, ArrayList<Integer>>();
    final static int numbersPerThread = 1000;
    final static int numberOfThreads = 300;
    final static int numberOfIterations = 200;
    int indexInIteration;
    int toTestLowInclusive;
    int toTestHighExclusive;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        int start = 0;
        while(scan.hasNext()) {
            start = scan.nextInt();
        }
        start++;
        System.out.println("starting at " + start);
        for(int a = 0; a < numberOfIterations; a++) {
            PrimeGenerator[] threads = new PrimeGenerator[numberOfThreads];
            for(int i = 0; i < threads.length; i++) {
                threads[i] = new PrimeGenerator(i, start, start + numbersPerThread);
                threads[i].start();
                System.out.println("Thread " + i + " started");
                start += numbersPerThread;
            }
            System.out.println(java.lang.Thread.activeCount());
            for(int i = 0; i < threads.length; i++) {
                try {
                    threads[i].join();
                    System.out.println("Thread " + i + " finsished");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Iteration complete\n\n");
            String primes = "";
            for(int i = 0; i < primesToAdd.size(); i++) {
                ArrayList<Integer> set = primesToAdd.get(i);
                for(int q = 0; q < set.size(); q++) {
                    primes += " " + set.get(q);
                }
            }
            addPrimes(primes);
            primesToAdd.clear();
        }
        System.out.println("numbers checked for primality: " + numberOfIterations * numberOfThreads * numbersPerThread);
    }
    
    public PrimeGenerator(int i, int low, int high) {
        this.indexInIteration = i;
        this.toTestLowInclusive = low;
        this.toTestHighExclusive = high;
    }

    @Override
    public void run() {
        ArrayList<Integer> discovered = new ArrayList<Integer>();
        for(int i = toTestLowInclusive; i < toTestHighExclusive; i++) {
            try {
                if(isPrime(i)) {
                    discovered.add(i);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        primesToAdd.put(indexInIteration, discovered);
    }

    /*
    *
    * cannot be used to test 
     */
    public static boolean isPrime(int n) throws FileNotFoundException {
        if (n == 2) {
            return true;
        } else if(n % 2 == 0) {
            return false;
        }
        Scanner scan = new Scanner(new File(fileName));
        int sqrt = (int) Math.sqrt(n);
        boolean isPrime = true;
        boolean isComplete = false;
        do {
            int next = scan.nextInt();
            if (n % next == 0) {
                isPrime = false;
                isComplete = true;
            } else if (next > sqrt) {
                isComplete = true;
            }
        } while (!isComplete);
        scan.close();
        return isPrime;
    }

    public static void addPrimes(String str) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            bw.write(" " + str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
