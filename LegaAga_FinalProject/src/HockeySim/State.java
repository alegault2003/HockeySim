package HockeySim;

/**
 * Agathe Legault
 * April 28, 2022
 * this interface will provide polymorphism
 */
public interface State {
    /**
     * action method
     * depending on ticket state, customer will be added to ticket or scanner line
     * @param c - customer
     * @param e - hockeyInfo object
     */
    void action(Customer c, HockeyInfo e, int n, Statistics s);
}
