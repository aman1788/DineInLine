import java.util.Scanner;
import java.util.Arrays;

// File Handling Imports
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;


public class DineInLineV1 {

//    Initialize Variables
    public static Scanner scanner = new Scanner(System.in);

    //    queue sizes
    private static final int cashier1_queueSize = 2;
    private static final int cashier2_queueSize = 3;
    private static final int cashier3_queueSize = 5;

    //    queues
    private static String[] cashier1 = new String[cashier1_queueSize];
    private static String[] cashier2 = new String[cashier2_queueSize];
    private static String[] cashier3 = new String[cashier3_queueSize];

    private static String[] queuesList = new String[7];

    private static final int maxBurgerStock = 50;
    private static int burgerStock = maxBurgerStock;
    private static String userViewMenuResponse = " ";



    public static void main (String[] args) {

//        User is prompted with if user wants to view the menu
        while (!userViewMenuResponse.equals("n")) {
            System.out.println("View DineInLine Console Menu ([Y] for yes or [N] for no) ?");
            userViewMenuResponse = scanner.next().toLowerCase();

            if (userViewMenuResponse.equals("y")){
                String consoleMenu = """
                    
                    100 or VFQ: View all Queues.
                    101 or VEQ: View all Empty Queues.
                    102 or ACQ: Add customer to a Queue.
                    103 or RCQ: Remove a customer from a Queue. 
                    104 or PCQ: Remove a served customer.
                    105 or VCS: View Customers Sorted in alphabetical order.
                    106 or SPD: Store Program Data into file.
                    107 or LPD: Load Program Data from file.
                    108 or STK: View Remaining burgers Stock.
                    109 or AFS: Add burgers to Stock.
                    999 or EXT: Exit the Program.
                    """;
                System.out.println(consoleMenu);

//                Prompt User for menu option
                System.out.println("Enter Option: ");
                String user_choice = scanner.next().toUpperCase();

//                the relevant methods are called based on user's input
                switch (user_choice) {
                    case "100", "VFQ", "101", "VEQ":
                        viewQueues();
                        break;
                    case "102", "ACQ":
                        addCustomer();
                        break;
                    case "103", "RCQ":
                        removeCustomer();
                        break;
                    case "104", "PCQ":
                        removeServedCustomer();
                        break;
                    case "105", "VCS":
                        viewCustomersSorted();
                        break;
                    case "106", "SPD":
                        storeProgramData();
                        break;
                    case "107", "LPD":
                        loadProgramData();
                        break;
                    case "108", "STK":
                        viewRemainingBurgers();
                        break;
                    case "109", "AFS":
                        addBurgers();
                        break;
                    case "999", "EXT":
                        userViewMenuResponse = "n";
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid code please try again!");
                }

            }else if (!userViewMenuResponse.equals("n")){
                System.out.println("Sorry i didn't understand you, please try again!! ");
            }
        }

    }

    /**
     * Checks for empty slots in the queue and if so returns "X" if not "O"
     * @param queue the queue array to check for empty slots
     * @param queueSize queue array's length
     * @return String queueSlotStatus with string containing either "X" or "O"
     */
    public static String queueStatus(String[] queue,int queueSize){
        String queueSlotStatus = "";
        for (int i = 0; i < queueSize; i++){
            queueSlotStatus += queue[i] != null ? "O" : "X";
        }
        return queueSlotStatus;
    }


    /**
     * Displays vertical queues with O - occupied or X - unoccupied
     */
    public static void viewQueues(){
        String queue1 = queueStatus(cashier1,cashier1_queueSize);
        String queue2 = queueStatus(cashier2,cashier2_queueSize);
        String queue3 = queueStatus(cashier3,cashier3_queueSize);

        String line1 = """
                ******************
                *    Cashiers    *
                ******************
                """;
        String line2 = "   " + queue1.charAt(0) + "    " + queue2.charAt(0) + "    " + queue3.charAt(0);
        String line3 = "   " + queue1.charAt(1) + "    " + queue2.charAt(1) + "    " + queue3.charAt(1);
        String line4 = "        " + queue2.charAt(2) + "    " + queue3.charAt(2);
        String line5 = "             " + queue3.charAt(3);
        String line6 = "             " + queue3.charAt(4);
        String line7 = "O-Occupied X-Unoccupied";

        System.out.print(line1);
        System.out.println(line2);
        System.out.println(line3);
        System.out.println(line4);
        System.out.println(line5);
        System.out.println(line6);
        System.out.println(line7);


        queuesList = new String[]{line1, line2, line3, line4, line5, line6, line7};

    }


