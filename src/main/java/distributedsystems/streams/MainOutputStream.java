package distributedsystems.streams;

import java.io.IOException;

public class MainOutputStream {

    public static void main(String[] args) {
        InvertOutputStream iv = new InvertOutputStream(System.out);
        try {
            String outputString = "1 2 3 4 5";
            iv.write(outputString.getBytes());

            iv.flush();
            iv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}