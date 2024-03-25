import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// File imports
import java.io.*;


public class DineInLine {
    public static Scanner scanner = new Scanner(System.in);

    private static FoodQueue queue1 = new FoodQueue(2);
    private static FoodQueue queue2 = new FoodQueue(3);
    private static FoodQueue queue3 = new FoodQueue(5);

    private static FoodQueue waitingListQueue = new FoodQueue(100);
    private static boolean queuesFull = false;

    private static String[] queuesList = new String[7];

    private static final int maxBurgerStock = 50;
    public static int burgerStock = maxBurgerStock;
    private static final int burgerPrice = 650;

    private static String userViewMenuResponse = " ";


    public static void main(String[] args) {

//        User is prompted with if user wants to view the menu
        while (!userViewMenuResponse.equals("n")) {
            System.out.println("View Food Fave Console Menu ([Y] for yes or [N] for no) ?");
            userViewMenuResponse = scanner.next().toLowerCase();


            if (userViewMenuResponse.equals("y")) {
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
                    case "110", "IFQ":
                        queuesIncome();
                        break;
                    case "999", "EXT":
                        userViewMenuResponse = "n";
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid code please try again!");
                }

            } else if (!userViewMenuResponse.equals("n")) {
                System.out.println("Sorry i didn't understand you, please try again!! ");
            }
        }

    }

    /**
     * Checks for empty slots in the queue and if so returns "X" if not "O"
     * @param queue the queue object to check for empty slots
     * @return String queueSlotStatus with string containing either "X" or "O"
     */
    public static String queueStatus(FoodQueue queue) {
        Customer[] customers = new Customer[queue.queueSize];

        for (int i=0; i < queue.queue.size(); i++){
            customers[i] = queue.queue.get(i);
        }

        String queueSlotStatus = "";
        for (int i = 0; i < queue.queueSize; i++) {
            queueSlotStatus += customers[i] != null ? "O" : "X";
        }
        return queueSlotStatus;
    }


    /**
     * Displays vertical queues with O - occupied or X - unoccupied
     */
    public static void viewQueues() {
        String queue1_slots = queueStatus(queue1);
        String queue2_slots = queueStatus(queue2);
        String queue3_slots = queueStatus(queue3);

        String line1 = """
                ******************
                *    Cashiers    *
                ******************
                """;
        String line2 = "   " + queue1_slots.charAt(0) + "    " + queue2_slots.charAt(0) + "    " + queue3_slots.charAt(0);
        String line3 = "   " + queue1_slots.charAt(1) + "    " + queue2_slots.charAt(1) + "    " + queue3_slots.charAt(1);
        String line4 = "        " + queue2_slots.charAt(2) + "    " + queue3_slots.charAt(2);
        String line5 = "             " + queue3_slots.charAt(3);
        String line6 = "             " + queue3_slots.charAt(4);
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
     * Get queue with the shortest length
     * @param queue1 queue number 1
     * @param queue2 queue number 2
     * @param queue3 queue number 3
     * @return array with FoodQueue objects sorted with queues
     */
    public static FoodQueue[] queueWithMinLength(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3) {
        FoodQueue[] queues;

        if (queue1.queue.size() <= queue2.queue.size()) {
            if (queue1.queue.size() <= queue3.queue.size()) {
                return new FoodQueue[]{queue1, queue2, queue3};
            } else {
                return new FoodQueue[]{queue3, queue1, queue2};
            }
        } else {
            if (queue2.queue.size() <= queue3.queue.size()) {
                return new FoodQueue[]{queue2, queue3, queue1};
            } else {
                return new FoodQueue[]{queue3, queue2, queue1};
            }
        }
    }


    /**
     * Adds customer to shortest queue
     * @param firstName firstname of customer
     * @param lastName lastname of customer
     * @param burgersRequired number of burgers ordered by customer.
     */
    public static void addCustomerToQueue(String firstName, String lastName, int burgersRequired){
        Customer customer = new Customer(firstName, lastName, burgersRequired);

        FoodQueue[] queues = queueWithMinLength(queue1, queue2, queue3);

        if (queues[0].queueSize != queues[0].queue.size()) {
            queues[0].addCustomer(customer);
            burgerStock -= customer.burgersRequired;
            queues[0].queueIncome += customer.totalIncome();
            System.out.println("Customer " + customer.firstName + " " + customer.secondName + " added...");
        } else if (queues[1].queueSize != queues[1].queue.size()) {
            queues[1].addCustomer(customer);
            burgerStock -= customer.burgersRequired;
            queues[1].queueIncome += customer.totalIncome();
            System.out.println("Customer " + customer.firstName + " " + customer.secondName + " added...");
        } else if (queues[2].queueSize != queues[2].queue.size()) {
            queues[2].addCustomer(customer);
            burgerStock -= customer.totalIncome();
            queues[2].queueIncome += burgerPrice;
            System.out.println("Customer " + customer.firstName + " " + customer.secondName + " added...");
        } else {
            queuesFull = true;
            waitingListQueue.addCustomer(customer);
            System.out.println("All queues are full...customer added to waiting list.");
        }
    }

    /**
     * Adds customer to the queue by calling the addcustomerToQueue method.
     */
    public static void addCustomer() {

        System.out.print("Enter firstname : ");
        String firstName = scanner.next();

        System.out.print("Enter lastname : ");
        String lastName = scanner.next();

        System.out.print("Enter the amount of burgers required : ");
        String amountOfBurgers = scanner.next();
        int burgersRequired = Integer.parseInt(amountOfBurgers);

        addCustomerToQueue(firstName,lastName,burgersRequired);

    }


    /**
     * gets the queue by the parameter queueNumber
     * @param queueNumber the queue to return
     * @return queue according to parameter queueNumber
     */
    public static FoodQueue getQueue(int queueNumber) {
        switch (queueNumber) {
            case 1:
                return queue1;
            case 2:
                return queue2;
            case 3:
                return queue3;
            default:
                return null;
        }
    }


    public static void removeCustomer() {
        while (true) {
            System.out.println("Remove customer from which queue (1, 2 or 3)? ");
            int queueNumber = scanner.nextInt();

            FoodQueue queue = getQueue(queueNumber);

            if (queue == null) {
                System.out.println("Invalid queue number...try again!!");
                continue;
            }
            FoodQueue.removeCustomerFromQueue(queue);
            break;
        }
    }


    public static void removeServedCustomer() {
        removeCustomer();

        boolean waitingListStatus = waitingListQueue.queue.isEmpty();
        if (waitingListStatus){
            queuesFull = false;
        }else {
            queuesFull = true;
        }

        if (queuesFull){
            Customer waitingListCustomer = waitingListQueue.queue.get(0);
            addCustomerToQueue(waitingListCustomer.firstName,waitingListCustomer.secondName,waitingListCustomer.burgersRequired);
            waitingListQueue.queue.remove(0);
        }

    }


    public static ArrayList<String> getAllCustomers(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3){
        ArrayList<String> customers = new ArrayList<>();

        int i = 0;

        for (Customer customer : queue1.queue){
            customers.add(i,customer.fullName);
            i++;
        }

        for (Customer customer : queue2.queue){
            customers.add(i,customer.fullName);
            i++;
        }

        for (Customer customer : queue3.queue){
            customers.add(i,customer.fullName);
            i++;
        }

        return customers;
    }


    public static void viewCustomersSorted() {
        ArrayList<String> customers = getAllCustomers(queue1,queue2,queue3);

        Collections.sort(customers);

        System.out.println("Customers : ");
        for (String customer : customers){
            System.out.println("  " + customer);
        }

    }


    public static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("ProgramData.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("-------------------------------------------------------------------------------");
            bufferedWriter.newLine();

//            write the queues data to file
            for (String queue : queuesList) {
                bufferedWriter.write(queue);
                bufferedWriter.newLine();

            }

            String remainingBurgers = Integer.toString(burgerStock);

//            write burger stock to file
            bufferedWriter.newLine();
            bufferedWriter.write("Burger Stock : ");
            bufferedWriter.write(remainingBurgers);
            bufferedWriter.newLine();

//            write queue incomes
            bufferedWriter.newLine();
            bufferedWriter.write("Total income for queue 1 : Rs " + queue1.queueIncome);
            bufferedWriter.newLine();
            bufferedWriter.write("Total income for queue 2 : Rs " + queue2.queueIncome);
            bufferedWriter.newLine();
            bufferedWriter.write("Total income for queue 3 : Rs " + queue3.queueIncome);
            bufferedWriter.newLine();


            ArrayList<String> customers = getAllCustomers(queue1,queue2,queue3);
            bufferedWriter.newLine();
            bufferedWriter.write("Customers : ");
            bufferedWriter.newLine();
            for (String customer : customers){
                bufferedWriter.write("   " + customer);
                bufferedWriter.newLine();
            }

            bufferedWriter.write("-------------------------------------------------------------------------------");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("An error occurred...try again!");
        }

        System.out.println("Program Data Stored. ");

    }


    public static void loadProgramData(){
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
        } catch (Exception e) {
            System.out.println("An error occurred...try again!");
        }

    }


    /**
     * Displays remaining burgers left
     */
    public static void viewRemainingBurgers() {
        System.out.println("Burgers left : " + burgerStock);
    }


    /**
     * Adds burgers to the burger stock
     * Maximum burger stock at any given time is 50 burgers
     * Prompts the user for amount of burgers to add and displays error message if user enters value which makes
     * the total burger stock exceed 50 or if burger stock reaches 50.
     */
    public static void addBurgers() {
        while (true) {
            System.out.println("How many burgers do you want to add? : ");
            int burgersToAdd = scanner.nextInt();
            int newBurgerStock = burgerStock + burgersToAdd;

            if (newBurgerStock <= maxBurgerStock) {
                burgerStock += burgersToAdd;
                System.out.println(burgersToAdd + " burgers added.");
                break;
            } else if (burgerStock == maxBurgerStock) {
                System.out.println("Cannot add...Maximum stock reached (50)!!");
                break;
            } else if (newBurgerStock > 50) {
                int maxBurgersAdd = maxBurgerStock - burgerStock;
                System.out.println("You can add a maximum of " + maxBurgersAdd + " Burgers...try again!");
            } else {
                System.out.println("Sorry i didn't understand that..please try again!!");
            }
        }

    }



    public static void queuesIncome(){

        System.out.println("Choose queue (1,2 or 3) ? ");
        String response = scanner.next();

        switch (response){
            case "1":
                System.out.println("Total income for queue 1 : Rs " + queue1.queueIncome);
                break;
            case "2":
                System.out.println("Total income for queue 2 : Rs " + queue2.queueIncome);
                break;
            case "3":
                System.out.println("Total income for queue 3 : Rs " + queue3.queueIncome);
                break;
            default:
                System.out.println("Invalid input...try again!!");
        }

    }


}
