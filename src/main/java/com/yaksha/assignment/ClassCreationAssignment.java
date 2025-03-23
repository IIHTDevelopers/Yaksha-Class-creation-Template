package com.yaksha.assignment;

class Car {  // Task 1: Create a Class
    // Properties (Attributes)
    String model;
    int year;

    // Constructor to initialize object
    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }

    // Method to display car details
    public void displayDetails() {
        System.out.println("Car Model: " + model);
        System.out.println("Car Year: " + year);
    }
}

public class ClassCreationAssignment {
    public static void main(String[] args) {
        
        // Task 2: Create an Object
        System.out.println("Task 2: Create an Object");
        Car car1 = new Car("Toyota", 2020);  // Creating an object
        car1.displayDetails();  // Calling method to display details of car1

        // Task 3: Create Multiple Objects
        System.out.println("\nTask 3: Create Multiple Objects");
        Car car2 = new Car("Honda", 2018);  // Creating another object
        Car car3 = new Car("Ford", 2022);   // Creating another object

        car2.displayDetails();  // Calling method to display details of car2
        car3.displayDetails();  // Calling method to display details of car3
    }
}
