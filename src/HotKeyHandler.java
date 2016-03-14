import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

interface HotKeyReceiver {
	void onReceive(String requestCode);
}

public class HotKeyHandler extends JDialog {
	private static LinkedHashMap<String, HotKeyReceiver> operations = new LinkedHashMap<>();
	private static HotKeyHandler theInstance;
	private JPanel mainPane;

	private HotKeyHandler() {
		this.setContentPane(mainPane);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setSize(200, 160 * (operations.size() + 1));
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.toFront();
		for (String s : operations.keySet()) {
			JButton but = new JButton(s);
			but.addActionListener(e -> {
				operations.get(s).onReceive(s);
				this.dispose();
				theInstance = null;
			});
			but.setPreferredSize(new Dimension(150, 50));
			mainPane.add(but, 0);
		}
		JButton butExit = new JButton(Main.strings.getString("cancel"));
		butExit.addActionListener(e -> {
			this.dispose();
			theInstance = null;
		});
		butExit.setPreferredSize(new Dimension(150, 50));
		mainPane.add(butExit);
		theInstance = this;
		this.setContentPane(mainPane);
		this.setLocationRelativeTo(null);
		this.setSize(200, 20 + 70 * (operations.size() + 1));
		this.setVisible(true);

	}

	public static void addOperation(String requestCode, HotKeyReceiver receiver) {
		operations.put(requestCode, receiver);
	}

	public static void removeOperation(String requestCode) {
		operations.remove(requestCode);
	}

	public static void popup() {
		if (theInstance != null)
			theInstance.toFront();
		else if (operations.size() != 0)
			new HotKeyHandler();
	}

}
