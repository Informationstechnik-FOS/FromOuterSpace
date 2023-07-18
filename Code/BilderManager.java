package Code;

import javafx.scene.image.Image;
import java.util.Random;

//Ansatz zum einfuegen von Bildern kommt von StackOverflow

public class BilderManager{
  private Image picSpieler1;
  private Image picSpieler2;
  private Image picSpieler3;
  private Image picSpieler4;
  private Image picSpieler5;
  private Image picSpieler6;
  private Image gegner1;
  private Image gegner2;
  private Image gegner3;
  private Image gegner4;
  private Image gegner5;
  private Image wallpaper;
  private Image wallaperend;
  private Image gameOver;
  private Image playerBullet;
  private Image ufoBullet;
  private Image explo1, explo2, explo3, explo4;
  private Image start;
  private Image links,rechts;
  private Image ueberschrift;
  private Image fireBoost;
  private Image leben;
  private Image bombe;
  private Image boss;
  
  
  public BilderManager(){
    picSpieler1 = new Image("/Images/raumschiff1.png");  //Gerade Zahl = Player2 | ungerade Zahl = Player1
    picSpieler2 = new Image("/Images/raumschiff2.png");     //Path zum Bild wird einem Image zugeordnet sodass man mit Gettern zugreifen kann
    picSpieler3 = new Image("/Images/raumschiff3.png");
    picSpieler4 = new Image("/Images/raumschiff4.png");
    picSpieler5 = new Image("/Images/raumschiff5.png");
    picSpieler6 = new Image("/Images/raumschiff6.png");
    gegner1 = new Image("/Images/gegner1.png");
    gegner2 = new Image("/Images/gegner2.png");
    gegner3 = new Image("/Images/gegner3.png");
    gegner4 = new Image("/Images/gegner4.png");
    gegner5 = new Image("/Images/gegner5.png");
    wallpaper = new Image("/Images/wallpaper.png"); 
    wallaperend = new Image("/Images/wallpaperEnd.jpg");
    gameOver = new Image("/Images/GameOver1.png"); 
    playerBullet = new Image("/Images/shoot.png"); 
    ufoBullet = new Image("/Images/ufoshoot.png");
    explo1 = new Image("/Images/Explo/1.png");
    explo2 = new Image("/Images/Explo/2.png");
    explo3 = new Image("/Images/Explo/3.png");
    explo4 = new Image("/Images/Explo/4.png");   
    start = new Image("/Images/startwallpaper.jpg");    
    links = new Image("/Images/skinLinks.png");
    rechts = new Image("Images/skinRechts.png");
    ueberschrift = new Image("/Images/ueberschrift.png");
    fireBoost = new Image("/Images/FireBoost.png");
    leben = new Image("/Images/Leben.png");
    bombe = new Image("/Images/Bomb.png");
    boss = new Image("/Images/Boss.png");
  }
  
  public Image getBildSpieler1(int zaehler){
    if (zaehler == 1) {
      return picSpieler3;
    } else if (zaehler == 2){
      return picSpieler1;
      } else if (zaehler == 3) {
      return picSpieler5;
    }
     return picSpieler3;
    }   
       // end of if-else
    
  

  public Image getBildSpieler2(int zaehler){
    if (zaehler == 1) {
      return picSpieler4;
    } else if (zaehler == 2){
      return picSpieler2;
      } else if (zaehler == 3) {
      return picSpieler6;
    }
     return picSpieler2;
  }

  public Image getBildGegner(){
    Random random = new Random();
    int randomNumber = random.nextInt(6);
    
    if (randomNumber ==1) {
      return gegner1;
    } 
    if (randomNumber == 2) {
      return gegner2;
    } 
    if (randomNumber == 3) {
      return gegner3;
    } 
    if (randomNumber == 4) {
      return gegner4;
    } 
    if (randomNumber == 5) {
      return gegner5;
    } 
    return gegner1;
  }
  public Image getBoss() {
      return boss;
  }
  public Image getBombe() {
      return bombe;
  }
  public Image getFireBoost() {
      return fireBoost;
  }
  public Image getLeben() {
      return leben;
  }
  public Image getWallpaper(){
    return wallpaper;
    }
  public Image getWallpaperEnd(){
    return wallaperend;
    }
  public Image getGameOver(){
    return gameOver;
    }
  public Image getPlayerShoot(){
    return playerBullet;
    }
  public Image getUfoShoot(){
    return ufoBullet;
    }
  public Image getGetroffen1(){
    return explo1;
    } 
  public Image getGetroffen2(){
    return explo2;
    } 
  public Image getGetroffen3(){
    return explo3;
    } 
  public Image getGetroffen4(){
    return explo4;
    } 
  public Image getStart(){
    return start;
    }
  public Image getLinks(){
    return links;
    }
  public Image getRechts(){
    return rechts;
  }
  public Image getUeberschrift(){
    return ueberschrift;
    }
}
    