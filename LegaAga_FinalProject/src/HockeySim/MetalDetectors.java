package HockeySim;
import java.util.concurrent.Semaphore;
import java.util.Random;

/**
 * Agathe Legault
 * April 28, 2022
 * this program represents a metal detector. it will wait for new customer. when
 * customer comes, it will have customer go through detector. it has 20% chance
 * of setting off detector, if so, give customer pat down and update stats. finally,
 * if customer already has ticket, send them to scanner, else send them to ticket counter.
 */
public class MetalDetectors extends Thread{
    //variables
    private int detectorNum;
    private Semaphore[] sem;
    private HockeyInfo h;
    private Statistics stat;

    /**
     * constructor
     * initializes variables
     * @param ho - hockeyInfo instance
     * @param d - line number
     * @param s - semaphore list
     */
    public MetalDetectors(HockeyInfo ho, int d, Semaphore[] s, Statistics st){
        h = ho;
        detectorNum = d;
        sem = s;
        stat = st;
    }

    /**
     * run method
     * will wait until detector has customer. when detector has new customer, have
     * them go through. if they set off detector, give them pat down and update stats.
     * if customer already has tickets, send them to scanners, else send them to counters
     */
    public void run() {
        Random rn = new Random();
        while(true) {
            //wait for permit
            try {sem[detectorNum].acquire();} catch (InterruptedException x) {}

            //if there are customers in line
            if (h.getMetalLineN(detectorNum).size() > 0) {
                stat.updateCustomers();
                System.out.println("\nnew customer at metal detector #" + detectorNum);

                //go through detector
                System.out.println("\ncustomer at metal detector #" + detectorNum + " going through");
                try {Thread.sleep(10 * 1000 / 4);} catch (InterruptedException c) {}

                //if detector goes off, give customer pat down
                double n = rn.nextDouble();
                if (n < 0.2) {
                    stat.updateBeeps();
                    System.out.println("\noops, customer at metal detector #" + detectorNum + " set it off");
                    System.out.println("\tcustomer is getting a pat down");

                    try {Thread.sleep(30 * 1000 / 4);} catch (InterruptedException i) {}
                }

                //depending on whether customer has ticket, send to counter or scanner
                System.out.println("\ncustomer at detector #" + detectorNum + " is leaving");
                Customer c = h.removeMetalCustomer(detectorNum);
                c.action(c, h, detectorNum, stat);
            }//if there are no customers
            else{
                System.out.println("metal detector #" + detectorNum + " waiting for customers...");
                try{Thread.sleep(10*1000);} catch(InterruptedException d){}
                if(h.getMetalLineN(detectorNum).size() == 0) {
                    System.out.println("\nno more customers at metal detector #" + detectorNum);
                    break;
                }
            }

            //release permit
            sem[detectorNum].release();

        }
    }

}
