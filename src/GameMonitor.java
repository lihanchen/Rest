import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;

public class GameMonitor {
	final public static int MonitorInterval = 5;
	private static GameMonitor ourInstance;

	static {
		ourInstance = JNI.success ? new GameMonitor() : null;
	}

	HashMap<String, String> gameNames;

	private GameMonitor() {
		Boolean newDay = false;
		Calendar today = Calendar.getInstance();
		String todayStr = "" + today.get(Calendar.MONTH) + "-" + today.get(Calendar.DATE);
		if (!Main.settings.getProperty("GameToday").equals(todayStr)) {
			newDay = true;
			Main.settings.setProperty("GameToday", todayStr);
		}
		gameNames = new HashMap<>();
		String[] games = Main.settings.getProperty("Games").split(",");
		for (String game : games) {
			String name = game.substring(0, game.indexOf(':'));
			String ps = game.substring(game.indexOf(':') + 1);
			gameNames.put(ps, name);
			if (newDay) Main.settings.remove("GAME" + ps);
		}
		new javax.swing.Timer(5 * 60000, e -> check());
	}

	public static GameMonitor getInstance() {
		return ourInstance;
	}

	public HashMap<String, String> getGameNames() {
		return gameNames;
	}

	public void check() {
		String psName;
		try {
			Process taskList = Runtime.getRuntime().exec("tasklist /nh /fo csv");
			BufferedReader tlout = new BufferedReader(new InputStreamReader(taskList.getInputStream()));
			while ((psName = tlout.readLine()) != null) {
				psName = psName.substring(1, psName.indexOf(',') - 1).toLowerCase();
				if (gameNames.containsKey(psName)) {
					System.out.print("found " + psName);
					String stri = Main.settings.getProperty("GAME" + psName);
					int i;
					if (stri == null)
						i = MonitorInterval;
					else
						i = Integer.parseInt(stri) + MonitorInterval;
					System.out.println(i);
					Main.settings.setProperty("GAME" + psName, Integer.toString(i));
				}
			}
			tlout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
