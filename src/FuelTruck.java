import static java.lang.Thread.currentThread;

public class FuelTruck {


    synchronized void useFuelTruck(Plane plane) throws InterruptedException
    {
        System.out.println("Plane "+plane.planeID+": Refuelling Start.");
        currentThread().sleep(500);
        System.out.println("Plane "+plane.planeID+": Refuelling...");
        currentThread().sleep(2000);
        System.out.println("Plane "+plane.planeID+": Refuelling Done!");
    }
}
