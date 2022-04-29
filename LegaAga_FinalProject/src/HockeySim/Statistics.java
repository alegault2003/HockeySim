package HockeySim;

/**
 * Agathe Legault
 * April 28, 2022
 * this program will keep statistics about how many people set off the detectors. this program
 * is also a singleton
 */
public class Statistics {
    //variables
    private int detectorBeeps = 0;
    private int cus = 0;
    private int tickets = 0;
    private int noTickets = 0;
    //single instance
    private static Statistics s = new Statistics();

    /**
     * default constructor
     */
    private Statistics(){}

    public void updateTickets(){tickets++;}

    public int getTickets(){return tickets;}

    public void updateNoTickets(){noTickets++;}

    public int getNoTickets(){return noTickets;}

    /**
     * updateBeeps method
     * increments stat by 1
     */
    public void updateBeeps(){detectorBeeps++;}

    /**
     * getDetectorBeeps
     * returns amount of people that set off detector
     * @return - detectorBeeps variable
     */
    public int getDetectorBeeps(){return detectorBeeps;}

    /**
     * updateCustomers method
     * increments stat by 1
     */
    public void updateCustomers(){cus++;}

    /**
     * getCustomers method
     * returns total amount of customers that went through detectors
     * @return - cus variable
     */
    public int getCustomers(){return cus;}

    /**
     * getInstance method
     * returns only instance of Statistics class
     * @return s
     */
    public static Statistics getInstance(){return s;}
}
