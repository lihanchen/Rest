import Languages.Chinese;
import Languages.Internationalization;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Properties;

public class Main {
	static Internationalization strings = null;
	static Properties settings;
	static TimeModel timeModel;
	static ImageIcon icon;

	static {
		Locale locale = Locale.getDefault();
		strings = Chinese.getInstance();
		try { //Setting Property
			settings = new Properties();
			settings.load(new FileInputStream(new File("Settings.properties")));
		} catch (Exception e) {//Defaults
			settings.setProperty("interval", "60");
			settings.setProperty("period", "10");
			settings.setProperty("GameToday", " ");
			settings.setProperty("Games", "Dota2:dota.exe");
		}
	}

	public static void playSound() {
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("Sound.wav"));
			AudioFormat format = in.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			SourceDataLine auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
			auline.start();
			byte[] buffer = new byte[512];
			int len;
			while ((len = in.read(buffer)) > 0)
				auline.write(buffer, 0, len);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void main(String args[]) throws InterruptedException {
		JNI.init();
		icon = new ImageIcon(Main.class.getResource("icon.png"));
		timeModel = new TimeModel(Integer.parseInt(settings.getProperty("interval")) * 60, Integer.parseInt(settings.getProperty("period")) * 60);
		HomePage.getInstance();
		GameMonitor.getInstance();
	}

}
