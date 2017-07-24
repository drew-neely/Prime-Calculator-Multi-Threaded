
package primegenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author drewneely
 */
public class PrimeGenerator {
    
    static File primesFile = new File("../primes");  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        
    }
    
    public static boolean isPrime(int n) throws FileNotFoundException {
        Scanner scan = new Scanner(primesFile);
        int sqrt = (int) Math.sqrt(n);
        boolean isPrime = true;
        boolean isComplete = false;
        do {
            int next = scan.nextInt();
            if(n % next == 0) {
                isPrime = false;
                isComplete = true;
            } else if(next > sqrt) {
                isComplete = true;
            }
        } while (!isComplete);
        return isPrime;
    }
    
    
    
}
