package Code;

public class Bullet {

  // Anfang Attribute
  private int hoehe = 10;
  private int breite = 5;
  private int anzahl = 0;
  private double xPos;
  private double yPos;
  private double speed;
  private boolean getroffen;
  private boolean abgeschossen;
  private boolean vonSpieler;
  // Ende Attribute

  public Bullet() {
    this.hoehe = 0;
    this.breite = 0;
    this.xPos = 0;
    this.yPos = 0;
    this.getroffen = false;
    this.abgeschossen = false;
  }

  public Bullet(double xPos, double yPos, double speed, boolean vonSpieler) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.speed = speed;
    this.getroffen = false;
    this.abgeschossen = false;
    this.vonSpieler = vonSpieler;
    anzahl++;
  }

  // Anfang Methoden

  public boolean move() {
    yPos -= speed;
    if (yPos <= 0) {
      return true;
    } // end of if
    return false;
  }

  public boolean getVonSpieler() {
    return vonSpieler;
  }

  public int getAnzahl() {
    return anzahl;
  }

  public void forward() {
    yPos = +5;
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

  public boolean getGetroffen() {
    return getroffen;
  }

  public void setGetroffen(boolean getroffen) {
    this.getroffen = getroffen;
  }

  public boolean getAbgeschossen() {
    return abgeschossen;
  }

  public void setAbgeschossen(boolean abgeschossen) {
    this.abgeschossen = abgeschossen;
  }

  // Ende Methoden
} // end of Bullet
