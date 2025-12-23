package se.gritacademy;

import java.util.Scanner;

import static se.gritacademy.AnnonsClient.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); //Läser in från konsolen
        System.out.println("Klienten startad!");

        boolean running = true;
        int pinkod;

        while (running) {
            //Meny
            System.out.println("== Fulköpings-annonser ==");
            System.out.println("1. Visa alla annonser");
            System.out.println("2. Visa annons med id");
            System.out.println("3. Skapa ny annons");
            System.out.println("4. Ändra pris");
            System.out.println("5. Radera annons");
            System.out.println("0. Avsluta");
            System.out.print("Välj: ");

            int choice = scanner.nextInt(); //Läser val
            scanner.nextLine(); //Rensar ny rad

            switch (choice) {
                case 1: //Visa alla annonser
                    getAllAnnonser();
                    break;

                case 2: //Visa annons med id
                    System.out.print("Ange id: ");
                    int getId = scanner.nextInt();
                    scanner.nextLine();
                    getAnnonsById(getId);
                    break;

                case 3: //Skapa ny annons
                    System.out.print("Id: ");
                    int newId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ämnesrad: ");
                    String amnesrad = scanner.nextLine();
                    System.out.print("Beskrivning: ");
                    String beskrivning = scanner.nextLine();
                    System.out.print("Pris: ");
                    double pris = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Säljare namn: ");
                    String namn = scanner.nextLine();
                    System.out.print("Säljare epost: ");
                    String epost = scanner.nextLine();
                    System.out.print("Säljare telefon: ");
                    String telefon = scanner.nextLine();
                    System.out.print("Pinkod: ");
                    pinkod = scanner.nextInt();
                    scanner.nextLine();

                    //Skapar JSON manuellt
                    String jsonNyAnnons = "{"
                            + "\"id\":" + newId + ","
                            + "\"amnesrad\":\"" + amnesrad + "\","
                            + "\"beskrivning\":\"" + beskrivning + "\","
                            + "\"pris\":" + pris + ","
                            + "\"saljare\":{"
                            + "\"namn\":\"" + namn + "\","
                            + "\"epost\":\"" + epost + "\","
                            + "\"telefon\":\"" + telefon + "\""
                            + "},"
                            + "\"pinkod\":" + pinkod
                            + "}";

                    createAnnons(jsonNyAnnons); //Skickar till servern
                    break;

                case 4: //Ändra pris
                    System.out.print("Ange id för annons som ska ändras: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Ange pinkod: ");
                    pinkod = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nytt pris: ");
                    double newPris = scanner.nextDouble();
                    scanner.nextLine();

                    //Skapar JSON med nytt pris, genom id, pris och pinkod
                    String jsonUpdate = "{"
                            + "\"pris\":" + newPris + ","
                            + "\"pinkod\":" + pinkod
                            + "}";
                    updateAnnons(updateId, jsonUpdate); //Skickar till servern
                    break;

                case 5: //Radera annons
                    System.out.print("Ange id för annons som ska raderas: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Ange pinkod: ");
                    pinkod = scanner.nextInt();
                    scanner.nextLine();

                    deleteAnnonsById(deleteId, pinkod); //Skickar DELETE
                    break;

                case 0: //Avsluta
                    running = false;
                    System.out.println("Avslutar");
                    break;

                default:
                    System.out.println("Ogiltigt val");
            }
        }

        scanner.close(); //Stänger scanner
    }
}
