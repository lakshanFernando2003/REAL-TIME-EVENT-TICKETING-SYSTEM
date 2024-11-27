package CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ticket_pool {
    private  final List<String> TicketPool; // To store Thread-safe tickets
    private final int maxCapacity; // max ticket capacity
    private  final  int totalTicketLimit; // total ticket allowed
    private int totalTicketsAdded; // count of total tickets in the pool

    // constructor
    public Ticket_pool(int MaxCapacity , int totalTicketLimit){
        this.maxCapacity = MaxCapacity;
        this.TicketPool = Collections.synchronizedList(new ArrayList<>());
        this.totalTicketLimit = totalTicketLimit;
        this.totalTicketsAdded = 0;

    }

    // Adding tickets to the pool sold by (Vendors)
    public synchronized void AddTickets(String tickets) throws InterruptedException{
        while (TicketPool.size()>= maxCapacity){
            System.out.println("TicketPool is full. Vendor is waiting....");
            wait(); // wait until space becomes available
        }
        if (totalTicketsAdded < totalTicketLimit) {
            TicketPool.add(tickets); // adding tickets to the pool
            totalTicketsAdded++;
            System.out.println("Ticket added " + tickets + "| Current Tickets : " + TicketPool.size());
            TicketPool.notifyAll(); // this is to notify the waiting threads.
        }
    }

    // Remove tickets from the pool bought by (customer)
    public synchronized String RemoveTickets() throws InterruptedException{
        while(TicketPool.isEmpty()){
            System.out.println("TicketPool is empty. customer is waiting....");
            TicketPool.wait(); // wait until tickets are available
        }
        String tickets = TicketPool.removeFirst();
        System.out.println("Ticket removed: " + tickets + " | Remaining Tickets: " + TicketPool.size());
        TicketPool.notifyAll();
        return tickets;
    }

    // check current ticket count
    public int getTicketCount(){
        synchronized (TicketPool){
            return TicketPool.size();
        }
    }

    // getting the total tickets added
    public  synchronized  int getTotalTicketsAdded(){
        return totalTicketsAdded;
    }

    // get the limit of tickets
    public  int getTotalTicketLimit(){
        return totalTicketLimit;
    }


}
