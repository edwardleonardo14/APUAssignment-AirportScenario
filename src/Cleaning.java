import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.currentThread;

public class Cleaning extends Thread{
    Plane plane;

    boolean cleaningProcess()
    {
        this.start();
        return true;
    }

    @Override
    public void run() {
        System.out.println("Plane "+plane.planeID+": Cleaning, Resupplying, and Maintenance Start.");
        try {
            currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Plane "+plane.planeID+": Cleaning, Resupplying, and Maintenance Done!");
    }
}
