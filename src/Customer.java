public class Customer {

    String firstName;
    String secondName;
    String fullName;

    int burgersRequired;
    private static final int burgerPrice = 650;



    Customer(String firstName, String secondName, int burgersRequired){
        this.firstName = firstName;
        this.secondName = secondName;
        this.burgersRequired = burgersRequired;
        this.fullName = this.firstName + " " + this.secondName;
    }



    public int totalIncome(){
        return burgersRequired * burgerPrice;
    }



}
