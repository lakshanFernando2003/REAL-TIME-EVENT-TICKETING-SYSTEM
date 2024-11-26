package CLI;
import java.util.InputMismatchException;
import java.util.Scanner;


public class System_Initialization {
    public static void initialize(){
        Scanner ReadInput = new Scanner(System.in);
        System_configuration Config = new System_configuration();

        System.out.println("<----- Real Time Ticketing System ----->");

        // input and validating Total tickets
        System.out.println("Enter Total number of Tickets : ");
        try {
            while (true){

                int Total_Tickets = ReadInput.nextInt();
                if (Total_Tickets > 0){
                    Config.setTotal_Tickets(Total_Tickets);
                    break;
                } else{
                    System.out.println("Invalid Input. Tickets should be greater than 0.");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Data Type... Enter a correct Input.  ");
            initialize();
        }

        // input and validating ticket release rate
        System.out.println("Enter Ticket Release Rate(ms) : ");
        try {
            while (true){

                int TicketReleaseRate = ReadInput.nextInt();
                if (TicketReleaseRate > 0){
                    Config.setTicket_Release_Rate(TicketReleaseRate);
                    break;
                }else{
                    System.out.println("Invalid input. Release rate must be greater than 0. please try again");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Data Type... Enter a correct Input.  ");
            initialize();
        }


        // input and validating Customer Retrieval rate
        System.out.println("Enter Customer Retrieval Rate(ms) : ");
        try{
            while (true){

                int CustomerRetrievalRate = ReadInput.nextInt();
                if(CustomerRetrievalRate > 0){
                    Config.setCustomer_Retrieval_Rate(CustomerRetrievalRate);
                    break;
                }else {
                    System.out.println("Invalid input. Release rate must be greater than 0. please try again");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Data Type... Enter a correct Input.  ");
            initialize();
        }


        // input and validating max ticket capacity
        System.out.println("Enter max Ticket Capacity : ");
        try {
            while(true){

                int MaxTicketCapacity =ReadInput.nextInt();
                if (MaxTicketCapacity > 0 && MaxTicketCapacity <= Config.getTotal_Tickets()){
                    Config.setMax_Ticket_Capacity(MaxTicketCapacity);
                    break;
                }else {
                    System.out.println("Invalid input. Release rate must be greater than 0 or equal to total tickets. please try again");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Data Type... Enter a correct Input.  ");
            initialize();
        }

        String message = "System Configuring...";
        int delay = 200;
        for (int cycle = 0; cycle < 3; cycle++) {
            for (int i = 0; i < message.length(); i++) {
                System.out.print(message.charAt(i));
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.print("\r"); // Reset the line for the next cycle
        }
        System.out.println("\r");
        System.out.println(Config);
    }

    public static void main(String[] args){
        //Initializing the system
          initialize();
    }

}
