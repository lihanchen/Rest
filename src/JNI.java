import com.melloware.jintellitype.JIntellitype;

import javax.swing.*;

public abstract class JNI {
	static boolean monitor = false;
	static boolean gameMonitor = false;

	public static void init() {
		String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (System.getProperty("os.arch").contains("amd64")) {
				System.loadLibrary("win64");
			} else {
				System.loadLibrary("win32");
			}
			monitor = true;
			gameMonitor = true;
			setHotKey();
		}
		if (os.contains("Linux")) {
			try {
				UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (System.getProperty("os.arch").contains("amd64")) {
				System.loadLibrary("linux64");
			} else {
				System.loadLibrary("linux32");
			}
			monitor = true;
		}
	}

    public native static void closeMonitor();

    public native static void openMonitor();

	public static void setHotKey() {
		JIntellitype.getInstance().registerHotKey(0, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT, (int) 'R');
		JIntellitype.getInstance().addHotKeyListener(i -> HotKeyHandler.popup());
	}

}
