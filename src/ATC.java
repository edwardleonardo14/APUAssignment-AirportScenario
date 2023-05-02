import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ATC extends Thread{
    double totalTime;
    double minTime;
    double maxTime;
    int totalPassenger;
    static int countPlane = 0;
    Semaphore mRunway = new Semaphore(1, true);
    Semaphore sGate = new Semaphore(2, true);
    FuelTruck fuelTruck = new FuelTruck();
    boolean gateAIndicator = true;
    boolean gateBIndicator = true;


    int planeID = 1;
    Random passengerGenerator = new Random();

    public static void main(String[] args) {
        ATC airport = new ATC();
        System.out.println("ATC: Good Morning all nearby aircraft, Asia Pacific Airport is now open, please approach the airspace for landing.");
        airport.start();

    }

    @Override
    public void run() {
        while (planeID <= 6) {
            Plane plane = new Plane(passengerGenerator.nextInt(50 - 10) + 10, planeID, this);
            planeID++;
            Thread thPlane = new Thread(plane);
            thPlane.start();
            try {
                TimeUnit.SECONDS.sleep((long) (Math.random() * 3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void landing(Plane plane)
    {
        try {
            sGate.acquire();
            mRunway.acquire();
            if(gateAIndicator)
            {
                System.out.println("ATC: Plane "+plane.planeID+", permission granted. Please land and head to gate A");
                System.out.println("Plane "+plane.planeID+": Affirmative ATC. Landing now.");
                plane.endTime = System.currentTimeMillis();
                plane.calculateTime();
                totalTime += plane.elapsedTime;
                totalPassenger += plane.numOfPassengers;
                if(plane.elapsedTime > maxTime)
                {
                    maxTime = plane.elapsedTime;
                }
                if(plane.elapsedTime < minTime)
                {
                    minTime = plane.elapsedTime;
                }
                plane.gateName = 'A';
                gateAIndicator = false;
                currentThread().sleep(1500);
                System.out.println("Plane "+plane.planeID+": has landed. Docking at Gate "+plane.gateName+".");
                mRunway.release();
                Gate gateA = new Gate('A', true, this, fuelTruck);
                gateA.setPlane(plane);
                gateA.start();
            }
            else if(gateBIndicator)
            {
                System.out.println("ATC: Plane "+plane.planeID+", permission granted. Please land and head to gate B");
                System.out.println("Plane "+plane.planeID+": Affirmative ATC. Landing now.");
                plane.endTime = System.currentTimeMillis();
                plane.calculateTime();
                totalTime += plane.elapsedTime;
                totalPassenger += plane.numOfPassengers;
                if(plane.elapsedTime > maxTime)
                {
                    maxTime = plane.elapsedTime;
                }
                if(plane.elapsedTime < minTime)
                {
                    minTime = plane.elapsedTime;
                }
                plane.gateName = 'B';
                gateBIndicator = false;
                currentThread().sleep(1500);
                System.out.println("Plane "+plane.planeID+": has landed. Docking at Gate "+plane.gateName+".");
                mRunway.release();
                Gate gateB = new Gate('B', true, this, fuelTruck);
                gateB.setPlane(plane);
                gateB.start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void depart(Plane plane)
    {
        System.out.println("Plane "+plane.planeID+": Plane "+plane.planeID+" will depart from Gate "+plane.gateName+". Asking ATC for approval.");
        try {
            mRunway.acquire();
            System.out.println("ATC: Plane "+plane.planeID+" Departure approved, Godspeed.");
            System.out.println("Plane "+plane.planeID+": Taking off.");
            currentThread().sleep(1500);
            System.out.println("Plane "+plane.planeID+": is airborne! See you around ATC. Plane "+plane.planeID+" out.");
            System.out.println("ATC: See you around, Plane "+plane.planeID+". ATC out.");
            countPlane++;
//            System.out.println("ATC: Total departed plane: "+countPlane);
            if(plane.gateName == 'A')
            {
                gateAIndicator = true;
                System.out.println("ATC: Gate A is now available.");
            }
            else
            {
                gateBIndicator = true;
                System.out.println("ATC: Gate B is now available.");
            }
            sGate.release();
            mRunway.release();
            if(countPlane == 6)
            {
                System.out.println("ATC: All aircraft have departed, generating report: ");
                System.out.println("ATC: Gate A is empty: "+gateAIndicator+".");
                System.out.println("ATC: Gate B is empty: "+gateBIndicator+".");
                System.out.println("ATC: Minimum wait time: "+minTime+" seconds.");
                System.out.println("ATC: Maximum wait time: "+maxTime+" seconds.");
                System.out.println("ATC: Average wait time: "+totalTime/countPlane+" seconds.");
                System.out.println("ATC: Number of plane served: "+countPlane+" planes.");
                System.out.println("ATC: Number of passenger served: "+totalPassenger+" passengers.");
                System.out.println("ATC: Report Done. ATC going dark. Good Night.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
