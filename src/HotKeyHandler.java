import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

interface HotKeyReceiver {
	void onReceive(int requestCode);
}

public class HotKeyHandler extends JDialog {
	private static TreeMap<Integer, HotKeyReceiver> operations = new TreeMap<Integer, HotKeyReceiver>();
	private static HotKeyHandler theInstance;
	private JPanel mainPane;

	private HotKeyHandler() {
		this.setContentPane(mainPane);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setSize(200, 160 * (operations.size() + 1));
		this.setVisible(true);
		for (Integer i : operations.keySet()) {
			JButton but = new JButton(Main.strings.getString("cancel"));
			but.addActionListener(e -> operations.get(i).onReceive(i));
			but.setPreferredSize(new Dimension(150, 50));
			mainPane.add(but);
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
		this.setSize(200, 75 * (operations.size() + 1));
		this.setVisible(true);

	}

	public static void addOperation(int requestCode, HotKeyReceiver receiver) {
		operations.put(requestCode, receiver);
	}

	public static void removeOperation(int requestCode) {
		operations.remove(requestCode);
	}

	public static void popup() {
		if (theInstance != null)
			theInstance.toFront();
		else
			new HotKeyHandler();
	}

}
