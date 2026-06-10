package pcd.FSStat.reactiveRx;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.File;

public class FSStatLibReactive {

    private static void recursiveFileSearch(FlowableEmitter<Long> emitter, String dirPath){
        try {
            File dir = new File(dirPath);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    if (child.isDirectory()) {
                        recursiveFileSearch(emitter, child.getAbsolutePath());
                    }else{
                        emitter.onNext(child.length());
                        Thread.sleep(5);
                    }
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }
        } catch (Exception e) {}
    }

    private static Flowable<Long> getColdStream(String dirPath){
        Flowable<Long> source = Flowable.create(emitter -> {
            recursiveFileSearch(emitter, dirPath);
        }, BackpressureStrategy.BUFFER);

        return source;
    }

    public Report getFSReport(String dir, long maxFS, int nb){

        Report report = new Report(maxFS, nb);

        Flowable<Long> source = getColdStream(dir);

        source.onBackpressureBuffer(5_000, () -> {
            System.out.println("HELP!");
        }).observeOn(Schedulers.computation()).subscribe(size -> {
            report.addFile(size);
        });

        return report;
    }
}
