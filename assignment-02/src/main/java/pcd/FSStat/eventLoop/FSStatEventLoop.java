package pcd.FSStat.eventLoop;


import io.vertx.core.*;

public class FSStatEventLoop extends VerticleBase {


    public Future<?> start() throws Exception {

        this.vertx.setTimer(1000, (res) -> {
            var num = Math.random();
            //promise.complete(num);
        });



        //chiamerà get report e prenderà future
        return super.start();
    }
}
