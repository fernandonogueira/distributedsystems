package distributedsystems.streams;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Fernando Nogueira
 * @since 9/12/16 6:43 PM
 */
public class MainInputStream {

    public static void main(String[] args) throws IOException {


//        System.out.println("\n".getBytes());

        System.out.println("Starting...");
        InvertInputStream iis = new InvertInputStream(System.in);

        Scanner scanner = new Scanner(iis);

        String line = scanner.nextLine();
        System.out.println("Read line: " + line);

//        byte[] b = new byte[1024];
//        System.out.println("Reading...");

//        iis.read(b);

//        System.out.println("Printing...");
//        System.out.println(new String(b));
    }

}
