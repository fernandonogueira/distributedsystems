package distributedsystems;

import java.io.IOException;
import java.io.InputStream;

public class ReverseInputStream extends InputStream {

    private final InputStream inputStream;

    public ReverseInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int read() throws IOException {
        return 0;
    }

    private synchronized void readAllBytes() throws IOException {
    }

    @Override
    public int read(byte[] b) throws IOException {


        return super.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }
}
