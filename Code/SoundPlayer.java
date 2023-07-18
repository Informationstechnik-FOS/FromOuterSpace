package Code;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//Ansatz zum Sound kommt von ChatGPT

public class SoundPlayer {
  private static final String STARTSCREEN_PATH = "Sounds/theme.mp3"; // DateiPath zum Sound
  private static final String MAINGAME_PATH = "Sounds/backround.mp3";
  private static final String SHOTS_PATH = "Sounds/shoot.mp3";
  private static final String DEATH_PATH = "Sounds/death.mp3";
  private static final String KILL_PATH = "Sounds/Kill.mp3";
  private static final String BOMB_PATH = "Sounds/bombe.mp3";
  private static final String LEBEN_PATH = "Sounds/leben.mp3";
  private static final String SPAWN_PATH = "Sounds/spawn.mp3";

  private Media startscreen_sound;
  private Media maingame_sound;
  private MediaPlayer playerLobby;// MediaPlayer zum abrufen des Sounds
  private MediaPlayer playerMainGame;
  private MediaPlayer shot;
  private MediaPlayer death;
  private MediaPlayer kill;
  private MediaPlayer bomb;
  private MediaPlayer leben;
  private MediaPlayer spawn;

  // Konstruktor
  public SoundPlayer() {
    startscreen_sound = new Media(new File(STARTSCREEN_PATH).toURI().toString());
    playerLobby = new MediaPlayer(startscreen_sound);

    maingame_sound = new Media(new File(MAINGAME_PATH).toURI().toString());
    playerMainGame = new MediaPlayer(maingame_sound);

    death = new MediaPlayer(new Media(new File(DEATH_PATH).toURI().toString()));

  }

  // Methoden
  public void spieleLobbySound() {
    playerLobby.play();
    playerLobby.setCycleCount(-1);
  }

  public void spieleMaingameSound() {
    playerMainGame.play();
  }

  public void spieleShotSound() {
    shot = new MediaPlayer(new Media(new File(SHOTS_PATH).toURI().toString()));
    shot.play();
  }

  public void spieleDeathSound() {
    death.play();
  }

  public void spieleKillSound() {
    kill = new MediaPlayer(new Media(new File(KILL_PATH).toURI().toString()));
    kill.play();
  }

  public void spieleBombSound() {
    bomb = new MediaPlayer(new Media(new File(BOMB_PATH).toURI().toString())); // Dem MediaPlayer wird der Path
                                                                               // hinzugefuegt
    bomb.play(); // Spielt den Sound vom Path ab
  }

  public void spieleLebenSound() {
    leben = new MediaPlayer(new Media(new File(LEBEN_PATH).toURI().toString()));
    leben.play();
  }

  public void spieleSpawn() {
    spawn = new MediaPlayer(new Media(new File(SPAWN_PATH).toURI().toString()));
    spawn.play();
  }
}