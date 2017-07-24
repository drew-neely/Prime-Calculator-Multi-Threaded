package primegenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author drewneely
 */
public class PrimeGenerator {

    static String fileName = "../primes.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        int last = 0;
        while(scan.hasNext()) {
            last = scan.nextInt();
        }
        System.out.println(last);
        for(int i = last + 2; true; i += 2) {
            if(isPrime(i)) {
                addPrime(i);
            }
        }
    }

    /*
    *
    * cannot be used to test 
     */
    public static boolean isPrime(int n) throws FileNotFoundException {
        if (n == 2) {
            return true;
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

    public static void addPrime(int prime) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            bw.write(" " + prime);
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
