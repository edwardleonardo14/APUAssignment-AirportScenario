
public class Plane implements Runnable{
    int numOfPassengers;
    int planeID;
    long startTime;
    long endTime;
    double elapsedTime;
    ATC airport;
    char gateName;
    boolean refueled;
    boolean cleaned;
    boolean passengers;

    public Plane(int numOfPassengers, int planeID, ATC airport) {
        this.numOfPassengers = numOfPassengers;
        this.planeID = planeID;
        this.startTime = 0;
        this.endTime = 0;
        this.airport = airport;
    }

    @Override
    public void run() {
        System.out.println("Plane "+planeID+": This is Plane "+planeID+". Total passengers: "+numOfPassengers+", entering Airport's airspace. Asking permission to land.");
        startTime = System.currentTimeMillis();
        if(airport.sGate.availablePermits() == 0)
        {
            System.out.println("ATC: Plane "+planeID+", all gates are full, please standby.");
            System.out.println("Plane "+planeID+": Copy ATC, will circle around the airspace.");
        }
        airport.landing(this);
    }
    public void calculateTime()
    {
        elapsedTime = (double) (endTime-startTime)/1000;
        System.out.println("Plane "+planeID+": Total waiting time: "+elapsedTime+" seconds.");
    }
}
