// Aaron Jacob
// AXJ210111

import java.io.*;
import java.util.Scanner;

public class Main {
    public static double calculateOrderCost(char drinkSize, String drinkType, double pricePerSquareInch, int numDrinks) {
        // All costs in USD.
        final double sodaPerOunceCost = 0.20;
        final double teaPerOunceCost = 0.12;
        final double punchPerOunceCost = 0.15;

        // All surface area calculations in square inches.
        // Surface area formula: pi * cup diameter * cup height
        final double smallSurfaceArea = Math.PI * 4 * 4.5;
        final double mediumSurfaceArea = Math.PI * 4.5 * 5.75;
        final double largeSurfaceArea = Math.PI * 5.5 * 7;

        // All volumes in fluid ounces.
        final double smallVolume = 12;
        final double mediumVolume = 20;
        final double largeVolume = 32;

        double orderCost = 0;

        // Order cost formula: Number of drinks to buy * (drink per-ounce cost * drink volume + graphic price per square
        // inch * drink surface area)
        if (drinkSize == 'S' && drinkType.equals("soda")) {
            orderCost = numDrinks * (sodaPerOunceCost * smallVolume + pricePerSquareInch * smallSurfaceArea);
        }
        else if (drinkSize == 'M' && drinkType.equals("soda")) {
            orderCost = numDrinks * (sodaPerOunceCost * mediumVolume + pricePerSquareInch * mediumSurfaceArea);
        }
        else if (drinkSize == 'L' && drinkType.equals("soda")) {
            orderCost = numDrinks * (sodaPerOunceCost * largeVolume + pricePerSquareInch * largeSurfaceArea);
        }
        else if (drinkSize == 'S' && drinkType.equals("tea")) {
            orderCost = numDrinks * (teaPerOunceCost * smallVolume + pricePerSquareInch * smallSurfaceArea);
        }
        else if (drinkSize == 'M' && drinkType.equals("tea")) {
            orderCost = numDrinks * (teaPerOunceCost * mediumVolume + pricePerSquareInch * mediumSurfaceArea);
        }
        else if (drinkSize == 'L' && drinkType.equals("tea")) {
            orderCost = numDrinks * (teaPerOunceCost * largeVolume + pricePerSquareInch * largeSurfaceArea);
        }
        else if (drinkSize == 'S' && drinkType.equals("punch")) {
            orderCost = numDrinks * (punchPerOunceCost * smallVolume + pricePerSquareInch * smallSurfaceArea);
        }
        else if (drinkSize == 'M' && drinkType.equals("punch")) {
            orderCost = numDrinks * (punchPerOunceCost * mediumVolume + pricePerSquareInch * mediumSurfaceArea);
        }
        else if (drinkSize == 'L' && drinkType.equals("punch")) {
            orderCost = numDrinks * (punchPerOunceCost * largeVolume + pricePerSquareInch * largeSurfaceArea);
        }

        return orderCost;
    }

    public static Customer[] addPreferredCustomerToPreferredCustomerArray(Customer preferredCustomer,
                                                                          Customer[] originalArray) {
        Customer[] newArray = new Customer[originalArray.length + 1];

        for (int i = 0; i < originalArray.length; i++) {
            newArray[i] = originalArray[i];
        }

        newArray[newArray.length - 1] = preferredCustomer;

        return newArray;
    }

    public static Customer[] removeRegularCustomerFromRegularCustomerArray(int index, Customer[] originalArray) {
        for (int i = index; i < originalArray.length - 1; i++) {
            originalArray[i] = originalArray[i + 1];
        }

        Customer[] newArray = new Customer[originalArray.length - 1];

        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = originalArray[i];
        }

