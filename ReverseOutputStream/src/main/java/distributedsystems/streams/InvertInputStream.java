package distributedsystems.streams;

import java.io.IOException;
import java.io.InputStream;

public class InvertInputStream extends InputStream {

    private InputStream is;

    public InvertInputStream(InputStream is) {
        this.is = is;
    }

    public int read() throws IOException {
        return is.read();
    }

    public int read(byte[] data, int length, int offset) throws IOException {

        byte[] auxData = new byte[data.length];

        int readBytes = is.read(auxData, length, offset);

        for (int i = readBytes - 2; i >= 0; i--) {
            if (auxData[i] == "\n".getBytes()[0]) continue;
            data[readBytes - (i + 2)] = auxData[i];
        }

        data[readBytes - 1] = "\n".getBytes()[0];

        return readBytes;
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

}
