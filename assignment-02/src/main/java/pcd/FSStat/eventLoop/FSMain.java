package pcd.FSStat.eventLoop;

import io.vertx.core.Vertx;

public class FSMain {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        final String dir = "./";
        final long maxFS = 1000;
        final int nb = 10;

        vertx.deployVerticle(new FSStatEventLoop(dir, maxFS, nb));

    }
}
