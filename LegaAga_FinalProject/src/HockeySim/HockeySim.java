package HockeySim;
import java.util.concurrent.Semaphore;

/**
 * Agathe Legault
 * April 28, 2022
 * this program initializes semaphores and HockeyInfo. it also declares, initializes,
 * and starts threads
 */
public class HockeySim {
    //variables
    //semaphores
    private Semaphore[] semCounter = new Semaphore[10];
    private Semaphore[] semScanner = new Semaphore[10];
    private Semaphore[] semMetal = new Semaphore[10];

    //counters and scanners initially available
    private int counterPermits = 3;
    private int scannerPermits = 3;
    private HockeyInfo hi;

    /**
     * constructor
     * initializes semaphore lists and HockeyInfo object
     */
    public HockeySim(){
        initializeSems(0, semCounter);
        initializeSems(0, semScanner);
        initializeSems(10, semMetal);
        hi = HockeyInfo.getInstance();
        hi.initialize(semCounter, semScanner, counterPermits, scannerPermits);
    }

    /**
     * run method
     * adds certain amount of customers to detector lines. it will then declare,
     * initialize, and start all threads
     */
    public void run(Statistics s){
        //add customers
        int time = 0;
        while(time < (60*2)){
            Customer c = new Customer();
            hi.addMetalCustomer(c);
            time++;
        }

        //declare and initialize metal detector threads
        MetalDetectors md0 = new MetalDetectors(hi, 0, semMetal, s);
        MetalDetectors md1 = new MetalDetectors(hi, 1, semMetal, s);
        MetalDetectors md2 = new MetalDetectors(hi, 2, semMetal, s);
        MetalDetectors md3 = new MetalDetectors(hi, 3, semMetal, s);
        MetalDetectors md4 = new MetalDetectors(hi, 4, semMetal, s);
        MetalDetectors md5 = new MetalDetectors(hi, 5, semMetal, s);
        MetalDetectors md6 = new MetalDetectors(hi, 6, semMetal, s);
        MetalDetectors md7 = new MetalDetectors(hi, 7, semMetal, s);
        MetalDetectors md8 = new MetalDetectors(hi, 8, semMetal, s);
        MetalDetectors md9 = new MetalDetectors(hi, 9, semMetal, s);

        //declare and initialize ticket counter threads
        TicketCounter tc0 = new TicketCounter(semCounter, hi, 0);
        TicketCounter tc1 = new TicketCounter(semCounter, hi, 1);
        TicketCounter tc2 = new TicketCounter(semCounter, hi, 2);
        TicketCounter tc3 = new TicketCounter(semCounter, hi, 3);
        TicketCounter tc4 = new TicketCounter(semCounter, hi, 4);
        TicketCounter tc5 = new TicketCounter(semCounter, hi, 5);
        TicketCounter tc6 = new TicketCounter(semCounter, hi, 6);
        TicketCounter tc7 = new TicketCounter(semCounter, hi, 7);
        TicketCounter tc8 = new TicketCounter(semCounter, hi, 8);
        TicketCounter tc9 = new TicketCounter(semCounter, hi, 9);

        //declare and initialize scanner threads
        Scanner sc0 = new Scanner(hi, 0, semScanner);
        Scanner sc1 = new Scanner(hi, 1, semScanner);
        Scanner sc2 = new Scanner(hi, 2, semScanner);
        Scanner sc3 = new Scanner(hi, 3, semScanner);
        Scanner sc4 = new Scanner(hi, 4, semScanner);
        Scanner sc5 = new Scanner(hi, 5, semScanner);
        Scanner sc6 = new Scanner(hi, 6, semScanner);
        Scanner sc7 = new Scanner(hi, 7, semScanner);
        Scanner sc8 = new Scanner(hi, 8, semScanner);
        Scanner sc9 = new Scanner(hi, 9, semScanner);

        //start scanner threads
        sc0.start();
        sc1.start();
        sc2.start();
        sc3.start();
        sc4.start();
        sc5.start();
        sc6.start();
        sc7.start();
        sc8.start();
        sc9.start();

        //start ticket counter threads
        tc0.start();
        tc1.start();
        tc2.start();
        tc3.start();
        tc4.start();
        tc5.start();
        tc6.start();
        tc7.start();
        tc8.start();
        tc9.start();

        //start metal detector threads
        md0.start();
        md1.start();
        md2.start();
        md3.start();
        md4.start();
        md5.start();
        md6.start();
        md7.start();
        md8.start();
        md9.start();

        while(tc0.isAlive() || tc1.isAlive() || tc2.isAlive()){}

        return;
    }

    /**
     * initializeSems method
     * allow 1 permit for services initially open and 0 for those closed
     * @param permits - services open
     * @param arr - semaphore list to be initialized
     */
    public void initializeSems(int permits, Semaphore[] arr){
        int i = 0;
        //1 permit for services open
        while(i < permits) {
            arr[i] = new Semaphore(1);
            i++;
        }

        //o permit for services closed
        while(i < arr.length) {
            arr[i] = new Semaphore(0);
            i++;
        }
    }
}
