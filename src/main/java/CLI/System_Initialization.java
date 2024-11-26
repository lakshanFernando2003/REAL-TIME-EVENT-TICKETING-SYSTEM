package CLI;
import java.util.InputMismatchException;
import java.util.Scanner;


public class System_Initialization {
    //Active configuration for the system
    private static System_configuration activeConfig;

    public static void main(String[] args){
        System.out.println("     <----- Real Time Ticketing System ----->");
        menu();
        System_run();

    }

    public static void menu(){
        System.out.println("************************************************** ");
        System.out.println("*                   MENU OPTIONS                 * ");
        System.out.println("************************************************** ");
        System.out.println(" 1) Create a new Configuration \n 2) Load a existing Config File \n 0) Exit program");
    }

    public static void System_run(){
        try {
            Scanner User_option = new Scanner(System.in);
            System.out.println("Select an option : ");
            int option = User_option.nextInt();
            if (option< 0 || option >2){
                System.out.println("Please choose a valid option to continue");
                System_run();
            }else{
                switch (option){
                    case 1:
                        System.out.println("\nStart Creating a new configuration\n");
                        initialize();
                        break;
                    case 2:
                        System.out.println("\nLoading an existing Configuration\n");
                        LoadConfig();
                        break;
                    case 0:
                        System.out.println("\nExiting program.......\n");
                        System.exit(0);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + option);
                }
            }


        }catch (InputMismatchException e){
            System.out.println("Please use the recommended data type");
            System_run();
        }
    }

    public static void initialize(){
        Scanner ReadInput = new Scanner(System.in);
         activeConfig = new System_configuration();

        // input and validating Total tickets
        System.out.println("Enter Total number of Tickets : ");
        try {
            while (true){
                int Total_Tickets = ReadInput.nextInt();
                if (Total_Tickets > 0){
                    activeConfig.setTotal_Tickets(Total_Tickets);
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
                    activeConfig.setTicket_Release_Rate(TicketReleaseRate);
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
                    activeConfig.setCustomer_Retrieval_Rate(CustomerRetrievalRate);
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
                if (MaxTicketCapacity > 0 && MaxTicketCapacity <= activeConfig.getTotal_Tickets()){
                    activeConfig.setMax_Ticket_Capacity(MaxTicketCapacity);
                    break;
                }else {
                    System.out.println("Invalid input. Release rate must be greater than 0 or equal to total tickets. please try again");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid Data Type... Enter a correct Input.  ");
            initialize();
        }
        animation();
        System.out.println(activeConfig+ "\n");
        Savefile();
    }

    public static void animation(){
        //Small loading animation to CLI
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
    }
    //  To save the configuration Created by the user
    public static void Savefile(){
        Scanner save =  new Scanner(System.in);
        System.out.println("would you like to save this Configuration? (Yes/No) : ");
        try {
            while (true) {
                String user_choice = save.next().toLowerCase();
                if (user_choice.equals("yes")) {
                    System.out.println("Enter file name to save");
                    String file_name = save.next();
                    activeConfig.SaveFile(file_name);
                    break;
                } else if (user_choice.equals("no")) {
                    break; // needed to be updated as the future implementation
                } else {
                    System.out.println("Error ~ Enter Only (Yes or No)");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Error ~ use correct data type");
            save.next();
        }
    }

    // To load an existing configuration
    public static void LoadConfig(){
        Scanner loader =  new Scanner(System.in);
        System.out.println("Enter the Config file name to load : ");
        while(true){
            String FileName = loader.next();
            activeConfig = System_configuration.LoadFile(FileName);
            if (activeConfig != null){
                animation();
                System.out.println(activeConfig);
                break;
            } else {
                System.out.println("Error ~ Failed to Load. Please check the file name and Try Again.");
            }
        }

    }

}
