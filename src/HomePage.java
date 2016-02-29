import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class HomePage extends JFrame implements WindowListener {
	private static HomePage theInstance = null;
	private SystemTray tray;
	private PopupMenu popMenu;
	private TrayIcon trayIcon;
	private JPanel PaneMain;
	private JTabbedPane Tabs;
	private JPanel PaneHome;
	private JPanel PaneHistory;
	private JPanel PaneGame;
	private JLabel LabelRemaining;
	private JButton ButRemaining;
	private JLabel LabelSetting1;
	private JTextField textInterval;
	private JLabel LabelSetting2;
	private JTextField textPeriod;
	private JLabel LabelSetting3;
	private JButton ButSet;
	private JButton ButRestNow;
	private JLabel LabelSignature;
	private JTable TableHistory;
	private JTable TableGame;
	private JPanel GamePanel;

	private HomePage() {
		this.setContentPane(this.PaneMain);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(Main.strings.getString("title"));
		this.setAlwaysOnTop(false);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(Main.icon.getImage());
		this.pack();
		this.setVisible(true);
		this.addWindowListener(this);

		//мпел
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			popMenu = new PopupMenu();
			MenuItem itemRestNow = new MenuItem(Main.strings.getString("RestNow"));
//			itemRestNow.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					MainWindow.windowMain.rest(false);
//				}
//			});
			MenuItem itemClear = new MenuItem(Main.strings.getString("ResetTime"));
//			itemClear.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					MainWindow.minutePassed = 0;
//					MainWindow.buttonTimePassed.setText("00:00");
//				}
//			});
			MenuItem itemExit = new MenuItem(Main.strings.getString("exit"));
			itemExit.addActionListener(e -> System.exit(0));
			popMenu.add(itemRestNow);
			popMenu.add(itemClear);
			popMenu.add(itemExit);
			trayIcon = new TrayIcon(Main.icon.getImage(), Main.strings.getString("signature"));
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {
				}

				public void mousePressed(MouseEvent arg0) {
				}

				public void mouseExited(MouseEvent arg0) {
				}

				public void mouseEntered(MouseEvent arg0) {
				}

				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getButton() == 1) {
						tray.remove(trayIcon);
						HomePage.this.setVisible(true);
						HomePage.this.setExtendedState(JFrame.NORMAL);
						HomePage.this.toFront();
					}
				}
			});
		}
	}

	public static HomePage getInstance() {
		if (theInstance == null) theInstance = new HomePage();
		return theInstance;
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		Toolkit.getDefaultToolkit().beep();
		int answer = JOptionPane.showConfirmDialog(this, Main.strings.getString("want exit?"), Main.strings.getString("exit"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
			Main.saveSettings();
			System.exit(0);
		}
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
		try {
			tray.add(trayIcon);
			trayIcon.setToolTip(Main.strings.getString("signature") + "\n" + "/");//TODO
			HomePage.this.setVisible(false);
		} catch (AWTException e1) {
		}
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}