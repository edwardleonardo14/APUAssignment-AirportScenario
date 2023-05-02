import java.util.concurrent.CountDownLatch;

public class Passengers extends Thread{
    Plane plane;

    boolean passengersProcess()
    {
        this.start();
        return true;
    }

    @Override
    public void run() {
        System.out.println("Plane "+plane.planeID+": Unloading Passengers Start.");
        for(int i = 1; i <= plane.numOfPassengers; i++)
        {
            System.out.println("Plane "+plane.planeID+": Passengers " + i + " left the plane.");
            try {
                currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Plane "+plane.planeID+": Unloading Passengers Finished!");
        try {
            currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Plane "+plane.planeID+": Loading Passengers Start.");
        for(int i = 1; i <= plane.numOfPassengers; i++)
        {
            System.out.println("Plane "+plane.planeID+": Passengers " + i + " entered the plane.");
            try {
                currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Plane "+plane.planeID+": Loading Passengers Finished!");
    }
}
