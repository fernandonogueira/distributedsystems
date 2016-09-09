package unifor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fernando Nogueira
 * @since 9/9/16 6:12 PM
 */
public class CaesarCipherWriter extends Writer {

    private final OutputStreamWriter output;

    public static List<Character> alphabet = new ArrayList<>();

    static {
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
                , 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'
                , 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        for (char c : alph) {
            alphabet.add(c);
        }
    }

    private final int shift;

    public CaesarCipherWriter(OutputStreamWriter output, int shift) {
        this.output = output;
        this.shift = shift % 26;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        if (len <= 0) {
            return;
        }

        List<Character> resp = new ArrayList<>();
        for (char c : cbuf) {
            if (alphabet.contains(c) || alphabet
                    .contains(Character.toLowerCase(c))) {
                resp.add(shiftChar(c));
            } else {
                resp.add(c);
            }
        }

        int size = resp.size();
        char[] respArray = new char[size];

        for (int i = 0; i < size; i++) {
            respArray[i] = resp.get(i);
        }

        output.write(respArray);

    }

    private Character shiftChar(char c) {

        boolean upperCase = Character.isUpperCase(c);

        c = Character.toLowerCase(c);

        char newChar;
        if (c > 'z') {
            newChar = alphabet.get((alphabet.indexOf(c) - (26 - shift)) % 26);
        } else {
            newChar = alphabet.get((alphabet.indexOf(c) + shift) % 26);
        }

        return upperCase ? Character.toUpperCase(newChar) : newChar;
    }


    @Override
    public void flush() throws IOException {
        output.flush();
    }

    @Override
    public void close() throws IOException {
        output.close();
    }

}
