package CLI;

public class Customer implements Runnable {
    private final Ticket_pool ticketPool;
    private  final String customerName;
    private  final int retrievalRate;




    public Customer(Ticket_pool ticketPool, String CustomerName ,int retrievalRate){
        this.ticketPool = ticketPool;
        customerName = CustomerName;
        this.retrievalRate = retrievalRate*1000;
    }

    @Override
    public void run(){
        String ticket;
        try{
            while(!ticketPool.AllTicketsSold()){
                synchronized (ticketPool){
                    if (ticketPool.getTotalTicketsAdded() == 0) {
                        ticketPool.wait();
                    }
                    ticket = ticketPool.BuyTicket();
                    if (ticket != null){

                        System.out.println(customerName + " Purchased: " + ticket + " | Total Tickets Purchased: " + ticketPool.getTotalTicketsSold());
                        System.out.println("Ticket Pool removing "+ticket+"| Pool size = "+ticketPool.getCurrentPoolSize());
                    }
                }
                if (ticket == null){
                    System.out.println("⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺");
                    System.out.println("Tickets SOLD OUT: " + ticketPool.getTotalTicketsSold());
                    System.out.println("⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺");
                    break;
                }
                Thread.sleep(retrievalRate);
            }
        }catch (InterruptedException e){
            System.out.println(customerName+" was interrupted");
            Thread.currentThread().interrupt();
        }


    }
}