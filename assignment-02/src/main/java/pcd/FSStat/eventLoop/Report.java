package pcd.FSStat.eventLoop;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Report {

    private static int OUT_OF_RANGE = -1;

    private int totalFiles;
    private final int maxFS;
    private final int nb;
    private final int chunkSize;
    private NavigableMap<Integer, Integer> bands = new TreeMap<>();


    public Report(int maxFS, int nb) {
        this.totalFiles = 0;
        this.maxFS = maxFS;
        this.nb = nb;

        this.chunkSize = maxFS / nb;

        for (int i = 0; i < this.nb; i++) {
            int endInterval = (i == nb - 1) ? this.maxFS : (i + 1) * this.chunkSize;
            this.bands.put(endInterval, 0);
        }

        this.bands.put(OUT_OF_RANGE, 0);

    }


    public void addFile(int size) {
        //aumentiamo il numero di file visti
        this.totalFiles++;

        //aggiungiamo il file alla corrispondente fascia
        Integer cell = this.bands.ceilingKey(size);

        if (cell != null) {
            this.bands.put(cell, bands.get(cell)+1);
        } else {
            this.bands.put(OUT_OF_RANGE, this.bands.get(OUT_OF_RANGE)+1);
        }
    }

    public int getTotalFiles() {
        return this.totalFiles;
    }

    public Map<Integer, Integer> getBands() {
        return this.bands;
    }


    //metodo per verificare che poi cancelleremo
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORT STATISTICHE ===\n");
        sb.append("File Totali Elaborati: ").append(this.totalFiles).append("\n");
        sb.append("Distribuzione nelle Fasce:\n");

        java.util.Map<Integer, Integer> mappaOrdinata = new java.util.TreeMap<>(this.bands);

        for (java.util.Map.Entry<Integer, Integer> entry : mappaOrdinata.entrySet()) {
            int chiave = entry.getKey();
            int valore = entry.getValue();

            if (chiave == OUT_OF_RANGE) {
                sb.append("  [Fuori Limite (OUT_OF_RANGE)] -> ").append(valore).append(" file\n");
            } else {
                sb.append("  [Fascia fino a ").append(chiave).append(" byte] -> ").append(valore).append(" file\n");
            }
        }
        sb.append("==========================");
        return sb.toString();
    }


}

/*

    ABBIAMO PENSATO COSI:
    dobbiamo avere un totalFiles (semplicemente int, che potremmo aumentare a mano a mano che viene visitato il file)

    e una mappa che ha come chiave la fascia di intervallo (o meglio, la fine dell'intervallo),
    come valore il numero di file che appartengono a quell'intervallo

    CHIAVE - VALORE
    100        4     -> ci sono 4 file che si trovano tra 0 e 100MB
    200        3     -> ci sono 3 file che si trovano tra 101 e 200MB
    300        eccetera
    400
    500
    ...
    -1         5     -> ci sono 5 file che superano la dimensione massima



*/


//19 giugno h 16
//24 giugno h ???

