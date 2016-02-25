
public abstract class JNI {
    static boolean success = false;

    static {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            if (System.getProperty("os.arch").contains("amd64"))
                System.loadLibrary("win64");
            else
                System.loadLibrary("win32");
            success = true;
        }
    }

    public native static boolean checkFullScreen();

    public native static void closeMonitor();

    public native static void openMonitor();
}
