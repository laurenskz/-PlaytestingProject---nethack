import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {

	String file;
	
	public MusicPlayer(){
		file = "sounds/music.wav";
	}
	
	public void start(){
		try{
			URL url = this.getClass().getClassLoader().getResource(file);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();

		} catch(Exception e){

		}
	}
}