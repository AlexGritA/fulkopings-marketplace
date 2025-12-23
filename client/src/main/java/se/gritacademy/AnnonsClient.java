package se.gritacademy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AnnonsClient {

    //Hämtar alla annonser från servern
    static void getAllAnnonser() {
        try {
            URL url = new URL("http://localhost:8080/annonser"); //Skapar URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Öppnar anslutning
            connection.setRequestMethod("GET"); //Använder GET-metod

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()) //Läser svar från servern
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); //Skriver ut varje rad
            }

            reader.close(); //Stänger läsaren

        } catch (Exception e) {
            System.out.println("Kunde inte hämta annonser"); //Felhantering
        }
    }

    //Hämtar en annons baserat på id
    static void getAnnonsById(int id) {
        try {
            URL url = new URL("http://localhost:8080/annonser/" + id); //URL med id
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Öppnar anslutning
            connection.setRequestMethod("GET"); //GET-metod

            int responseCode = connection.getResponseCode(); //Kontrollerar svar
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); //Skriver ut rad
                }
                reader.close();
            } else if (responseCode == 404) {
                System.out.println("Ingen annons med det id:t hittades."); //404 Not Found
            }

        } catch (Exception e) {
            System.out.println("Kunde inte hämta annons"); //Felhantering
        }
    }

    //Skapar en ny annons på servern
    static void createAnnons(String jsonAnnons) {
        sendJsonToServer("http://localhost:8080/annonser", "POST", jsonAnnons); //Använder hjälpmetod
    }
    //Uppdaterar en befintlig annons baserat på id
    static void updateAnnons(int id, String jsonAnnons) {
        sendJsonToServer("http://localhost:8080/annonser/" + id, "PUT", jsonAnnons); //Hjälpmetod
    }

    //Raderar en annons baserat på id
    static void deleteAnnonsById(int id,  int pinkod) {
        try {
            URL url = new URL("http://localhost:8080/annonser/" + id + "?pinkod=" + pinkod); //URL med id
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Öppnar anslutning
            connection.setRequestMethod("DELETE"); //DELETE-metod

            int responseCode = connection.getResponseCode(); //Kontrollerar svar
            if (responseCode == 204) {
                System.out.println("Annons raderad!"); //204 No Content
            } else if (responseCode == 404) {
                System.out.println("Ingen annons med det id:t hittades."); //404 Not Found
            }

        } catch (Exception e) {
            System.out.println("Kunde inte radera annons"); //Felhantering
        }
    }

    //Hjälpmetod för att skicka JSON vid POST och PUT
    private static void sendJsonToServer(String urlString, String method, String jsonAnnons) {
        try {
            URL url = new URL(urlString); //Skapar URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Öppnar anslutning
            connection.setRequestMethod(method); //Använder den valda metoden (POST/PUT)
            connection.setRequestProperty("Content-Type", "application/json"); //Header: JSON
            connection.setDoOutput(true); //Tillåter att skriva till kropp

            OutputStream os = connection.getOutputStream();
            os.write(jsonAnnons.getBytes()); //Skickar JSON
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode(); //Kontrollerar svar

            if (method.equals("POST")) {
                if (responseCode == 201) {
                    System.out.println("Annons skapad!"); //201 Created
                } else if (responseCode == 409) {
                    System.out.println("Annons med samma id finns redan."); //409 Conflict
                }
            } else if (method.equals("PUT")) {
                if (responseCode == 200) {
                    System.out.println("Annons uppdaterad!"); //200 OK
                } else if (responseCode == 404) {
                    System.out.println("Ingen annons med det id:t hittades."); //404 Not Found
                }
            }

        } catch (Exception e) {
            System.out.println("Kunde inte skicka annons till servern"); //Felhantering
        }
    }
}
