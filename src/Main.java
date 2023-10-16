import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;

public class Main {
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

        Customer[] preferredCustomerArray;

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

                    preferredCustomerArray[i] = new GoldCustomer(firstName, lastName, guestId, amountSpent,
                            discountPercentage);
                }
                else {
                    bonusBucks = Integer.parseInt(discountPercentageOrBonusBucks);

                    preferredCustomerArray[i] = new PlatinumCustomer(firstName, lastName, guestId, amountSpent,
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

            String orderGuestId;
            String guestStatus;
            int guestArrayIndex;
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
        }
    }
}