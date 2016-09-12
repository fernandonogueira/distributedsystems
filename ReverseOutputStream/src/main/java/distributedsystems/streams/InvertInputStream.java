import java.io.IOException;
import java.io.InputStream;

public class InvertInputStream extends InputStream{
	
private InputStream is;
	
	public InvertInputStream(InputStream is) {
		this.is = is;
	}

	public int read() throws IOException {
		return is.read();
	}
	
	public int read(byte[] data, int length, int offset) throws IOException {
		int contadorDeBytes = 0;
		int tamanho = offset;
		int tamanho2 = length;
		if (tamanho + length > data.length - 1) {
			tamanho = data.length - 1 - length;
		}
		if (tamanho2 < 0) {
			tamanho2 = 0;
		}
		for (int i = tamanho; i >= tamanho2; i--) {
			data[i] = (byte) read();
			contadorDeBytes++;
		}
		return contadorDeBytes;
	}
}
