package HockeySim;

/**
 * Agathe Legault
 * April 28, 2022
 * this program implements State interface. action method is what customer with
 * no ticket does
 */
public class NoTicket implements State{
    /**
     * action method
     * adds customer to ticket line
     * @param c - customer
     * @param e - hockeyInfo object
     */
    public void action(Customer c, HockeyInfo e, int n, Statistics s) {
        System.out.println("\ncustomer from metal detector #" + n + " goes to ticket counter");
        e.addTicketCustomer(c);
        s.updateNoTickets();
    }
}
