package unifor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CaesarCipher {

    public static void main(String[] args) throws IOException {

        String userHome = System.getProperty("user.home");

        FileWriter fileWriter = new FileWriter(userHome + "/sistemas_dist_test.txt");

        CaesarCipherWriter cipher = new CaesarCipherWriter(fileWriter, 15);
        cipher.write("Deus eh pai...abcdef.. MAIUSCULO. :D RISOS");
        cipher.flush();
        cipher.close();

        FileReader fileReader = new FileReader(userHome + "/sistemas_dist_test.txt");

        CaesarCipherReader decipher = new CaesarCipherReader(fileReader, 15);
        char[] buf = new char[1024];

        int read = decipher.read(buf);

        String str = "";
        for (int i = 0; i < read; i++) {
            if (Character.MIN_VALUE != buf[i]) {
                str += buf[i];
            }
        }

        System.out.println("result: " + str);
    }

}
