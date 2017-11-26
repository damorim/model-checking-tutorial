public class MyDeadlock {
    public static class Pair {
        String x = "x";
        String y = "y";

        public void update() {
            x = x + y + x;
        }
    }

    public static class RC0 extends java.lang.Thread {
        Pair p;


        public void run() {
            synchronized(p.x) {
                synchronized(p.y) {
                    p.update();
                }
            }
        }
    }

    public static class RC1 extends java.lang.Thread {
        Pair p;

        public void run() {
            synchronized(p.y) {
                synchronized(p.x) {
                    p.update();
                }
            }
        }
        
    }

    public static void main(String... args) throws java.lang.Exception {
        Pair p = new Pair();
        RC0 rc1 = new RC0();
        RC1 rc2 = new RC1();

        rc1.p = p;
        rc2.p = p;

        rc1.start();
        rc2.start();
        rc1.join();
        rc2.join();
        System.out.println("x -> "+p.x);
    }

}
