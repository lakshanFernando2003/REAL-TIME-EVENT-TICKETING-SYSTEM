package CLI;

import java.util.ArrayList;
import java.util.List;

public class Ticket_pool {
    private final List<String> TicketPool;
    private final int MaxCapacity;
    private final int TotalTicketLimit;
    private boolean WaitLog;
    private int totalTicketsAdded;
    private int TotalTicketsSold;

    public Ticket_pool(int maxCapacity, int totalTicketLimit) {
        this.TicketPool = new ArrayList<>();
        MaxCapacity = maxCapacity;
        TotalTicketLimit = totalTicketLimit;
        this.totalTicketsAdded = 0;
        this.TotalTicketsSold = 0;
        this.WaitLog = false;
    }

    public synchronized void AddTickets(String ticket) throws InterruptedException {
        while (TicketPool.size() >= MaxCapacity) {
            if (!WaitLog) {
                System.out.println("⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺");
                System.out.println("Ticket pool is full. Vendor is waiting....");
                WaitLog = true;
            }
            wait();
        }
        WaitLog = false;
        TicketPool.add(ticket); //adding the ticket
        totalTicketsAdded++;
        System.out.println("Ticket Pool adding " + ticket + "| Pool size = " + TicketPool.size());
        notify();
    }

    public synchronized String BuyTicket() throws InterruptedException {
        while (TicketPool.isEmpty()) {
            if (AllTicketsSold()) {
                notifyAll();
                return null;
            } else {
                if (!WaitLog) {
                    System.out.println("⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺");
                    System.out.println("Ticket pool is empty. customer is waiting....");
                    WaitLog = true;
                }
                wait();
            }
        }
        WaitLog = false;
        System.out.println("⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺");
        System.out.println("Customer running....");
        String ticket = TicketPool.removeFirst();
        TotalTicketsSold++;

        notify();
        return ticket;
    }

    public synchronized boolean AllTicketsSold() {
        return totalTicketsAdded >= TotalTicketLimit && TicketPool.isEmpty();
    }

    public synchronized int getTotalTicketsAdded() {
        return totalTicketsAdded;
    }

    public synchronized int getTotalTicketsSold() {
        return TotalTicketsSold;
    }

    public synchronized int getCurrentPoolSize() {
        return TicketPool.size();
    }

    public synchronized int getTotalTicketLimit() {
        return TotalTicketLimit;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public synchronized void setWaitLog(boolean waitLog){
        WaitLog = waitLog;
    }

    public synchronized boolean WaitLog() {
        return WaitLog;
    }

}



