package CLI;

public class Vendor implements Runnable {
    private final Ticket_pool ticketPool;
    private final int ticketReleaseRate;
    private final String vendorName;
    private int ticketCounter; // has a record of ticket issued

    // constructor
    public Vendor(Ticket_pool  ticketPool, int ticketReleaseRate, String vendorName){
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate*1000;
        this.vendorName = vendorName;
        this.ticketCounter = 0;
    }

    @Override
    public void run(){
        try {
            while (!ticketPool.AllTicketsSold()){
                boolean Notify = false; // purpose is to notify only when tickets are adding
                synchronized (ticketPool){
                    while (ticketPool.getCurrentPoolSize() >= ticketPool.getMaxCapacity()){
                        if (!ticketPool.WaitLog()){
                            System.out.println("Ticket pool is full. Vendor is waiting....");
                            ticketPool.setWaitLog(true);
                        }
                        ticketPool.wait();
                    }
                    ticketPool.setWaitLog(false);
                    if (ticketPool.getTotalTicketsAdded() < ticketPool.getTotalTicketLimit()){
                        String ticket = "Ticket-"+(++ticketCounter);
                        System.out.println("⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺");
                        System.out.println("Vendor running....");
                        ticketPool.AddTickets(ticket);
                        System.out.println(vendorName+" added - "+ticket+" | Tickets added "+ticketPool.getTotalTicketsAdded());
                        Notify = true;
                    }
                    if (Notify) { // notify the waiting threads if notify boolean is true
                        ticketPool.notify(); // this will notify to say a ticket is added to the pool
                    }
                }
                Thread.sleep(ticketReleaseRate);
            }
        }catch (InterruptedException e){
            System.out.println(vendorName + " was interrupted.");
            Thread.currentThread().interrupt();
        }

    }
}
