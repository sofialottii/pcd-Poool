package pcd.FSStat.eventLoop;

import io.vertx.core.Vertx;

public class FSMain {

    public static void main(String[] args) {
        System.out.println("miao");
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new FSStatEventLoop());

    }
}
