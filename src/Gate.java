import java.util.concurrent.CountDownLatch;

public class Gate extends Thread{
    char name;
    boolean available;
    ATC airport;
    Plane plane;
    FuelTruck fuelTruck;
    Cleaning cleaning = new Cleaning();
    Passengers passengers = new Passengers();

    public Gate(char name, boolean available, ATC airport, FuelTruck fuelTruck) {
        this.name = name;
        this.available = available;
        this.airport = airport;
        this.fuelTruck = fuelTruck;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
        cleaning.plane = plane;
        passengers.plane = plane;
    }

    @Override
    public void run() {
        try {
            cleaning.start();
            passengers.start();
            fuelTruck.useFuelTruck(plane);
            cleaning.join();
            passengers.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Plane "+plane.planeID+": All process done. Final check.");
            try {
                currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Plane "+plane.planeID+": Final check done. Ready to Depart.");
            airport.depart(plane);

    }
}
