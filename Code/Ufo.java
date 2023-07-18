package Code;

import java.util.ArrayList;

public class Ufo {
  
  // Anfang Attribute
  protected int hoehe;
  protected int breite;
  protected double xPos;
  protected double yPos;
  protected static int anzahlUfos;
  protected boolean abgeschossen = false;
  protected int bewegen;
  protected int shotWait = 0;
  protected static int shotLimiter = 200;
  protected boolean istBoss;
  // Ende Attribute
  
  public Ufo() {
    this.hoehe = 50;
    this.breite = 50;
    this.xPos = 0;
    this.yPos = 0;
    this.istBoss = false;
    Ufo.anzahlUfos = anzahlUfos++;
  }

  public Ufo(double xPos, double yPos, int bewegen) {
    this.hoehe = 50;
    this.breite = 50;
    this.xPos = xPos;
    this.yPos = yPos;
    Ufo.anzahlUfos = anzahlUfos++;
    this.bewegen = bewegen;
    this.istBoss = false;
  }

  // Anfang Methoden
  public boolean shot(){
    if(shotWait<shotLimiter){
      shotWait++;
      return false;
    } else{
      shotWait = 0;
      return true;
    }
  }

  public void move(){
    if (xPos >= Main.BREITE ) {
      xPos = 0 - breite;
      yPos += 75;
    } else if(xPos <= 0 - breite) {
      xPos = Main.BREITE;
      yPos += 75;
    } 
    xPos += bewegen;
  }

  //Kolision mit kugel überprüfen
  public boolean checkAbegeschossen(ArrayList<Bullet> bullets){
    for (Bullet bullet : bullets) {
      //xAchse überprüfen
      if(xPos < bullet.getXPos() && (xPos + breite) > bullet.getXPos()){
        //xAchse überprüfen
        if(yPos < bullet.getYPos() && (yPos + breite) > bullet.getYPos()){
          //Wen kugel von Spieler
          if(bullet.getVonSpieler()){
            //Ufo wird abgeschossen
            Main.bullets.remove(bullet);
            System.out.println("Abgeschossen");
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public boolean getAbgeschossen() {
    return abgeschossen;
  }
  
  public void setAbgeschossen(boolean abgeschossen){
    this.abgeschossen = abgeschossen;
  }

  public int getHoehe() {
    return hoehe;
  }

  public void setHoehe(int hoehe) {
    this.hoehe = hoehe;
  }

  public int getBreite() {
    return breite;
  }

  public void setBreite(int breite) {
    this.breite = breite;
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

  public int getAnzahlUfos() {
    return anzahlUfos;
  }

  public void setAnzahlUfos(int anzahlUfos) {
    Ufo.anzahlUfos = anzahlUfos;
  }
  
  public boolean getBoss(){
    return istBoss;
    }

  // Ende Methoden
} // end of Ufo
