package unifor;

/**
 * @author Fernando Nogueira
 * @since 9/29/16 7:59 PM
 */
public class ZipResult {

    private String fileName;
    private String fileSize;
    private long durationMs;
    private String memory;
    private int cores;
    private boolean binary;
    private boolean async;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public void setBinary(boolean binary) {
        this.binary = binary;
    }

    public boolean isBinary() {
        return binary;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isAsync() {
        return async;
    }
}
