package HockeySim;

/**
 * Agathe Legault
 * April 28, 2022
 * this program implements State interface. action method is what customer with
 * a ticket does
 */
public class Ticket implements State{
    /**
     * action method
     * adds customer to scanner line
     * @param c - customer
     * @param e - hockeyInfo object
     */
    public void action(Customer c, HockeyInfo e, int n, Statistics s){
        System.out.println("\ncustomer from metal detector #" + n + " goes to scanner");
        e.addScannerCustomer(c);
        s.updateTickets();
    }
}
