package Code;

import java.util.ArrayList;

//import java.awt.event.KeyEvent;
public class Player {
  // Anfang Attribute
  private int breite;
  private int hoehe;
  private int shotCounter;  // Zählt hoch bis shotCounterLimiter damit kein repidfire entsteht
  private int shotCounterLimiter; // Setzt die gschwindigkeit in der gschossen werden kann
  private int shotCounterLimiterLimiter;  // Legt fest wie lange die geschwindigkeit verändert werden kann
  private int leben;
  private double xPos;
  private double yPos;
  private String name;
  // Ende Attribute

  public Player() {
    this.breite = 50;
    this.hoehe = 50;
    this.xPos = 0;
    this.yPos = 0;
    this.shotCounter = 0;
    this.shotCounterLimiter = 125;
    this.shotCounterLimiterLimiter = 0;
    this.leben = 3;
  }

  public Player(int playerBreite, int playerHoehe, int xPos, int yPos) {
    this.breite = playerBreite;
    this.hoehe = playerHoehe;
    this.xPos = xPos;
    this.yPos = yPos;
    this.shotCounter = 0;
    this.shotCounterLimiter = 125;
    this.shotCounterLimiterLimiter = 0;
    this.leben = 3;
  }

  // Methoden
  // ------Spieler bewegen------
  public void move(int bewegen) {

    //Nach Links
    if(bewegen<0 && xPos>0){
      xPos += bewegen;
    } 
    
    //Nach Rechts
    if(bewegen>0 && xPos<800 - breite){
      xPos += bewegen;
    }
  }

  public boolean checkAbegeschossen(ArrayList<Bullet> bullets) {
    for (Bullet bullet : bullets) {
      // xAchse überprüfen
      if (xPos < bullet.getXPos() && (xPos + breite) > bullet.getXPos()) {
        // yAchse überprüfen
        if (yPos < bullet.getYPos() && (yPos + breite) > bullet.getYPos()) {
          return true;
        }
      }
    }
    return false;
  }

  //-----Shot Counter (damit keine minigun entsteht)-----
  public boolean checkShotCounter(){
      

    if(shotCounter>shotCounterLimiter){
      shotCounter = 0; 
      return true; // Wenn geschossen werden darf
    } else {
      return false; //Wenn nicht geschosen werden darf
    }
  }

  public void shotCounterErhöhen(){
    shotCounterLimiterLimiter++;
    shotCounter++;
    if(shotCounterLimiterLimiter>2000){
      shotCounterLimiter = 125; // Nach 2000 ticks wird die Geschwindigkeit verringert
    }
  }

  public void respawn(int xPos, int yPos){
    leben = 3;
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public void addLives(int amount){
    if(leben<3){
      leben += amount;
    } else {
      // TODO: Leben an anderen Spieler verteilen
      if(Main.player1.getLeben()==3){
        if (Main.player2.getLeben() == 0) {
          Main.player2.setXPos(Main.BREITE/2);
        } 
        Main.player2.setLeben(Main.player2.getLeben()+1);
        System.out.println("Spieler 2 leben Erhöht auf " + Main.player2.getLeben());
        
        
      } else if(Main.player2.getLeben()==3){
          if (Main.player1.getLeben() == 0) {
          Main.player1.setXPos(Main.BREITE/2);
        }
        Main.player1.setLeben(Main.player1.getLeben()+1);
        System.out.println("Spieler 1 leben Erhöht auf " + Main.player1.getLeben());
        
      }

    }
    System.out.println("Zusätzliches Leben erhalten!");
  }

  public void increaseFireRate(){
    //TODO: Feuerrate erhöhen
    shotCounterLimiter = 50;
    shotCounterLimiterLimiter = 0;
    System.out.println("Feuerrate erhöht!");
  }

  // ------Gett-/Setter------
  public int getLeben() {
      return leben;
  }

  public void setLeben(int leben) {
      this.leben = leben;
  }
  
  public int getPlayerBreite() {
    return breite;
  }

  public void setPlayerBreite(int breite) {
    this.breite = breite;
  }

  public int getPlayerHoehe() {
    return hoehe;
  }

  public void setPlayerHoehe(int hoehe) {
    this.hoehe = hoehe;
  }

  public double getXPos() {
    return xPos;
  }

  public void setXPos(double xPos) {
    this.xPos = xPos;
  }

  public double getYPos() {
    return yPos;
  }

  public void setYPos(double yPos) {
    this.yPos = yPos;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  // Ende Methoden
} // end of Player
