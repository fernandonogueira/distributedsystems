package unifor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static unifor.CaesarCipherWriter.alphabet;

/**
 * @author Fernando Nogueira
 * @since 9/9/16 6:38 PM
 */
public class CaesarCipherReader extends Reader {


    private final int shift;
    private final InputStreamReader reader;

    public CaesarCipherReader(InputStreamReader reader, int shift) {
        this.shift = (shift % 26);
        this.reader = reader;
    }

    private Character unshiftChar(char c) {

        boolean upperCase = Character.isUpperCase(c);

        c = Character.toLowerCase(c);

        char newChar;
        if (c < 'a') {
            newChar = alphabet.get((alphabet.indexOf(c) + (26 - shift)) % 26);
        } else {
            int idx = (alphabet.indexOf(c) - shift) % 26;
            if (idx < 0) {
                newChar = alphabet.get(26 + idx);
            } else {
                newChar = alphabet.get(idx);
            }
        }

        return upperCase ? Character.toUpperCase(newChar) : newChar;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (len <= 0) {
            return -1;
        }

        char[] newArray = new char[len];

        int readTotal = reader.read(newArray, off, len);

        List<Character> resp = new ArrayList<>();

        for (int i = 0; i < readTotal; i++) {
            char c = newArray[i];
            if (alphabet.contains(c)
                    || alphabet.contains(Character.toLowerCase(c))) {
                resp.add(unshiftChar(c));
            } else {
                resp.add(c);
            }
        }

        int size = resp.size();

        for (int i = 0; i < size; i++) {
            cbuf[i] = resp.get(i);
        }

        return readTotal;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