    /**
     * Checks for empty slots(null) in the queue and if so returns true, if not false
     * @param queue the queue array to check for empty slots
     * @return boolean value true or false
     */
    public static boolean checkQueueAvailability(String[] queue) {
        for(String customer : queue){
            if (customer==null){
                return true;
            }
        }
        return false;
    }


    /**
     * Adds a customer to the queue
     * @param queue the queue array to add the customer
     * @param queueSize the queue array's length
     * @param customer the customer to add
     */
    public static void addCustomerQueue(String[] queue,int queueSize, String customer) {
        for (int i=0;i<queueSize;i++){
            if(queue[i]==null){
                queue[i] = customer;
                break;
            }
        }
    }


    /**
     * adds customer to a queue by checking if a queue is empty and then adding the customer
     * uses both checkQueueAvailability() & addCustomerQueue() methods
     */
    public static void addCustomer() {
        System.out.println("Please enter customer name: ");
        String customer = scanner.next() ;

        if (checkQueueAvailability(cashier1)) {
            addCustomerQueue(cashier1,cashier1_queueSize,customer);
            burgerStock -= 5;
            System.out.println("Added customer " + customer);
        } else if (checkQueueAvailability(cashier2)) {
            addCustomerQueue(cashier2,cashier2_queueSize,customer);
            burgerStock -= 5;
            System.out.println("Added customer " + customer);
        } else if (checkQueueAvailability(cashier3)) {
            addCustomerQueue(cashier3,cashier3_queueSize,customer);
            burgerStock -= 5;
            System.out.println("Added customer " + customer);
        } else {
            System.out.println("All queues are full!!");
        }

        if (burgerStock <= 10){
            System.out.println("Warning!...10 Burgers Remaining...add more burgers!");
        }

    }


    /**
     * Gets the cashier(queue) based on the cashierNumber parameter value
     * @param cashierNumber the cashier/queue to return
     * @return cashier array (String[])
     */
    public static String[] getCashier(int cashierNumber) {

        switch (cashierNumber) {
            case 1:
                return cashier1;
            case 2:
                return cashier2;
            case 3:
                return cashier3;
            default:
                return null;
        }
    }


    /**
     * remove a customer from any queue and any position in the queue
     * uses the getCashier() method to get the queue to remove customer from
     */
    public static void removeCustomer(){
        while (true){
            System.out.println("Remove customer from which cashier (1, 2 or 3)? ");
            int cashierNumber = scanner.nextInt();

            String[] cashier = getCashier(cashierNumber);

            if (cashier==null){
                System.out.println("Invalid cashier number");
                continue;
            }

            System.out.println("Remove customer from which queue position in cashier "+ cashierNumber + "?" );
            int queuePosition = scanner.nextInt();

            if (queuePosition < 1 || queuePosition > cashier.length) {
                System.out.println("Invalid queue position!! ");
                continue;
            }

            if (cashier[queuePosition - 1] != null){
                String removedCustomer = cashier[queuePosition - 1];
                cashier[queuePosition - 1] = null;
                System.out.println("Removed customer " + removedCustomer + " from queue position " + queuePosition + " in cashier " + cashierNumber);
            } else {
                System.out.println("No customer found in queue position " + queuePosition);
            }
            break;
        }
    }


    /**
     * removes a served customer
     * calls the removeCustomer() method and additionally deducts 5 burgers from burgerStock
     */
    public static void removeServedCustomer(){
        removeCustomer();
        burgerStock -= 5;
    }


