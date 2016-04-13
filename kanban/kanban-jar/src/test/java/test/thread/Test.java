package test.thread;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(){

            @Override
            public void run() {
                System.err.println("hello from new thread");
            }
           
        };
        
        t.start();
//        Thread.yield();
        Thread.sleep(1);
        System.out.println("hello from main");
        t.join();
        
    }
}
