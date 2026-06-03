package pcd.FSStat.eventLoop;

import io.vertx.core.Vertx;

public class FSMain {

    public static void main(String[] args) {
        System.out.println("miao");
        //Vertx vertx = Vertx.vertx();

        //FSStatLib lib = new FSStatLib();


        Report report = new Report(1000, 10);


        report.addFile(1);
        report.addFile(99);

        report.addFile(100);

        report.addFile(1000);
        report.addFile(10000);
        report.addFile(0);
        System.out.println(report.toString());



    }
}
