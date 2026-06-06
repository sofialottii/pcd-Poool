package pcd.FSStat.eventLoop;


import io.vertx.core.*;
import io.vertx.core.file.FileSystem;


public class FSStatEventLoop extends VerticleBase {


    public Future<?> start() throws Exception {

        FileSystem fs = this.vertx.fileSystem();

        FSStatLib lib = new FSStatLib(fs);

        Future<Report> report = lib.getFSReport("./", 1000, 10);

        /*vertx.setTimer(1000L, (Handler<Long>) report.onComplete((res) -> {

                    System.out.println(res.result().toString());
        }));*/

        report.onComplete(( res) -> {
            System.out.println(res.result().toString());

        });

        return super.start();
    }

    public Future<?> stop() throws Exception {
        return super.stop();
    }
}
