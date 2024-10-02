import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class car {
    private String carId;

    private String brand;

    private String model;

    private double basepriceperday;

    private boolean isAvailable;


    public car(String carId, String brand, String model, double basepriceperday) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basepriceperday = basepriceperday;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculateprice(int rentalDays) {
        return basepriceperday * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returncar() {
        isAvailable = true;
    }
}

class Customer{
    private String customerId;

    private String name;

    public Customer(String customerId,String name){
        this.customerId=customerId;
        this.name=name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental{

    private car car;

    private Customer customer;

    private int days;

    public Rental(car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem{

    private List<car> cars;

    private List<Customer> customers;

    private List<Rental> rental;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rental=new ArrayList<>();
    }

    public void addcar(car car){
        cars.add(car);
    }

    public void addcustomer(Customer customer){
        customers.add(customer);
    }

    public void rentcar(car car,Customer customer,int days) {
        if (car.isAvailable()) {
            car.rent();
            rental.add(new Rental(car, customer, days));
        } else {
            System.out.println("car is not available for Rent");
        }
    }

    public void returnCar(car car){
        car.returncar();
        Rental rentalToRemove=null;
        for (Rental rental:rental){
            if(rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }
        if (rentalToRemove!=null){
            rental.remove(rentalToRemove);
        }else {
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner scanner=new Scanner(System.in);

        while (true){
            System.out.println("==== Car Rental System ====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            int choice=scanner.nextInt();
            scanner.nextLine();

            if(choice==1){
                System.out.println("\n==Rent a car==\n");
                System.out.println("Enter your name: ");
                String customerName=scanner.nextLine();

                System.out.println("\nAvailable Cars: ");
                for (car car:cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCarId()+"-"+car.getBrand()+"-"+car.getModel());
                    }
                }

                System.out.println("\nEnter the car ID you want to rent: ");
                String carId=scanner.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays=scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer=new Customer("CUS"+(customers.size()+1),customerName);
                addcustomer(newCustomer);

                car selectedCar=null;
                for (car car:cars){
                    if(car.getCarId().equals(carId)&& car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }

                if (selectedCar!=null) {
                    double totalPrice = selectedCar.calculateprice(rentalDays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer NAME: " + newCustomer.getName());
                    System.out.println("Car: "+selectedCar.getBrand()+" "+selectedCar.getModel());
                    System.out.println("Rental Days: "+rentalDays);
                    System.out.printf("total Price: $%.2f%n",totalPrice);

                    System.out.println("\nConfirm rental(Yes/No): ");
                    String confirm=scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Yes")){
                        rentcar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\nCar rented successfully.");
                    }
                    else {
                        System.out.println("\nRental canceled");
                    }
                }else {
                    System.out.println("\n Invalid car selection or car not available for rent.");
                }
            } else if (choice==2) {
                System.out.println("\n==Return a Car==\n");
                System.out.println("Enter the car ID you want to return: ");
                String carId=scanner.nextLine();

                car carToReturn =null;
                for (car car:cars){
                    if(car.getCarId().equals(carId)&&!car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }
                if(carToReturn!=null){
                    Customer customer=null;
                    for (Rental rental:rental){
                        if (rental.getCar()==carToReturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }

                    if(customers!=null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by "+customer.getName());
                    }else {
                        System.out.println("Car was not rented or rental information is missing");
                    }
                }else {
                    System.out.println("Invalid car ID or Car is not rented");
                }
            } else if (choice==3) {
                break;
            }else {
                System.out.println("Invalid Choice.Please enter a valid option");
            }
        }
        System.out.println("\n Thank you for using the Car Rental System");
    }

}


public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem=new CarRentalSystem();
        car car1=new car("MP001","HONDA"," ACCORD ",70);
        car car2=new car("MP002","TOYOTA"," CAMRY ",60);
        car car3=new car("MP003","MAHINDRA"," XUV400 ",70);
        rentalSystem.addcar(car1);
        rentalSystem.addcar(car2);
        rentalSystem.addcar(car3);
        rentalSystem.menu();

        }
    }
