package Code;
import java.util.ArrayList;

public class Boss extends Ufo{
  private int anzahlLeben;
  
  public Boss(){
    super(0,75.0,1);
    anzahlLeben = 3;
    hoehe = 55;
    breite = 100;
    istBoss = true;
    }
  
  public boolean checkAbegeschossen(ArrayList<Bullet> bullets){
    for (Bullet bullet : bullets) {
      //xAchse ueberpruefen
      if(xPos < bullet.getXPos() && (xPos + breite) > bullet.getXPos()){
        //xAchse ueberpruefen
        if(yPos < bullet.getYPos() && (yPos + breite) > bullet.getYPos()){
          //Wen kugel von Spieler
          if(bullet.getVonSpieler()){
            
            Main.bullets.remove(bullet);
            System.out.println("Treffer!"); 
            System.out.println("Leben Boss: " + anzahlLeben);
            anzahlLeben = anzahlLeben-1;
            if (anzahlLeben == 0) {
              return true;
            } // end of if
          }
        }
      }
    }
    return false;
  }
}