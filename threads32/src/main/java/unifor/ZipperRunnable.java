package unifor;

import java.io.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Fernando Nogueira
 * @since 9/16/16 7:11 PM
 */
public class ZipperRunnable implements Runnable {

    private final String file;
    private final String uuid;

    public ZipperRunnable(String file) {
        this.file = file;
        this.uuid = UUID.randomUUID().toString();
    }

    private void zip() {
        long start = System.currentTimeMillis();
        try {
            System.out.println("Starting async zipper. Id: " + uuid);

            BufferedInputStream bis;
            bis = new BufferedInputStream(new FileInputStream(file));

            ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file + ".zip")));
            ZipEntry zipEntry = new ZipEntry(file);
            output.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finished zipping file with id: "
                    + uuid + ". Took: " + (System.currentTimeMillis() - start) + "ms");
        }
    }

    @Override
    public void run() {
        zip();
    }
}
