import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main {
    static ResourceBundle strings;
    static Properties settings;

    static {
        try { //Internationalization
            strings = ResourceBundle.getBundle("Languages", Locale.SIMPLIFIED_CHINESE);//TODO change to default
        } catch (MissingResourceException e) {
            strings = ResourceBundle.getBundle("Languages", Locale.SIMPLIFIED_CHINESE);
        }
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

    public static boolean saveSettings() {
        try {
            FileOutputStream fos = new FileOutputStream(new File("Settings.properties"));
            settings.store(fos, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String args[]) {

    }
}
