import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
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
				this.dispose();
				theInstance = null;
				operations.get(s).onReceive(s);
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

		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c >= '1' && c <= '0' + operations.size()) {
					int count = operations.size() - (c - '0');
					Iterator<String> s = operations.keySet().iterator();
					while (count-- > 0)
						s.next();
					String key = s.next();
					HotKeyHandler.this.dispose();
					theInstance = null;
					operations.get(key).onReceive(key);
				} else if (c == '1' + operations.size()) {
					HotKeyHandler.this.dispose();
					theInstance = null;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

	}

	public static void addOperation(String requestCode, HotKeyReceiver receiver) {
		operations.put(requestCode, receiver);
		if (theInstance != null) {
			theInstance.dispose();
			theInstance = null;
			popup();
		}
	}

	public static void removeOperation(String requestCode) {
		operations.remove(requestCode);
		if (theInstance != null) {
			theInstance.dispose();
			theInstance = null;
			popup();
		}
	}

	public static void popup() {
		if (theInstance != null)
			theInstance.toFront();
		else if (operations.size() != 0)
			new HotKeyHandler();
	}

}
