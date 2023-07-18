package Code;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
  // Fenster Größe festlegen
  public final static int HOEHE = 800;
  public final static int BREITE = 800;

  // Highscore
  private int highscore = 0;
  private boolean highscoreGestzt = false;
  private HighscoreManager highscoreManager = new HighscoreManager();

  // Medienplayer
  private static SoundPlayer sound = new SoundPlayer();
  private BilderManager bilder = new BilderManager();

  Random random = new Random();

  // Spieler erstellen
  public static Player player1 = new Player(50, 75, ((BREITE / 2) - 25) - 100, HOEHE - 125);
  public static Player player2 = new Player(50, 75, ((BREITE / 2) - 25) + 100, HOEHE - 125);
  private int skin1 = 2;
  private int skin2 = 2;

  // Keyinputs
  private boolean keyA, keyD, keyLeft, keyRight = false;

  // Buttons + Textfelder
  private TextField textField1 = new TextField("Name 1");
  private TextField textField2 = new TextField("Name 2");
  private Button startButton = new Button("Spiel Starten");
  private Button neuStartButton = new Button("Zum Hauptmenü");
  private Button closeButton = new Button("Schließen");
  private Button swapper1L = new Button("");
  private Button swapper1R = new Button("");
  private Button swapper2L = new Button("");
  private Button swapper2R = new Button("");
  private Button invisibeButton = new Button();
  private ImageView knopfBildLinks1 = new ImageView(bilder.getLinks());
  private ImageView knopfBildLinks2 = new ImageView(bilder.getLinks());
  private ImageView knopfBildRechts1 = new ImageView(bilder.getRechts());
  private ImageView knopfBildRechts2 = new ImageView(bilder.getRechts());

  // Ufo Liste
  public static ArrayList<Ufo> ufos = new ArrayList<Ufo>();
  private int anzahlUfos = 5;
  // Schuss Liste
  public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  public static ArrayList<Bullet> ufoBullet = new ArrayList<Bullet>();

  // Power-Up liste
  public static ArrayList<PowerUp> powerUps = new ArrayList<>();

  // Gamestatus für Fenstersteuerung
  public static int gameStatus = 1; // 1 = Startmnü, 2 = Spiel, 3 = Spieler gestorben, 4 = Spieler Haben Gewonnen

  public void start(Stage stage) {
    Canvas canvas = new Canvas(BREITE, HOEHE); // Leinwand erstellen
    GraphicsContext gc = canvas.getGraphicsContext2D(); // Farbeeeeeeeeeeeee
    Timeline tl = new Timeline(new KeyFrame(Duration.millis(5), e -> run(gc)));
    tl.setCycleCount(Timeline.INDEFINITE); // Endloswiederholung
    Pane root = new Pane();
    Scene scene = new Scene(root);
    scene.getRoot().setStyle("-fx-font-family: Arial;");
    scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      if (gameStatus == 2) {
        switch (e.getCode()) {
          case A: // A = X-10 PL1
            keyA = true;
            break;
          case D: // D = X+10 PL1
            keyD = true;
            break;
          case LEFT: // LEFT = X-10 PL2
            keyLeft = true;
            break;
          case RIGHT: // RIGHT = X+10 PL2
            keyRight = true; // X+10
            break;
          case SPACE: // SPACE = PL1 schießt
            if (player1.checkShotCounter() == true) {
              bullets.add(new Bullet(player1.getXPos() + 22.5, player1.getYPos(), 2, true));
              sound.spieleShotSound();
            }
            break;
          case ENTER: // ENTER = PL2 schießt
            if (player2.checkShotCounter() == true) {
              bullets.add(new Bullet(player2.getXPos() + 22.5, player2.getYPos(), 2, true));
              sound.spieleShotSound();
            }
            break;
          default: // Default leer
            break;
        }
      }
    }); // End of KeyEvent PRESSED
    stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> { // Taste loslassen
      switch (e.getCode()) {
        case A: // A = X-1 PL1
          keyA = false;
          break;
        case D: // D = X+1 PL1
          keyD = false;
          break;
        case LEFT: // LEFT = X-1 PL2
          keyLeft = false;
          break;
        case RIGHT: // RIGHT = X+1 PL2
          keyRight = false;
          break;
        default: // Default leer
          break;
      }
    });

    // Buttons, Textfelder, Icon und Titel hinzufügen
    root.getChildren().addAll(canvas, textField1, textField2, startButton, neuStartButton, closeButton, swapper1L,
        swapper1R, swapper2L, swapper2R, invisibeButton);
    stage.setTitle("From Outer Space");
    stage.getIcons().add(bilder.getBildSpieler2(2));
    stage.setScene(scene);
    stage.show();
    tl.play();
  }

  private void run(GraphicsContext gc) {
    // ------------------------------Im Spiel------------------------------//
    if (gameStatus == 2) {
      // Backround
      gc.setFill(Color.BLACK);
      gc.drawImage(bilder.getWallpaper(), 0, 0);

      player1.shotCounterErhöhen();
      player2.shotCounterErhöhen();

      // Highscore anzeigen
      gc.setFill(Color.WHITE);
      var small = new Font("Helvetica", 20);
      gc.setFont(small);
      gc.fillText("Highscore: " + highscore, BREITE / 2 - 70, HOEHE - 20);

      // Spielerbewegung
      if (keyA == true) {
        player1.move(-1);
      }
      if (keyD == true) {
        player1.move(+1);
      }
      if (keyLeft == true) {
        player2.move(-1);
      }
      if (keyRight == true) {
        player2.move(+1);
      }

      // Malen
      gc.drawImage(bilder.getBildSpieler1(skin1), player1.getXPos(), player1.getYPos());
      gc.drawImage(bilder.getBildSpieler2(skin2), player2.getXPos(), player2.getYPos());
      // gc.fillRect(player1.getXPos(), player1.getYPos(), player1.getPlayerBreite(),
      // player1.getPlayerHoehe()); //DEBUG HITBOX 1
      // gc.fillRect(player2.getXPos(), player2.getYPos(), player2.getPlayerBreite(),
      // player2.getPlayerHoehe()); //DEBUG HITBOX 2

      // Ufos neu spawnen
      while (ufos.size() < anzahlUfos) {
        ufos.add(new Ufo(random.nextInt(BREITE), 75.0, 1));
        if (highscore % 20 == 0) {
          ufos.add(new Boss());
          sound.spieleSpawn();
        }
      }

      // -----------------------------UFO--------------------------------------------//
      if (highscore % 20 == 0 && highscore != 0 && highscore <= 120) {
        anzahlUfos++;
        highscore++;
      } // end of if
      for (int i = 0; i < ufos.size(); i++) {
        ufos.get(i).move(); // bewegen
        if (ufos.get(i).shot() == true) { // automatisches schießen
          ufoBullet.add(new Bullet(ufos.get(i).getXPos() + ufos.get(i).getBreite() / 2,
              ufos.get(i).getYPos() + ufos.get(i).getHoehe() - 5, -2, false));
        }
        if (ufos.get(i).getAbgeschossen() == false) { // displayen der ufos
          if (ufos.get(i).getBoss() == false) {
            gc.drawImage(bilder.getBildGegner(), ufos.get(i).getXPos(), ufos.get(i).getYPos());
          } else {
            gc.drawImage(bilder.getBoss(), ufos.get(i).getXPos(), ufos.get(i).getYPos());
          } // end of if-else
        }
        if (bullets.size() >= 1) { // checkgetroffen
          if (ufos.get(i).checkAbegeschossen(bullets) == true) {
            sound.spieleKillSound();
            gc.drawImage(bilder.getGetroffen1(), ufos.get(i).getXPos(), ufos.get(i).getYPos());
            powerUps.add(new PowerUp(ufos.get(i).getXPos(), ufos.get(i).getYPos()));
            ufos.remove(i);
            highscore++;
            System.out.println("Ufo abgeschossen: " + i);
            System.out.println("Highsore: " + highscore);
            break;
          }
        }
        if (ufos.get(i).getYPos() > 800) { // spiel endet wenn ufo unten ankommt
          player1.setLeben(0);
          player2.setLeben(0);
        }
      }
      // ----------------------SCHUESSE-----------------------//
      if (bullets.size() >= 1) {
        for (int i = 0; i < bullets.size(); i++) { // Spielerschuesse
          bullets.get(i).move(); // kugel bewegen
          gc.drawImage(bilder.getPlayerShoot(), bullets.get(i).getXPos(), bullets.get(i).getYPos()); // Kugel Malen
          if (bullets.get(i).getYPos() <= 0) { // löschen wenn YPos kleiner als 0 oder größer als HOEHE
            bullets.remove(i);
          } else if (bullets.get(i).getYPos() >= HOEHE) {
            bullets.remove(i);
          }
        }
      }

      // -----------------UFO Bullets--------------------------//
      if (ufoBullet.size() > 0) {
        for (int i = 0; i < ufoBullet.size(); i++) { // Ufoschuesse
          ufoBullet.get(i).move(); // kugel bewegen

          gc.drawImage(bilder.getUfoShoot(), ufoBullet.get(i).getXPos(), ufoBullet.get(i).getYPos()); // Kugel malen
          gc.setFill(Color.RED);
          // gc.fillRect(ufoBullet.get(i).getXPos(), ufoBullet.get(i).getYPos(), 4, 10);

          if (ufoBullet.get(i).getYPos() <= 0) { // löschen wenn YPos kleiner als 0 oder größer als HOEHE
            ufoBullet.remove(i);
          } else if (ufoBullet.get(i).getYPos() >= HOEHE) {
            ufoBullet.remove(i);
          }

          if (
          // Xpos
          ufoBullet.get(i).getXPos() > player1.getXPos()
              && ufoBullet.get(i).getXPos() < player1.getXPos() + player1.getPlayerBreite()
              // Ypos
              && ufoBullet.get(i).getYPos() > player1.getYPos()
              && ufoBullet.get(i).getYPos() < player1.getYPos() + player1.getPlayerHoehe()) {
            // Kugel löschen und leben abziehen
            ufoBullet.remove(i);
            player1.setLeben(player1.getLeben() - 1);
            break;
          }
          // Spieler 2 auf aubschuss prüfen
          if (
          // Xpos
          ufoBullet.get(i).getXPos() > player2.getXPos()
              && ufoBullet.get(i).getXPos() < player2.getXPos() + player2.getPlayerBreite()
              // Ypos
              && ufoBullet.get(i).getYPos() > player2.getYPos()
              && ufoBullet.get(i).getYPos() < player2.getYPos() + player2.getPlayerHoehe()) {
            // Kugel löschen und leben abziehen
            ufoBullet.remove(i);
            player2.setLeben(player2.getLeben() - 1);
            break;
          }
        }
      }

      // ------------------------------Power-Ups------------------------------//
      for (int i = 0; i < powerUps.size(); i++) {
        powerUps.get(i).setYPos(powerUps.get(i).getYPos() + 1); // Power-Up bewegen

        if (powerUps.get(i).getType() == 1) { // Malen wenn typ gleich 1 (Zusätzliches Leben)
          gc.drawImage(bilder.getLeben(), powerUps.get(i).getXPos(), powerUps.get(i).getYPos());

        } else if (powerUps.get(i).getType() == 2) { // Malen wenn typ gleich 2 (Feuer Boost)
          gc.drawImage(bilder.getFireBoost(), powerUps.get(i).getXPos(), powerUps.get(i).getYPos());

        } else if (powerUps.get(i).getType() == 3) { // Malen wenn typ gleich 2 (Bombe)
          gc.drawImage(bilder.getBombe(), powerUps.get(i).getXPos(), powerUps.get(i).getYPos());

        }

        // Auf Kolision prüfen
        if (powerUps.get(i).checkAbegeschossen(player1)) {
          // Power-Up Spieler1 hinzufügen und löschen
          powerUps.get(i).applyPowerUp(player1);
          if (powerUps.get(i).getType() == 3) {
            sound.spieleBombSound();
          } // end of if
          if (powerUps.get(i).getType() == 1) {
            sound.spieleLebenSound();
          } // end of if
          powerUps.remove(i);
          System.out.println("PowerUps benutzt und gelöscht");
          break;
        }

        if (powerUps.get(i).checkAbegeschossen(player2)) {
          // Power-Up Spieler2 hinzufügen und löschen
          powerUps.get(i).applyPowerUp(player2);
          if (powerUps.get(i).getType() == 1) {
            sound.spieleBombSound();
          } // end of if
          if (powerUps.get(i).getType() == 3) {
            sound.spieleLebenSound();
          } // end of if
          powerUps.remove(i);
          System.out.println("PowerUps benutzt und gelöscht");
          break;
        }

        if (powerUps.get(i).getYPos() > HOEHE) { // Power-Up löschen wenn aus Spielfeld
          powerUps.remove(i);
        }
      }

      // Leben Rahmen PL1
      gc.setFill(Color.WHITE);
      gc.fillRect(15, 765, 65, 3);
      gc.fillRect(15, 787, 65, 3);
      gc.fillRect(15, 765, 3, 22);
      gc.fillRect(77, 765, 3, 22);
      // Leben displayen PL1
      switch (player1.getLeben()) {
        case 1:
          gc.setFill(Color.RED);
          gc.fillRect(20, 770, 15, 15);
          gc.setFill(Color.DARKGREY);
          gc.fillRect(40, 770, 15, 15);
          gc.fillRect(60, 770, 15, 15);
          break;
        case 2:
          gc.setFill(Color.RED);
          gc.fillRect(20, 770, 15, 15);
          gc.fillRect(40, 770, 15, 15);
          gc.setFill(Color.DARKGREY);
          gc.fillRect(60, 770, 15, 15);
          break;
        case 3:
          gc.setFill(Color.RED);
          gc.fillRect(20, 770, 15, 15);
          gc.fillRect(40, 770, 15, 15);
          gc.fillRect(60, 770, 15, 15);
          break;
        case 0:
          gc.setFill(Color.DARKGREY);
          gc.fillRect(20, 770, 15, 15);
          gc.fillRect(40, 770, 15, 15);
          gc.fillRect(60, 770, 15, 15);
          gc.setFill(Color.RED);
          gc.setFont(new Font("Looser1", 15));
          gc.fillText("Du bist ein", 15, 745);
          gc.fillText("Looser", 25, 760);
          player1.setXPos(900);
          break;
        default:
          System.out.println("Das kann legit nicht passieren, du hast " + player1.getLeben() + "Leben.");
          player1.setLeben(0);
          System.out.println("Leben auf Null gesetzt");
      }
      // Rahmen leben PL2
      gc.setFill(Color.WHITE);
      gc.fillRect(715, 765, 65, 3);
      gc.fillRect(715, 787, 65, 3);
      gc.fillRect(715, 765, 3, 22);
      gc.fillRect(777, 765, 3, 22);
      // Leben displayen PL2
      switch (player2.getLeben()) {
        case 1:
          gc.setFill(Color.GREEN);
          gc.fillRect(720, 770, 15, 15);
          gc.setFill(Color.DARKGREY);
          gc.fillRect(740, 770, 15, 15);
          gc.fillRect(760, 770, 15, 15);
          break;
        case 2:
          gc.setFill(Color.GREEN);
          gc.fillRect(720, 770, 15, 15);
          gc.fillRect(740, 770, 15, 15);
          gc.setFill(Color.DARKGREY);
          gc.fillRect(760, 770, 15, 15);
          break;
        case 3:
          gc.setFill(Color.GREEN);
          gc.fillRect(720, 770, 15, 15);
          gc.fillRect(740, 770, 15, 15);
          gc.fillRect(760, 770, 15, 15);
          break;
        case 0:
          gc.setFill(Color.DARKGREY);
          gc.fillRect(720, 770, 15, 15);
          gc.fillRect(740, 770, 15, 15);
          gc.fillRect(760, 770, 15, 15);
          gc.setFill(Color.GREEN);
          gc.setFont(new Font("Looser", 15));
          gc.fillText("Du bist ein", 715, 745);
          gc.fillText("Looser", 725, 760);
          player2.setXPos(900);
          break;
        default:
          System.out.println("Das kann legit nicht passieren, du hast " + player2.getLeben() + "Leben.");
          player2.setLeben(0);
          System.out.println("Leben auf Null gesetzt");
      }

      // ------------------------------Gestorben------------------------------//
      if (player1.getLeben() == 0 && player2.getLeben() == 0) {
        gameStatus = 3;
      }
      if (gameStatus == 3) {
        sound.spieleDeathSound();
        while (ufos.size() > 0) {
          ufos.remove(0);
        }
        while (powerUps.size() > 0) {
          powerUps.remove(0);
        }
        if (highscoreGestzt == false) {
          highscoreManager.addHigscore(highscore, player1.getName(), player2.getName());
          highscoreGestzt = true;
          System.out.println("Highscore hinzugefügt");
        }
        gc.drawImage(bilder.getWallpaperEnd(), 0, 0);
        gc.drawImage(bilder.getGameOver(), 140, 80);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Score", 30));
        int abstand = 50;
        for (int i = 0; i < 5; i++) {
          if (highscoreManager.getScores().size() > i) {
            abstand = abstand + 50;
            gc.fillText(highscoreManager.toStringIndex(i), 260, 320 + abstand);
          }
        }

        // Highscore anzeigen
        gc.fillText("Highscore: " + highscore, BREITE / 2 - 70, HOEHE - 20);

        // Restart Button
        neuStartButton.setVisible(true);
        neuStartButton.setLayoutX(350);
        neuStartButton.setLayoutY(700);
        neuStartButton.setScaleX(3);
        neuStartButton.setScaleY(3);
        neuStartButton.setOnAction(new EventHandler<ActionEvent>() { // Code ausführen wenn der Knopf neuStartButton
                                                                     // gedrückt wird
          @Override
          public void handle(ActionEvent event) {
            gameStatus = 1;
            highscoreGestzt = false;

            // textfeld & co. wieder einblende wenn hauptmenü geüffnet wird
            textField1.setVisible(true);
            textField2.setVisible(true);
            startButton.setVisible(true);
            swapper1L.setVisible(true);
            swapper1R.setVisible(true);
            swapper2L.setVisible(true);
            swapper2R.setVisible(true);
            closeButton.setVisible(true);
            startButton.requestFocus();
            invisibeButton.setVisible(false);
            neuStartButton.setVisible(false);

            // Spieler leben Wiederherstellen
            player1.respawn(((BREITE / 2) - 25) - 100, HOEHE - 125);
            player2.respawn(((BREITE / 2) - 25) + 100, HOEHE - 125);

            // highscore zurücksetzen
            highscore = 0;
          }
        });

        invisibeButton.setVisible(true);
        invisibeButton.requestFocus();
        invisibeButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
      }
      // ------------------------------Gewonnen------------------------------//
      if (ufos.size() == 0 && gameStatus != 3) {
        gameStatus = 4;
      }
      if (gameStatus == 4) {
        System.out.println("Du hast gewonnen??!!?!?!!!??! ");
        System.out.println("Higscore: " + highscore);
      }
      // ------------------------------Startmenü------------------------------//
    }
    if (gameStatus == 1) {
      gc.drawImage(bilder.getStart(), 0, 0);
      gc.drawImage(bilder.getUeberschrift(), 30, 40);
      neuStartButton.setVisible(false);
      invisibeButton.setVisible(false);

      // RestartButton
      startButton.setLayoutX(350);
      startButton.setLayoutY(600);
      startButton.setScaleX(3);
      startButton.setScaleY(3);

      startButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          // Code ausführen wenn der Knopf startButton gedrückt wird
          gameStatus = 2;

          ufos.add(new Ufo(random.nextInt(BREITE), 75.0, 1));

          System.out.println("Spiel Status: " + gameStatus);

          player1.setName(textField1.getText());
          player2.setName(textField2.getText());

          // textfeld & co. ausblenden wenn spiel gestartet
          textField1.setVisible(false);
          textField2.setVisible(false);
          startButton.setVisible(false);
          neuStartButton.setVisible(false);
          swapper1L.setVisible(false);
          swapper1R.setVisible(false);
          swapper2L.setVisible(false);
          swapper2R.setVisible(false);
          closeButton.setVisible(false);
          System.out.println("Set Visible false");
        }
      });

      // ----------------------Spieler Auswahl & Benennen---------

      gc.setFont(new Font("Schrift oder so", 25));
      gc.setFill(Color.RED);

      // ---Spieler 1 + Namensbox
      gc.drawImage(bilder.getBildSpieler1(skin1), 590, 200);
      textField1.setLayoutX(570);
      textField1.setLayoutY(280);
      textField1.setPrefWidth(105);

      // ---Spieler 2 + Namensbox
      gc.drawImage(bilder.getBildSpieler2(skin2), 590, 400);
      textField2.setLayoutX(570);
      textField2.setLayoutY(480);
      textField2.setPrefWidth(105);

      // Highscores
      gc.setFill(Color.WHITE);
      gc.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 30));
      gc.fillText("HIGHSCORES", 100, 260);
      gc.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 15));
      int abstand = 20;
      for (int i = 0; i < 5; i++) {
        if (highscoreManager.getScores().size() > i) {
          abstand = abstand + 20;
          gc.fillText(highscoreManager.toStringIndex(i), 100, 250 + abstand);
          // gc.fillText(highscoreManager.getPlayerName1(i), 100, 250 + abstand);
          // gc.fillText(highscoreManager.getPlayerName2(i), 180, 250 + abstand);
          // gc.fillText(highscoreManager.getScore(i), 260, 250 + abstand);
        }
      }

      // Schließknopf
      closeButton.setLayoutX(350);
      closeButton.setLayoutY(700);
      closeButton.setScaleX(3);
      closeButton.setScaleY(3);
      closeButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          Platform.exit();
          System.out.println("Alt + F4");

        }
      });
      // -----Skinswapper Spieler 1
      // Skinswapp buttons
      swapper1L.setLayoutX(540);
      swapper1L.setLayoutY(220);
      swapper1L.setScaleX(1);
      swapper1L.setScaleY(1);
      swapper1L.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
      swapper1L.setGraphic(knopfBildLinks1);
      swapper1L.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          skin1 -= 1;
          if (skin1 < 1) {
            skin1 = 3;
          }
          System.out.println("Skin Nr:" + skin1);
        }
      });
      swapper1R.setLayoutX(660);
      swapper1R.setLayoutY(220);
      swapper1R.setScaleX(1);
      swapper1R.setScaleY(1);
      swapper1R.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
      swapper1R.setGraphic(knopfBildRechts1);
      swapper1R.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          skin1 += 1;
          if (skin1 > 3) {
            skin1 = 1;
          }
          System.out.println("Skin Nr:" + skin1);
        }
      });
      // -----Skinswapper Spieler 2
      // Sjinswapp buttons
      swapper2L.setLayoutX(540);
      swapper2L.setLayoutY(420);
      swapper2L.setScaleX(1);
      swapper2L.setScaleY(1);
      swapper2L.setGraphic(knopfBildLinks2);
      swapper2L.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
      swapper2L.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          skin2 -= 1;
          if (skin2 < 1) {
            skin2 = 3;
          }
          System.out.println("Skin Nr:" + skin2);
        }
      });
      swapper2R.setLayoutX(660);
      swapper2R.setLayoutY(420);
      swapper2R.setScaleX(1);
      swapper2R.setScaleY(1);
      swapper2R.setGraphic(knopfBildRechts2);
      swapper2R.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
      swapper2R.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          skin2 += 1;
          if (skin2 > 3) {
            skin2 = 1;
          }
          System.out.println("Skin Nr:" + skin2);
        }
      });
    } // end of Startmenü
  }

  public static void main(String[] args) {
    sound.spieleLobbySound();
    launch(args);
  }
}

//In unserer Quellen.txt findet man Seiten die uns beim Programmieren unterstützt haben