import Languages.Chinese;
import Languages.Internationalization;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Properties;

public class Main {
	static Internationalization strings = null;
	static Properties settings;

	static {
		Locale locale = Locale.getDefault();
		strings = Chinese.getInstance();
		try { //Setting Property
			settings = new Properties();
			settings.load(new FileInputStream(new File("Settings.properties")));
		} catch (Exception e) {//Defaults

		}
	}

	public static void playSound() {
		try {
			AudioStream ring = new AudioStream(Main.class.getResourceAsStream("Sound.wav"));
			AudioPlayer.player.start(ring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String twoDigitStr(int a) {
		return (a > 9 ? Integer.toString(a) : "0" + a);
	}

	public synchronized static boolean saveSettings() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("Settings.properties"));
			settings.store(fos, null);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void hotKeyPressed() {
		HomePage.getInstance();
	}

	public static void main(String args[]) throws InterruptedException {
		JNI.init();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeModel tm = new TimeModel();

	}

}