        return newArray;
    }

    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);

        System.out.print("Enter the regular customer file name: ");
        String regularCustomerFileName = scnr.nextLine();

        FileInputStream inputFileStream = new FileInputStream(regularCustomerFileName);
        Scanner inputFileScanner = new Scanner(inputFileStream);

        int numRegularCustomers = 0;

        while (inputFileScanner.hasNextLine()) {
            inputFileScanner.nextLine();
            numRegularCustomers++;
        }

        Customer[] regularCustomerArray = new Customer[numRegularCustomers];

        inputFileScanner.close();
        inputFileStream = new FileInputStream(regularCustomerFileName);
        inputFileScanner = new Scanner(inputFileStream);

        String guestId;
        String firstName;
        String lastName;
        double amountSpent;

        for (int i = 0; i < numRegularCustomers; i++) {
            guestId = inputFileScanner.next();
            firstName = inputFileScanner.next();
            lastName = inputFileScanner.next();
            amountSpent = inputFileScanner.nextDouble();

            regularCustomerArray[i] = new Customer(firstName, lastName, guestId, amountSpent);
        }

        // TODO: Remove debug statements
        for (Customer customer : regularCustomerArray) {
            System.out.println(customer);
        }

        inputFileScanner.close();

        System.out.print("Enter the preferred customer file name: ");
        String preferredCustomerFileName = scnr.nextLine();

        int numPreferredCustomers = 0;

        try {
            inputFileStream = new FileInputStream(preferredCustomerFileName);
            inputFileScanner = new Scanner(inputFileStream);

            while (inputFileScanner.hasNextLine()) {
                inputFileScanner.nextLine();
                numPreferredCustomers++;
            }

            inputFileScanner.close();
        }
        catch (FileNotFoundException exception) { /* Do nothing when a FileNotFoundException is thrown. */ }

        Customer[] preferredCustomerArray = new Customer[0];

        if (numPreferredCustomers > 0) {
            preferredCustomerArray = new Customer[numPreferredCustomers];

            inputFileStream = new FileInputStream(preferredCustomerFileName);
            inputFileScanner = new Scanner(inputFileStream);

            int discountPercentage;
            int bonusBucks;
            String discountPercentageOrBonusBucks;

            for (int i = 0; i < numPreferredCustomers; i++) {
                guestId = inputFileScanner.next();
                firstName = inputFileScanner.next();
                lastName = inputFileScanner.next();
                amountSpent = inputFileScanner.nextDouble();
                discountPercentageOrBonusBucks = inputFileScanner.next();

                if (discountPercentageOrBonusBucks.contains("%")) {
                    discountPercentage = Integer.parseInt(discountPercentageOrBonusBucks.substring(0,
                            discountPercentageOrBonusBucks.indexOf("%")));

                    preferredCustomerArray[i] = new Gold(firstName, lastName, guestId, amountSpent,
                            discountPercentage);
                }
                else {
                    bonusBucks = Integer.parseInt(discountPercentageOrBonusBucks);

                    preferredCustomerArray[i] = new Platinum(firstName, lastName, guestId, amountSpent,
                            bonusBucks);
                }
            }

            inputFileScanner.close();

            // TODO: Remove debug statements
            for (Customer customer : preferredCustomerArray) {
                System.out.println(customer);
            }
        }

        System.out.print("Enter the order file name: ");
        String orderFileName = scnr.nextLine();

        inputFileStream = new FileInputStream(orderFileName);
        inputFileScanner = new Scanner(inputFileStream);

        while (inputFileScanner.hasNextLine()) {
            String orderLine = inputFileScanner.nextLine();

            Customer customer = null;
            String orderGuestId;
            String guestStatus = "";
            int guestArrayIndex = -1;
            String drinkSizeString;
            char drinkSize;
            String drinkType;
            double pricePerSquareInch;
            int numDrinks;

            Scanner orderLineScanner = new Scanner(orderLine);

            // TODO: For core implementation, assume all orders in the order file are valid.
            orderGuestId = orderLineScanner.next();
            drinkSize = orderLineScanner.next().charAt(0);
            drinkType = orderLineScanner.next();
            pricePerSquareInch = orderLineScanner.nextDouble();
            numDrinks = orderLineScanner.nextInt();

            double orderCost = calculateOrderCost(drinkSize, drinkType, pricePerSquareInch, numDrinks);

            // TODO: Remove debug statement
            System.out.println(orderCost);

            for (int i = 0; i < regularCustomerArray.length; i++) {
                if (regularCustomerArray[i].getGuestId().equals(orderGuestId)) {
                    // TODO: Guest ID matches order.
                    customer = regularCustomerArray[i];
                    guestStatus = "regular";
                    guestArrayIndex = i;
                    break;
                }
            }

            for (int i = 0; i < preferredCustomerArray.length; i++) {
                if (preferredCustomerArray[i].getGuestId().equals(orderGuestId)) {
                    // TODO: Guest ID matches order.
                    customer = preferredCustomerArray[i];
                    double guestAmountSpent = preferredCustomerArray[i].getAmountSpent();

                    if (guestAmountSpent >= 50 && guestAmountSpent < 200) {
                        guestStatus = "gold";
                    }
                    else {
                        guestStatus = "platinum";
                    }

                    guestArrayIndex = i;
                    break;
                }
            }

            if (customer.getAmountSpent() < 200) {
                double newAmountSpentBeforeAddedDiscounts = customer.getAmountSpent() + orderCost;

                if (newAmountSpentBeforeAddedDiscounts >= 150) {
                    customer.setAmountSpent(customer.getAmountSpent() + 0.85 * orderCost);
                }
                else if (newAmountSpentBeforeAddedDiscounts >= 100) {
                    customer.setAmountSpent(customer.getAmountSpent() + 0.90 * orderCost);
                }
                else if (newAmountSpentBeforeAddedDiscounts >= 50) {
                    customer.setAmountSpent(customer.getAmountSpent() + 0.95 * orderCost);
                }
                else {
                    customer.setAmountSpent(newAmountSpentBeforeAddedDiscounts);
                }

                if (customer.getAmountSpent() >= 200) {
                    Customer upgradedCustomer = new Platinum(customer.getFirstName(),
                            customer.getLastName(), customer.getGuestId(), customer.getAmountSpent(),
                            (int) ((customer.getAmountSpent() - 200) / 5));

                    if (guestStatus.equals("regular")) {
                        preferredCustomerArray = addPreferredCustomerToPreferredCustomerArray(upgradedCustomer,
                                preferredCustomerArray);
                        regularCustomerArray = removeRegularCustomerFromRegularCustomerArray(guestArrayIndex,
                                regularCustomerArray);
                    }
                    else {
                        preferredCustomerArray[guestArrayIndex] = upgradedCustomer;
                    }
                }
                else if (customer.getAmountSpent() >= 50 && guestStatus.equals("regular")) {
                    int newDiscountPercentage = 0;

                    if (customer.getAmountSpent() >= 150) {
                        newDiscountPercentage = 15;
                    }
                    else if (customer.getAmountSpent() >= 100) {
                        newDiscountPercentage = 10;
                    }
                    else if (customer.getAmountSpent() >= 50) {
                        newDiscountPercentage = 5;
                    }

                    Customer upgradedCustomer = new Gold(customer.getFirstName(), customer.getLastName(),
                            customer.getGuestId(), customer.getAmountSpent(), newDiscountPercentage);

                    preferredCustomerArray = addPreferredCustomerToPreferredCustomerArray(upgradedCustomer,
                            preferredCustomerArray);
                    regularCustomerArray = removeRegularCustomerFromRegularCustomerArray(guestArrayIndex,
                            regularCustomerArray);
                }
                else if (customer.getAmountSpent() >= 50 && guestStatus.equals("gold")) {
                    if (customer.getAmountSpent() >= 150) {
                        ((Gold) customer).setDiscountPercentage(15);
                    }
                    else if (customer.getAmountSpent() >= 100) {
                        ((Gold) customer).setDiscountPercentage(10);
                    }
                    else {
                        ((Gold) customer).setDiscountPercentage(5);
                    }
                }
            }
            else {
                if (orderCost >= ((Platinum) customer).getBonusBucks()) {
                    orderCost -= ((Platinum) customer).getBonusBucks();
                    ((Platinum) customer).setBonusBucks((int) (orderCost / 5));
                }
                else {
                    ((Platinum) customer).setBonusBucks(
                            ((Platinum) customer).getBonusBucks() - (int) Math.ceil(orderCost)
                    );

                    orderCost = 0;
                }

                customer.setAmountSpent(customer.getAmountSpent() + orderCost);
            }

            // TODO: Remove debug statements
            System.out.println("Regular ---");
            for (Customer c : regularCustomerArray) {
                System.out.println(c);
            }

            System.out.println("Preferred ---");
            for (Customer c : preferredCustomerArray) {
                System.out.println(c);
            }
        }

        FileOutputStream outputFileStream = new FileOutputStream("customer.dat");
        PrintWriter outputFileWriter = new PrintWriter(outputFileStream);

        for (Customer c : regularCustomerArray) {
            outputFileWriter.print(c.getGuestId() + " ");
            outputFileWriter.print(c.getFirstName() + " ");
            outputFileWriter.print(c.getLastName() + " ");
            outputFileWriter.printf("%.2f\n", Math.round(c.getAmountSpent() * 100) / 100.0);
        }

        outputFileWriter.close();

        outputFileStream = new FileOutputStream("preferred.dat");
        outputFileWriter = new PrintWriter(outputFileStream);

        for (Customer c : preferredCustomerArray) {
            outputFileWriter.print(c.getGuestId() + " ");
            outputFileWriter.print(c.getFirstName() + " ");
            outputFileWriter.print(c.getLastName() + " ");
            outputFileWriter.printf("%.2f ", Math.round(c.getAmountSpent() * 100) / 100.0);

            if (c.getAmountSpent() >= 200) {
                outputFileWriter.print(((Platinum) c).getBonusBucks() + "\n");
            }
            else {
                outputFileWriter.print(((Gold) c).getDiscountPercentage() + "%\n");
            }
        }

        outputFileWriter.close();
    }
}