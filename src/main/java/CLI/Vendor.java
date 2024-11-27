package CLI;

public class Vendor implements Runnable {
    private final Ticket_pool ticketPool; // reference Ticket pool from its class
    private final int ticketReleaseRate;
    private final String VendorName;// vendor name
    private int ticketCounter;  // tacking the number  of releasing tickets by vendor



    //constructor
    public Vendor(Ticket_pool ticketPool, int ticketReleaseRate ,String VendorName){
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.VendorName =VendorName;
        this.ticketCounter = 0; // set counter to 0
    }

    @Override
    public void run(){
        try {
            while(ticketPool.getTotalTicketsAdded() < ticketPool.getTotalTicketLimit()){
                // creating tickets
                String ticket = generateTicket();
                // Add the ticket to the pool
                ticketPool.AddTickets(ticket);
                //info of  adding tickets
                System.out.println(VendorName+" added ticket : "+ ticket);
                // wait time in ms
                Thread.sleep(ticketReleaseRate);
            }
            System.out.println(VendorName + " has reached the total ticket limit. Stopping production.");
        }catch (InterruptedException e){
            System.out.println(VendorName +"has been interrupted. Stopped creating Tickets .");
            Thread.currentThread().interrupt(); // restore the interrupted status
        }
    }

    // Generates a unique ticket string
    private String generateTicket() {
        ticketCounter++;
        return VendorName + "-TICKET-" + ticketCounter;
    }

}
