package Code;

import java.io.File;
import java.io.FileWriter; // Um in Datein zu Schreiben
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner; // Um Text datein zu lesen
import java.io.IOException; // Um fehler zu handeln

public class HighscoreManager {
    private static final String HIGHSCORE_FILE = "highscores.txt"; // name der Datei
    private static final int MAX_HIGHSCORES = 10; // Legt die maximalen Highscores fest
    private ArrayList<ScoreData> scores = new ArrayList<ScoreData>();
    File myObj; // Objekt in dem die Datei "Gespeichert" wird

    // Konstruktor
    public HighscoreManager() {
        myObj = new File(HIGHSCORE_FILE);
        try {
            // Versucht datei zu erstellen
            if (myObj.createNewFile()) {
                // Bei erstellen
                System.out.println("Datei erstellt: " + myObj.getName());
            } else {
                // Wenn bereits erstellt
                System.out.println("Datei exesetiert bereits: " + myObj.getName());
            }
        } catch (IOException e) {
            // Bei fehler
            System.out.println("Es ist ein fehler beim erstellen der datei aufgetreten: " + myObj.getName());
            e.printStackTrace();
        }
        loadScores(); // Ließt die Highscores aus der Datei und speichert sie in die ArrayList
    }

    // Speichert higscore in ArrayList und Datei
    public void addHigscore(int higscore, String player1, String player2) {

        // "highscore" zu ArrayList hinzufügen
        scores.add(new ScoreData(higscore, player1, player2));

        // Highscores Sortieren
        Collections.sort(scores, (score1, score2) -> {
            return Integer.compare(score2.getScore(), score1.getScore());
        });

        trim(); // Letzten Highscore Löschen

        // Liste "scores" in Datei Speichern
        writeScores(scores);
    }

    // Werte aus der Datei laden und in die ArrayList Speichern
    public void loadScores() {
        scores.clear();
        try {
            Scanner myScanner = new Scanner(myObj);

            // Solange eine nächste zeile exestiert..
            while (myScanner.hasNextLine()) {
                // ...wird wird der inhalt zu einem intager umgewandelt und in die "scores" ArrayList geschrieben
                 // speichert die werte aus der zeile in einem Array ab (getrennt durch semikolon)
                String[] split = myScanner.nextLine().split(";");
                // speichert die werte aus dem Array in die ArrayList und wandelt den Highscores wert von String zu int um
                scores.add(new ScoreData(Integer.parseInt(split[0]), split[1], split[2]));  
            }
            myScanner.close();
        } catch (IOException e) { // bei Fehler
            System.out.println("Fehler beim Speichern des Higscores in die ArrayList");
        }
    }

    // ArrayList in datei speichern
    public void writeScores(ArrayList<ScoreData> liste) {
        System.out.println("Scores abgespeichert");
        try {
            // FileWriter anlegen um zu schreiben
            FileWriter myWriter = new FileWriter(HIGHSCORE_FILE);

            // Wirklich schreiben (werte werden durch semikolon getrennt)
            for (ScoreData score : liste) {
                myWriter.write(score.getScore() + ";" + score.getPlayerName1() + ";" + score.getPlayerName2() + "\n");
                // System.out.println("In Datei geschrieben");
            }

            // myWriter wieder schließen
            myWriter.close();

        } catch (IOException e) {
            // Falls ein fehler entsteht
            System.out.println("Fehler beim Highscore schreiben!");
            e.printStackTrace();
        }
    }

    // Löscht alle highscores die zuviel sind
    public void trim() {
        while (scores.size() > MAX_HIGHSCORES) {
            scores.remove(MAX_HIGHSCORES);
        }
    }

    // getter
    public ArrayList<ScoreData> getScores() {
        return scores;
    }

    // gibt alle werte des übergebenen platzes als String zurück
    public String toStringIndex(int index) {
        loadScores();
        String satz = "";

        satz += scores.get(index).getPlayerName1() + " & ";
        satz += scores.get(index).getPlayerName2() + ":  ";
        satz += scores.get(index).getScore();
        return satz;
    }

    // Geben werte von einem besteimmten platz zurück
    public String getScore(int index){
        return Integer.toString(scores.get(index).getScore());
    }

    public String getPlayerName1(int index){
        return scores.get(index).getPlayerName1();
    }

    public String getPlayerName2(int index){
        return scores.get(index).getPlayerName2();
    }

}
