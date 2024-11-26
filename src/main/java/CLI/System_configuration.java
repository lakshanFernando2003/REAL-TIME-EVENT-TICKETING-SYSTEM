package CLI;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class System_configuration {
    private int Total_Tickets; // Total Number Of Tickets
    private int Ticket_Release_Rate; // Ticket Releasing Rate
    private int Customer_Retrieval_Rate; // Customer Retrieving Rate
    private int Max_Ticket_Capacity; // maximum capacity of Tickets

        //non-parameterized constructor
    public System_configuration(){

    }

    // Getters and Setters
    public int getTotal_Tickets() {
        return Total_Tickets;
    }
    public void setTotal_Tickets(int total_Tickets) {
        Total_Tickets = total_Tickets;
    }
    public int getTicket_Release_Rate(){
        return Ticket_Release_Rate;
    }
    public void setTicket_Release_Rate(int ticket_Release_Rate){
        Ticket_Release_Rate = ticket_Release_Rate;
    }
    public int getCustomer_Retrieval_Rate(){
        return Customer_Retrieval_Rate;
    }
    public void setCustomer_Retrieval_Rate(int customer_Retrieval_Rate){
        Customer_Retrieval_Rate = customer_Retrieval_Rate;
    }
    public int getMax_Ticket_Capacity(){
        return Max_Ticket_Capacity;
    }
    public void setMax_Ticket_Capacity(int max_Ticket_Capacity){
        Max_Ticket_Capacity = max_Ticket_Capacity;
    }

    // File saving method
   public void SaveFile(String filename) {
     Gson gson = new GsonBuilder().setPrettyPrinting().create();
     try(FileWriter writer = new FileWriter(filename)){
         gson.toJson(this,writer); // converting to json format
         System.out.println("Configuration file saved as : " + filename);
     } catch (IOException e){
         System.out.println("Error! - Failed to save Configuration : " + e.getMessage());
     }
   }

   // Load configuration from JSON file
    public static  System_configuration LoadFile(String filename){
        Gson gson = new Gson();
        try(FileReader Loader = new FileReader(filename)){
            return gson.fromJson(Loader,System_configuration.class);
        }catch (IOException e){
            System.out.println("Error! - Failed to load Configuration: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString(){
        return "System-Configured Successfully\n"+
                " Total_tickets = "+Total_Tickets+
                "\n Ticket release Rate = "+Ticket_Release_Rate+
                "\n customer Retrieval Rate = "+Customer_Retrieval_Rate+
                "\n max Ticket Capacity = "+Max_Ticket_Capacity;

    }

}
