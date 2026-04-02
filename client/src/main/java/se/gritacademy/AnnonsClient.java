package se.gritacademy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

// AnnonsClient:
// Skickar HTTP-anrop till servern
// Använder HttpURLConnection

// GET – hämtar alla annonser
// POST – skapar ny annons
// PUT – uppdaterar pris
// DELETE – tar bort annons

public class AnnonsClient {

    //För att kunna konvertera JSON-sträng till Java-objekt
    private static final ObjectMapper mapper = new ObjectMapper();

    //Hämtar alla annonser från servern
    // Hämtar alla annonser från servern och returnerar dem som en lista av Annons-objekt
    static List<Annons> getAllAnnonser(String sortering) {
        try {
            URL url = new URL("http://localhost:8080/annonser?sortering=" + sortering);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            String json = readResponse(connection);
            return mapper.readValue(json, new TypeReference<List<Annons>>() {});

        } catch (Exception e) {
            System.out.println("Kunde inte hämta annonser");
            return null;
        }
    }

    //Hämtar en annons baserat på id
    // Hämtar en annons från servern och returnerar den som ett Annons-objekt
    static Annons getAnnons(int id) {
        try {
            URL url = new URL("http://localhost:8080/annonser/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 404) {
                System.out.println("Ingen annons med det id:t hittades.");
                return null;
            }

            String json = readResponse(connection);
            return mapper.readValue(json, Annons.class);

        } catch (Exception e) {
            System.out.println("Kunde inte hämta annons");
            return null;
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
    //Läser HTTP-svaret och returnerar det som en sträng
    private static String readResponse(HttpURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        return sb.toString();
    }

}
