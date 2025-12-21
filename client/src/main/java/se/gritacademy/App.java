package se.gritacademy;

import tools.jackson.databind.ser.jdk.JDKKeySerializers;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Klienten startad!");

        boolean running = true;

        while (running) {
            System.out.println("== Fulköpings-annonser ==");
            System.out.println("1. Visa alla annonser");
            System.out.println("2. Visa annons med id");
            System.out.println("3. Skapa ny anons");
            System.out.println("4. Ändra pris");
            System.out.println("5. Radera annons");
            System.out.println("0. Avsluta");
            System.out.println("Välj: ");

            //läser in ett nummer
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Visa alla annonser");
                    break;
                case 2:
                    System.out.println("Visa annons med id");
                    break;
                case 3:
                    System.out.println("Skapa ny annons");
                    break;
                case 4:
                    System.out.println("Ändra pris");
                    break;
                case 5:
                    System.out.println("Radera annons");
                    break;
                case 0:
                    running = false;
                    System.out.println("Avslutar");
                    break;
                default:
                    System.out.println("Ogiltigt val");
            }
        }

    }
}
