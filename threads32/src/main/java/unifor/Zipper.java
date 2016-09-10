package unifor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    public static final String PREFIX = "/Users/fernandonogueira/Temp/SD/";

    private static final boolean parallel = true;

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            return;
        }

        List<String> list = new ArrayList<>();
        for (String arg : args) {
            list.add(PREFIX + arg);
        }

        long start = System.currentTimeMillis();
        if (!parallel) {
            list.forEach(Zipper::zip);
        } else {
            list.parallelStream().forEach(Zipper::zip);
        }
        long end = System.currentTimeMillis();
        System.out.println("Took: " + (end - start) + "ms");

    }

    private static void zip(String file) {
        try {
            BufferedInputStream bis;
            bis = new BufferedInputStream(new FileInputStream(file));

            ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file + ".zip")));
            ZipEntry zipEntry = new ZipEntry(file);
            output.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024]; // Adjust if you want
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
