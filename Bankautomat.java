private static int guthaben; // Deklariert eine Variable für den Kontostand

void main() { // Startpunkt des Programms
    guthaben = 1_000; // Die Variable wird initilasiert und das Startguthaben wird auf 1000 gesetzt. Der Unterstrich (_) in 1_000 ist einfach nur eine Schreibweise zur besseren Lesbarkeit von Zahlen.
    while (true) { // Startet eine Endlosschleife für das Hauptmenü
        switch (IO.readln("Bitte geben Sie eine Operation (GUTHABEN,EINZAHLEN,ABHEBEN,ZINSESZINS,STOP) ein:\n")) { // Die Klasse IO ist neu und wurde mit Java 25 eingeführt. Der Befehl bedeutet zeige dem Benutzer den Text an, lies seine Eingabe und entscheide dann mit switch, was zu tun ist. Ohne die IO Klasse müsste man den Scanner importieren und ein neues Scanner Objekt erstellen.
            case "GUTHABEN" -> guthaben(); // Führt Methode guthaben() aus
            case "EINZAHLEN" -> einzahlen(); // Führt Methode einzahlen() aus
            case "ABHEBEN" -> abheben(); // Führt Methode abheben() aus
            case "ZINSESZINS" -> zinseszins(); // Führt Methode zinseszins() aus
            case "STOP" -> { // Startet den Block für den Abbruch
                stop(); // Führt die Abschiedsmethode aus
                return; // return hat zwei Bedeutungen gleichzeitig: 1. Wert zurückgeben (wenn vorhanden), 2. Funktion sofort beenden (immer!), denn man kann nichts "zurückgeben", ohne fertig zu sein, deshalb beendet return die Funktion.
            } // Schließt den STOP Block
        } // Schließt die switch Abfrage
    } // Schließt die while Schleife
} // Schließt die main Methode

private static void guthaben() { // Methode zur reinen Zahlenausgabe
    IO.println(guthaben); // Druckt den aktuellen Kontostand auf die Konsole
} // Schließt die Methode

private static void guthabenText() { // Methode für formatierte Textausgabe
    IO.println("Ihr Guthaben beträgt " + guthaben + " EURO."); // Gibt den Kontostand als ganzen Satz aus
} // Schließt die Methode

private static void einzahlen() { // Methode zum Einzahlen von Geld
    String deposit = IO.readln("Wie viel Geld möchten Sie einzahlen? Es wird eine Gebühr von 5% berechnet.\n"); // Fragt Einzahlungsbetrag ab
    try { // Versucht den folgenden Code fehlerfrei auszuführen
        double amount = Double.parseDouble(deposit); // Wandelt die Texteingabe von deposit in eine Kommazahl um
        if (amount <= 0) { // Prüft, ob der Betrag negativ oder 0 ist
            IO.println("Betrag negativ oder 0."); // Gibt Fehlermeldung aus
            return; // Bricht die Methode vorzeitig ab
        } // Schließt die if Bedingung

        int net = (int) Math.ceil(amount * 0.95); // Berechnet 95% des Betrags (zieht 5% ab) und runde mit Math.ceil eine Methode aus der Java Klasse java.lang.Math immer nach oben auf. Ceil = "aufrunden". Das (int) ist ein sogenannter Cast. Es wandelt den Double Wert in ein Int um, dabei werden einfach die Nachkommastellen entfernt.
        guthaben += net; // Addiert den berechneten Nettobetrag zum Guthaben
        guthabenText(); // Ruft Methode für die Ausgabe des neuen Kontostands auf
    } catch (NumberFormatException e) { // Fängt ab, falls keine gültige Zahl eingegeben wurde
        IO.println("Betrag ungültig."); // Gibt Fehlermeldung aus
    } // Schließt den catch Block
} // Schließt die Methode

private static void abheben() { // Methode zum Abgeben von Geld
    String withdrawal = IO.readln("Wie viel Geld möchten Sie abheben?\n"); // Fragt Abhebebetrag ab
    try { // Versucht den folgenden Code fehlerfrei auszuführen
        int amount = Integer.parseInt(withdrawal); // Wandelt die Texteingabe in eine Ganzzahl um
        if (amount <= 0) { // Prüft, ob der Betrag negativ oder 0 ist
            IO.println("Beitrag negativ oder 0."); // Gibt Fehlermeldung aus
            return; // Bricht die Methode vorzeitig ab
        } // Schließt die if Bedingung
        if (amount > guthaben) { // Prüft, ob der Abhebebetrag das Guthaben übersteigt
            IO.println("Guthaben nicht ausreichend."); // Gibt Fehlermeldung aus
            return; // Bricht die Methode vorzeitig ab
        } // Schließt die if Bedingung
        guthaben -= amount; // Zieht den Betrag vom Guthaben ab
        guthabenText(); // Ruft Methode für die Ausgabe des neuen Kontostands auf
    } catch (NumberFormatException e) { // Fängt ab, falls keine gültige Zahl eingegeben wurde
        IO.println("Betrag ungültig."); // Gibt Fehlermeldung aus
    } // Schließt den catch-Block
} // Schließt die Methode

private static void zinseszins() { // Methode zur Zinsberechnung über mehrere Jahre
    String years = IO.readln("Wie viele Jahre wollen Sie Ihr Guthaben verzinsen?\n"); // Fragt die Laufzeit ab
    try { // Versucht den folgenden Code fehlerfrei auszuführen
        int n = Integer.parseInt(years); // Wandelt die Jahreseingabe in eine Ganzzahl um
        if (n <= 0) { // Prüft, ob die Jahresanzahl negativ oder 0 ist
            IO.println("Jahresanzahl ungültig."); // Gibt Fehlermeldung aus
            return; // Bricht die Methode vorzeitig ab
        } // Schließt die if Bedingung

        for (int i = 0; i < n; i++) { // Startet eine Schleife, die für jedes Jahr einmal durchläuft
            guthaben = (int) Math.ceil(guthaben * 1.04) - 2; // Berechnet 4% Zinsen, rundet auf und zieht 2 Euro Gebühr ab
            if (guthaben < 0) { // Prüft, ob das Guthaben durch die 2 Euro Gebühr ins Minus gerutscht ist
                guthaben = 0; // Setzt das Guthaben auf 0 (verhindert Schulden)
                IO.println("Kein Guthaben mehr verfügbar!"); // Gibt Warnmeldung aus
                break; // Bricht die Jahresschleife vorzeitig ab
            } // Schließt die if Bedingung
        } // Schließt die for Schleife
        guthabenText(); // Ruft Methode für die Ausgabe des finalen Kontostands auf
    } catch (NumberFormatException e) { // Fängt ab, falls keine gültige Jahreszahl eingegeben wurde
        IO.println("Jahresanzahl ungültig."); // Gibt Fehlermeldung aus
    } // Schließt den catch Block
} // Schließt die Methode

private static void stop() { // Methode für das Ende des Programms
    IO.println("Ihr Guthaben beträgt " + guthaben + " EURO. Vielen Dank und bis zum nächsten Mal!"); // Gibt Abschiedsnachricht mit finalem Kontostand aus
} // Schließt die Methode