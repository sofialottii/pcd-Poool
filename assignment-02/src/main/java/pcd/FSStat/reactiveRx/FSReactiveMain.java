package pcd.FSStat.reactiveRx;

public class FSReactiveMain {
    public static void main(String[] args) {

        final String dir = "./";
        final long maxFS = 1000;
        final int nb = 10;

        FSStatLibReactive lib = new FSStatLibReactive();
        System.out.println(lib.getFSReport(dir, maxFS, nb).toString());
    }
}
