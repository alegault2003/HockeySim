package HockeySim;
import java.util.Random;

/**
 * Agathe Legault
 * April 28, 2022
 * this program represents the customer. it will have variables and methods for
 * ticket whether or not the customer already have their ticket
 */
public class Customer implements State {
    //variables
    private State tickets;
    private State have = new Ticket();
    private State havent = new NoTicket();
    private Random rn = new Random();

    /**
     * main constructor
     * randomizes whether customer has tickets or not
     */
    public Customer(){
        int n = rn.nextInt(10);
        if(n < 5)
            setTickets(havent);
        else
            setTickets(have);
    }

    /**
     * setTickets method
     * sets the ticket state (class Ticket or NoTicket)
     * @param s - state of tickey
     */
    public void setTickets(State s){tickets = s;}

    /**
     * getTickets method
     * returns the state of ticket (class Ticket or NoTicket)
     * @return - ticket state
     */
    public State getTickets(){return tickets;}

    /**
     * action method
     * calls action method of either ticket state
     * @param c - customer to be added
     * @param e - event class variable
     */
    public void action(Customer c, HockeyInfo e, int n, Statistics s) {
        tickets.action(c, e, n, s);
    }
}
