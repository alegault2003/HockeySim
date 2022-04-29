package HockeySim;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Agathe Legault
 * April 28, 2022
 * this program represents a ticket counter. it will wait for new customers. when
 * customer comes, it will sell ticket then send them to scanner line
 */
public class TicketCounter extends Thread{
    //variables
    private Semaphore[] sem;
    private int counterNum;
    private HockeyInfo h;

    /**
     * constructor
     * initializes variables
     * @param s - semaphore list
     * @param ho - hockeyInfo instance
     * @param n - line number
     */
    public TicketCounter(Semaphore[] s, HockeyInfo ho, int n){
        h = ho;
        sem = s;
        counterNum = n;
    }

    /**
     * run method
     * will wait until counter has customer. when counter has customer, sell ticket
     * then send them to scanner.
     */
    public void run(){
        Random rn = new Random();

        while(true){
            //wait for permit
            try{sem[counterNum].acquire();}catch(InterruptedException i){}

            //if there are customers
            if(h.getTicketLineN(counterNum).size() > 0){
                System.out.println("\nnew customer at ticket counter #" + counterNum);

                //sell ticket
                System.out.println("\ncustomer at counter #" + counterNum + " is getting their tickets");
                try {Thread.sleep(30 * 1000 / 4);}catch(InterruptedException x){}

                //send customer to scanner line
                System.out.println("\ncustomer at counter #" + counterNum + " is leaving");
                h.addScannerCustomer(h.removeTicketCustomer(counterNum));
            }//if there are no customers
            else {
                System.out.println("\nticket counter #" + counterNum + " waiting for customers...");
                try{Thread.sleep(10*1000);} catch(InterruptedException d){}
                if(h.getTicketLineN(counterNum).size() == 0) {
                    System.out.println("\nno more customers at counter #" + counterNum);
                    break;
                }
            }
            //release permit
            sem[counterNum].release();
        }
    }

}
