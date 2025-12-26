package se.gritacademy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

// AnnonsClient:
// Skickar HTTP-anrop till servern
// Använder HttpURLConnection

// GET – hämtar alla annonser
// POST – skapar ny annons
// PUT – uppdaterar pris
// DELETE – tar bort annons

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
    static void getAnnons(int id) {
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
    static void deleteAnnons(int id, int pinkod) {
        try {
            // Skapa URL med id och pinkod som query-param
            URL url = new URL("http://localhost:8080/annonser/" + id + "?pinkod=" + pinkod);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            // Hämta response code
            int responseCode = connection.getResponseCode();

            // Stream för att läsa meddelandet från servern
            BufferedReader reader;
            if (responseCode >= 400) { // Felkoder (4xx, 5xx)
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else { // 2xx OK
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }

            // Läs hela servermeddelandet
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Skriv ut servermeddelandet
            if (response.length() > 0) {
                System.out.println(response.toString());
            } else {
                // Om servern skickar 204 No Content
                if (responseCode == 204) {
                    System.out.println("Annons raderad!");
                } else {
                    System.out.println("Okänt svar, kod: " + responseCode);
                }
            }

        } catch (Exception e) {
            System.out.println("Kunde inte radera annons");
            e.printStackTrace();
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

                BufferedReader reader;
                if (responseCode >= 400) {
                    reader = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream()));
                } else {
                    reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                }

                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                if (response.length() > 0) {
                    System.out.println(response.toString());
                } else {
                    System.out.println("Svar från servern, kod: " + responseCode);
                }
            }


        } catch (Exception e) {
            System.out.println("Kunde inte skicka annons till servern"); //Felhantering
        }
    }
}
