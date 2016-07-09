import com.melloware.jintellitype.JIntellitype;

import javax.swing.*;

public abstract class JNI {
    static boolean success = false;

    static {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
			if (System.getProperty("os.arch").contains("amd64")) {
				System.loadLibrary("win64");
			} else {
				System.loadLibrary("win32");
			}
			success = true;
			setHotKey();
		}
		if (os.contains("Linux")) {
			try {
				UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

    public native static boolean checkFullScreen();

    public native static void closeMonitor();

    public native static void openMonitor();

	public static void setHotKey() {
		if (!success) return;
		JIntellitype.getInstance().registerHotKey(0, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT, (int) 'R');
		JIntellitype.getInstance().addHotKeyListener(i -> HotKeyHandler.popup());
//		JIntellitype.getInstance().addHotKeyListener(i -> GameMonitor.getInstance().check());
	}

	public static void init() {
	}

}
