import java.util.ArrayList;
import java.util.Scanner;

public class FoodQueue {

    public static Scanner scanner = new Scanner(System.in);
    public ArrayList<Customer> queue = new ArrayList<>();
    public int queueSize;
    public int queueIncome = 0;

    FoodQueue (int queueSize) {
        this.queueSize = queueSize;
    }

    public void addCustomer(Customer customer){
        queue.add(customer);
    }

    public static void removeCustomerFromQueue(FoodQueue queue){
        while (true){
            System.out.println("Remove customer from which position in the queue? ");
            int queuePosition = scanner.nextInt();

            if (queuePosition < 1 || queuePosition > queue.queue.size()) {
                System.out.println("Invalid queue position...try again!!");
                continue;
            }

            if (queue.queue.get(queuePosition - 1) != null){
                Customer removedCustomer = queue.queue.get(queuePosition - 1);
                DineInLine.burgerStock += removedCustomer.burgersRequired;
                queue.queueIncome -= removedCustomer.totalIncome();
                queue.queue.remove(queuePosition - 1);
                System.out.println("Removed customer " + removedCustomer.fullName);
            } else {
                System.out.println("No customer found in queue position " + queuePosition);
            }
            break;
        }
    }


}
