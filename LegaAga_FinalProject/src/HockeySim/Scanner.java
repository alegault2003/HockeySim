package HockeySim;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Agathe Legault
 * April 28, 2022
 * this program represents a ticket scanner. if will wait for new customer. when
 * customer comes, it will scan ticket. it will then remove customer from list
 * (represents customer going to seat)
 */
public class Scanner extends Thread{
    //variables
    private Semaphore[] sem;
    private int scannerNum;
    private HockeyInfo h;

    /**
     * constructor
     * initializes variables
     * @param ho - hockeyInfo instance
     * @param n - line number
     * @param s - semaphore list
     */
    public Scanner(HockeyInfo ho, int n, Semaphore[] s){
        h = ho;
        scannerNum = n;
        sem = s;
    }

    /**
     * run method
     * will wait until scanner has customer. when scanner has customer, scan ticket
     * and remove them from list
     */
    public void run(){
        Random rn = new Random();

        while(true){
            //wait for permit
            try{sem[scannerNum].acquire();} catch(InterruptedException i){}

            //if there are customers in line
            if(h.getScanLineN(scannerNum).size() > 0){
                System.out.println("\nnew customer at scanner #" + scannerNum);

                //scan ticket
                System.out.println("\ncustomer at scanner #" + scannerNum + " getting ticket scanned");
                try{Thread.sleep(3*1000/4);} catch(InterruptedException x){};

                //remove customer from line list
                System.out.println("\ncustomer at scanner #" + scannerNum + " is leaving");
                if(h.getScanLineN(scannerNum).size() > 0)
                    h.removeScannerCustomer(scannerNum);
            }//if there are no customers
            else {
                System.out.println("\nscanner #" + scannerNum + " waiting for customers...");
                try{Thread.sleep(10*1000);} catch(InterruptedException d){}
                if(h.getScanLineN(scannerNum).size() == 0) {
                    System.out.println("\nno more customers at scanner #" + scannerNum);
                    break;
                }
            }
            //release permit
            sem[scannerNum].release();
        }
    }
}
