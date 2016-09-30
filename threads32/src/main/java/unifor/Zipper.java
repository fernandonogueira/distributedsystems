package unifor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    public static final String PREFIX = "/opt/test-files/";

    public static void main(String[] args) throws IOException {

        if (args == null || args.length == 0) {
            return;
        }

        long maxMemory = Runtime.getRuntime().maxMemory();
        int cores = Runtime.getRuntime().availableProcessors();
        boolean parallel = Boolean.parseBoolean(args[0]);
        boolean binary = Boolean.parseBoolean(args[1]);
        String fileSize = args[2];

        String filesFolder = args[3];

        List<String> list = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(filesFolder))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    list.add(filePath.toString());
                }
            });
        }

//        for (int i = 3; i < args.length; i++) {
//            list.add(PREFIX + (binary ? "/binary/" : "text") + args[i]);
//        }

        if (!parallel) {
            long startSync = System.currentTimeMillis();
            list.forEach(Zipper::zip);
            long syncEnd = System.currentTimeMillis();
            System.out.println("Sync took: " + (syncEnd - startSync) + "ms");
        } else {
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

            long tookMs = System.currentTimeMillis() - allThreadsStart;

            ZipResult result = new ZipResult();
            result.setMemory(maxMemory);
            result.setCores(cores);
            result.setDurationMs(tookMs);
            result.setBinary(binary);
            result.setFileSize(fileSize);

            appendResult(result);

            System.out.println("All threads completed! Took: "
                    + (tookMs) + "ms");
        }

    }

    private static void appendResult(ZipResult result) throws IOException {
        appendResult(result);
        FileWriter writer = new FileWriter(new File("/opt/test-results/result.csv"));
        writer.append(result.getCores() + "," + result.getMemory() + "," + result.getFileSize() + "," + result.getDurationMs());
        writer.write("/n");
        writer.flush();
        writer.close();
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
