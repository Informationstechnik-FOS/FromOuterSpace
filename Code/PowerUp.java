package Code;

import java.util.Random;

class PowerUp {
  private double x; // X-Koordinate des Power-Ups
  private double y; // Y-Koordinate des Power-Ups
  private int breite = 25;
  private int hoehe = 15;
  private int typ; // Art des Power-Ups: 1 für zusätzliche Leben, 2 für erhöhte Feuerrate, 3 für Bombe

  public PowerUp(double d, double e) {
    this.x = d;
    this.y = e;
    this.typ = generateRandomType();
    System.out.println("PowerUp Erstellt, Typ:" + typ);
  }

  private int generateRandomType() {
    Random random = new Random();
    // Hier kannst du weitere Arten von Power-Ups hinzufügen und ihre
    // Wahrscheinlichkeiten anpassen
    int[] types = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,3}; // Beispiel: 50% Kein Powerup, 30% zusätzliche Leben, 20% erhöhte Feuerrate
    int randomIndex = random.nextInt(types.length);
    return types[randomIndex];
  }

  public void applyPowerUp(Player player) {
    switch (typ) {
      case 1:
        player.addLives(1);
        break;
      case 2:
        player.increaseFireRate();
        break;
      case 3:
        Main.ufoBullet.clear();
        Main.ufos.clear();
        Main.ufos.add(new Ufo(Main.BREITE/2, 75.0, 1));
        System.out.println("Kabooommm!!!!!");
        // Logik für ein anderes Power-Up hinzufügen
        break;
      default:
        break;
    }
  }

  public boolean checkAbegeschossen(Player player) {
    // Übergebenen Spieler auf kolision prüfen
    //if (x > player.getXPos() && (x + breite) > (player.getXPos() + player.getPlayerBreite()) ) { // xAchse überprüfen
    //  if (y > player.getYPos() && (y + hoehe) > (player.getYPos() + player.getPlayerHoehe()) ) { // yAchse überprüfen
    //    return true;
    //  }
    //}

    if (player.getXPos() < x + breite &&
      player.getXPos() + player.getPlayerBreite() > x &&
      player.getYPos() < y + hoehe &&
      player.getYPos() + player.getPlayerHoehe() > y) {
      return true;
    }

    return false;
  }

  // Getter und Setter für x, y und type
  public double getXPos() {
    return x;
  }

  public void setXPos(double x) {
    this.x = x;
  }

  public double getYPos() {
    return y;
  }

  public void setYPos(double y) {
    this.y = y;
  }

  public int getType() {
    return typ;
  }

  public void setType(int type) {
    this.typ = type;
  }
}