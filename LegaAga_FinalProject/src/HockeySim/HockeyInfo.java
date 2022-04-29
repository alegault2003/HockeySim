package HockeySim;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Agathe Legault
 * April 28 2022
 * this program will hold all information that will be passed through other classes
 * it holds all lines info, semaphores, permits, etc.
 * this program is a singleton
 */
public class HockeyInfo {
    //variables
    //total detectors, counters, and scanners
    private int metalPermits = 10;
    private int ticketCounters = 10;
    private int scanners = 10;

    //semaphores
    private Semaphore[] semScanner;
    private Semaphore[] semCounter;

    //current open counters and scanners
    private int ticketPermits;
    private int scannerPermits;

    //array lists of lines for each detector, counter, and scanner
    private ArrayList<Customer>[] metalLines = new ArrayList[metalPermits];
    private ArrayList<Customer>[] ticketLines = new ArrayList[ticketCounters];
    private ArrayList<Customer>[] scanLines = new ArrayList[scanners];

    //singleton object
    private static HockeyInfo h = new HockeyInfo();

    /**
     * default constructor
     */
    private HockeyInfo(){}

    /**
     * initialize method
     * initialize semaphore lists, permits, current running services, and line
     * array lists
     * @param semC - counter semaphores
     * @param semS - scanner semaphores
     */
    public void initialize(Semaphore[] semC, Semaphore[] semS, int counters, int scanners){
        //semaphore lists
        semCounter = semC;
        semScanner = semS;

        //permits
        ticketPermits = counters;
        scannerPermits = scanners;

        //initialize line lists
        for(int x = 0; x < metalLines.length; x++)
            metalLines[x] = new ArrayList<Customer>();

        for(int x = 0; x < ticketLines.length; x++)
            ticketLines[x] = new ArrayList<Customer>();

        for(int x = 0; x < scanLines.length; x++)
            scanLines[x] = new ArrayList<Customer>();

    }

    /**
     * getInstance method
     * returns single instance of HockeyInfo
     * @return - instance
     */
    public static HockeyInfo getInstance(){return h;}

    /**
     * addMetalCustomer method
     * adds new customer to metal waiting lines equally
     * @param c - new customer
     */
    public void addMetalCustomer(Customer c){
        //find line with smallest amount of customers
        int min = 0;
        for(int i = 1; i < metalLines.length; i++)
            if(metalLines[i].size() < metalLines[min].size())
                min = i;

        //add new customer to smallest line
        metalLines[min].add(c);
    }

    /**
     * removeMetalCustomer
     * removes first customer in line
     * @param n - line customer is in
     * @return - removed customer
     */
    public Customer removeMetalCustomer(int n){return metalLines[n].remove(0);}

    /**
     * addTIcketCustomer
     * adds new customer to counter waiting line equally. if lines become greater
     * than 10 customers each, open new counter
     * @param c - new customer
     */
    public void addTicketCustomer(Customer c){
        //find line with smallest amount of customers
        int min = 0;
        for(int i = 1; i < ticketPermits; i++)
            if(ticketLines[i].size() < ticketLines[min].size())
                min = i;

        //add new customer to smallest line
        if(ticketLines[min].size() == 0)
            semCounter[min].release();
        ticketLines[min].add(c);

        //maybe open new counter
        openCounter(ticketPermits);
    }

    /**
     * removeTicketCustomer method
     * removes first customer in line
     * @param n - line customer is in
     * @return - removed customer
     */
    public Customer removeTicketCustomer(int n){return ticketLines[n].remove(0);}

    /**
     * openCounter method
     * opens new counter if lines are longer than 10 customers each
     * @param n - new counter
     */
    public void openCounter(int n){
        //determine is lines are greater than 10 each
        int totalWaiting = 0;
        for(int i = 0; i < ticketPermits; i++)
            totalWaiting += ticketLines[i].size();

        //add new counter and move people from back of lines to new counter
        if(totalWaiting >= (ticketPermits*10) && ticketPermits < ticketCounters-1){
            System.out.println("counter #" + ticketPermits + " is opening");
            semCounter[ticketPermits].release();
            ticketPermits++;
            while(ticketLines[ticketPermits].size() < ticketLines[0].size()){
                for(int i = 0; i < ticketPermits-1; i++)
                    addTicketCustomer(ticketLines[i].remove(ticketLines[i].size() - 1));
            }
        }
    }

    /**
     * addScannerCustomer method
     * adds new customer to scanner lines equally. if lines become greater than 10
     * customers each, open new scanner
     * @param c - new customer
     */
    public void addScannerCustomer(Customer c){
        //find line with smallest amount of customers
        int min = 0;
        for(int i = 1; i < scannerPermits; i++)
            if(scanLines[i].size() < scanLines[min].size())
                min = i;

        //add customer to smallest line
        if(scanLines[min].size() == 0)
            semScanner[min].release();
        scanLines[min].add(c);

        //maybe open new scanner
        openScanner(scannerPermits);
    }

    /**
     * removeScannerCustomer method
     * removes first customer in line
     * @param n - line customer is in
     * @return - removed customer
     */
    public Customer removeScannerCustomer(int n){return scanLines[n].remove(0);}

    /**
     * openScanner method
     * opens new scanner if lines have more than 10 customers each
     * @param n - new scanner
     */
    public void openScanner(int n){
        //determine if lines are greater than 10 each
        int totalWaiting = 0;
        for(int i = 0; i < scannerPermits; i++)
            totalWaiting += scanLines[i].size();

        //open new scanner and move people from back of line to new scanner
        if(totalWaiting >= (scannerPermits*10) && ticketPermits < ticketCounters-1){
            System.out.println("scanner #" + scannerPermits + " is opening");
            semScanner[scannerPermits].release();
            scannerPermits++;
            while(scanLines[scannerPermits].size() < scanLines[0].size()){
                for(int i = 0; i < scannerPermits-1; i++)
                    addTicketCustomer(scanLines[i].remove(scanLines[i].size() - 1));
            }
        }
    }

    /**
     * getSemCounter method
     * returns counter semaphore list
     * @return - semCounter variable
     */
    public Semaphore[] getSemCounter(){return semCounter;}

    /**
     * getSemScanner method
     * returns scanner semaphore list
     * @return - semScanner variable
     */
    public Semaphore[] getSemScanner(){return semScanner;}

    /**
     * getMetalLineN method
     * returns arrayList of detector line
     * @param n - line number wanted
     * @return - array list of line wanted
     */
    public ArrayList<Customer> getMetalLineN(int n){return metalLines[n];}

    /**
     * getTicketLineN method
     * returns arrayList of counter line
     * @param n - line number wanted
     * @return - array list of line wanted
     */
    public ArrayList<Customer> getTicketLineN(int n){return ticketLines[n];}

    /**
     * getScannerLineN method
     * returns arrayList of scanner line
     * @param n - line number wanted
     * @return - array list of line wanted
     */
    public ArrayList<Customer> getScanLineN(int n){return ticketLines[n];}

    /**
     * getTicketPermits method
     * returns amount of counters open
     * @return - ticketPermits variable
     */
    public int getTicketPermits(){return ticketPermits;}

    /**
     * getScannerPermits method
     * returns amount of scanners open
     * @return - scannerPermits variable
     */
    public int getScannerPermits(){return scannerPermits;}
}
