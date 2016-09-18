package unifor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    public static final String PREFIX = "/Users/fernandonogueira/Temp/SD/";

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            return;
        }

        List<String> list = new ArrayList<>();
        for (String arg : args) {
            list.add(PREFIX + arg);
        }

        long startSync = System.currentTimeMillis();
        list.forEach(Zipper::zip);
        long syncEnd = System.currentTimeMillis();

        System.out.println("Sync took: " + (syncEnd - startSync) + "ms");


        System.out.println("Starting all threads...");
        long allThreadsStart = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<>();
        list.forEach(file -> {
            Thread t = new Thread(new ZipperRunnable(file));
            threadList.add(t);
            t.start();
        });

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("All threads completed! Took: "
                + (System.currentTimeMillis() - allThreadsStart) + "ms");
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
