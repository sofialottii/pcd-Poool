package pcd.FSStat.reactiveRx;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Report {

    private static long OUT_OF_RANGE = Long.MAX_VALUE;

    private int totalFiles;
    private final long maxFS;
    private final int nb;
    private final long chunkSize;
    private NavigableMap<Long, Integer> bands = new TreeMap<>();

    public Report(long maxFS, int nb) {
        this.totalFiles = 0;
        this.maxFS = maxFS;
        this.nb = nb;
        this.chunkSize = maxFS / nb;

        for (int i = 0; i < this.nb; i++) {
            long endInterval = (i == nb - 1) ? this.maxFS : (i + 1) * this.chunkSize;
            this.bands.put(endInterval, 0);
        }

        this.bands.put(OUT_OF_RANGE, 0);

    }

    public void addFile(long size) {
        //aumentiamo il numero di file visti
        this.totalFiles++;

        //aggiungiamo il file alla corrispondente fascia
        Long cell = this.bands.ceilingKey(size);

        if (cell != null) {
            this.bands.put(cell, bands.get(cell)+1);
        } else {
            this.bands.put(OUT_OF_RANGE, this.bands.get(OUT_OF_RANGE)+1);
        }
    }

    public int getTotalFiles() {
        return this.totalFiles;
    }

    public Map<Long, Integer> getBands() {
        return this.bands;
    }

    @Override
    public String toString() {
        String res = "Total files founded: " + this.totalFiles + "\n";
        res += "Distribution:\n";

        for (Long key : this.bands.keySet()) {
            int numFile = this.bands.get(key);

            if (key == OUT_OF_RANGE) {
                res += " - Out of range: " + numFile + " files\n";
            } else {
                res += " - Up to " + key + " bytes: " + numFile + " files\n";
            }
        }

        return res;
    }
}
