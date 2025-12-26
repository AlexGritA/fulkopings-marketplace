[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/cATxCFEq)
# README.MD

Här kan du dokumentera ditt projekt. 

- Projektets mål och funktionalitet:
Projektets mål är att skapa ett annons program. Jag valde att skapa det
menyn i konsolen som används för hela programmmet.
Man kan använda det för att skapa, läsa, ändra och ta bort annonser.

- Instruktioner för hur man startar servern och klientapplikationen:
Först, starta servern genom:
1. Öppna projektet i t.ex. IntelliJ.  
2. Kör "ServerApplication" (main-metoden).  
3. Servern startar på "http://localhost:8080".
Andra steget är att starta klienten:
1. Kör "Main" klassen.
2. Välj önskat nästa steg i menyn. Här kan du skapa, ändra och radera annonser.
För varje ändring och radering behövs en pinkod som överensstämmer med den 
pinkod som bestämdes när annons skapades. 

- Exempel på API-anrop

1. GET "http://localhost:8080/annonser"  
   - Hämtar alla annonser.  
   - Första gången fanns inga annonser, så svaret blev "[]".  
   - Efter att ha lagt till en annons med POST dök informationen upp vid nästa GET-anrop.

2. POST "http://localhost:8080/annonser"  
   - Lägger till en ny annons.  
   - Annonserna hamnar i minneslistan på servern och kan sedan visas med GET anrop.

Andra dagen
Jag testade alla fyra API-anrop och allt fungerar:

1. GET – hämtar annonser från servern.  
2. POST – lägger till nya annonser.  
3. PUT – ändrar befintliga annonser, t.ex. priset (kräver rätt pinkod).  
4. DELETE – tar bort annonser (kräver rätt pinkod).