    /**
     * Gets all customers from all the queues to one array 'customers'.
     * @param queue1 first queue array
     * @param queue2 second queue array
     * @param queue3 third queue array
     * @return the customers array (String[]) containing all customers from all three queues.
     */
    public static String[] getAllCustomers(String[] queue1, String[] queue2, String[] queue3){
        int combinedSize = queue1.length + queue2.length + queue3.length ;
        String[] customers = new String[combinedSize];

        int i = 0;

        for (String customer : queue1){
            if (customer != null){
                customers[i] = customer;
                i++;
            }
        }

        for (String customer : queue2){
            if (customer != null){
                customers[i] = customer;
                i++;
            }
        }

        for (String customer : queue3){
            if (customer != null){
                customers[i] = customer;
                i++;
            }
        }

        return customers;
    }


    /**
     * Sorts the customers array alphabetically
     * uses method getAllCustomers() to get all customers from all 3 queues to one 'customers' array
     * Arrays.sort() returns index values based on comparing 2 customers and checking for null values.
     */
    public static void viewCustomersSorted(){
        String[] customers = getAllCustomers(cashier1,cashier2,cashier3);

        Arrays.sort(customers,(customer1,customer2) ->  {
            if (customer1 == null && customer2== null){
                return 0;
            } else if (customer1 == null) {
                return 1;
            } else if (customer2 == null){
                return -1;
            }
            return customer1.compareTo(customer2);
        });

        System.out.println("Customers : ");
        for (String customer : customers){
            if (customer != null) {
                System.out.println("  " + customer);
            }
        }

    }


    /**
     * Reference - CodeJava (www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java)
     * Storing Program Data - queues , burger stock and customers to a file.
     * file named ProgramData.txt
     */
    public static void storeProgramData(){
        try{
            FileWriter writer = new FileWriter("ProgramData.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("-------------------------------------------------------------------------------");
            bufferedWriter.newLine();

//            write the queues data to file
            for (String queue : queuesList){
                bufferedWriter.write(queue);
                bufferedWriter.newLine();

            }

            String remainingBurgers = Integer.toString(burgerStock);

//            write burger stock to file
            bufferedWriter.newLine();
            bufferedWriter.write("Burger Stock : ");
            bufferedWriter.write(remainingBurgers);
            bufferedWriter.newLine();


            String[] customers = getAllCustomers(cashier1,cashier2,cashier3);
            bufferedWriter.newLine();
            bufferedWriter.write("Customers : ");
            bufferedWriter.newLine();
            for (String customer : customers){
                if (customer != null){
                    bufferedWriter.write("   " + customer);
                    bufferedWriter.newLine();
                }
            }

            bufferedWriter.write("-------------------------------------------------------------------------------");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Program Data Stored. ");

    }


    /**
     * Reference - w3schools (www.w3schools.com/java/java_files_read.asp)
     * Reads and displays program data from stored data file - ProgramData.txt
     */
    public static void loadProgramData() {
        try {
            File file = new File("ProgramData.txt");
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String file_data = fileReader.nextLine();
                System.out.println(file_data);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * Displays remaining burgers left
     */
    public static void viewRemainingBurgers(){
        System.out.println("Burgers left : " + burgerStock);
    }


    /**
     * Adds burgers to the burger stock
     * Maximum burger stock at any given time is 50 burgers
     * Prompts the user for amount of burgers to add and displays error message if user enters value which makes
     * the total burger stock exceed 50 or if burger stock reaches 50.
     */
    public static void addBurgers(){
        while (true){
            System.out.println("How many burgers do you want to add? : ");
            int burgersToAdd = scanner.nextInt();
            int newBurgerStock = burgerStock + burgersToAdd;

            if (newBurgerStock <= maxBurgerStock){
                burgerStock += burgersToAdd;
                System.out.println(burgersToAdd + " burgers added.");
                break;
            }else if (burgerStock == maxBurgerStock){
                System.out.println("Cannot add...Maximum stock reached (50)!!");
                break;
            }else if (newBurgerStock > 50){
                int maxBurgersAdd = maxBurgerStock - burgerStock;
                System.out.println("You can add a maximum of " + maxBurgersAdd + " Burgers...try again!");
            }else {
                System.out.println("Sorry i didn't understand that..please try again!!");
            }
        }

    }



}
