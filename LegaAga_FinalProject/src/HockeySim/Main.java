package HockeySim;

/**
 * Agathe Legault
 * April 28, 2022
 * this program will start the hockey lobby simulation
 */
public class Main {
    /**
     * main method
     * starts simulation
     * @param args - command line argument
     */
    public static void main(String[] args){
        HockeySim hs = new HockeySim();
        Statistics s = Statistics.getInstance();
        hs.run(s);

        System.out.println("\nnumber of customers: " + s.getCustomers());
        System.out.println("number of customers that set off detectors: " + s.getDetectorBeeps());
        System.out.println("number of customers with tickets: " + s.getTickets());
        System.out.println("number of customers without tickets: " + s.getNoTickets());
    }
}
