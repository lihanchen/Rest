import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.TimerTask;

public class HomePage extends JFrame implements WindowListener, HotKeyReceiver {
	private static HomePage theInstance = null;
	public SystemTray tray;
	public TrayIcon trayIcon;
	private PopupMenu popMenu;
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
	private JCheckBox checkFullScreen;
	private JCheckBox checkCloseMonitor;
	private JPanel GamePanel;
	private java.util.Timer timer = null;
	private int interval;
	private volatile boolean restAfterWaiting;
	private Thread mainThread;
	private short skipCounter = 0;

	private HomePage() {
		this.setContentPane(this.PaneMain);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(Main.strings.getString("title"));
		this.setResizable(false);
		this.setIconImage(Main.icon.getImage());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTime(Main.timeModel.getInterval());
		this.textPeriod.setText("" + Main.timeModel.getPeriod() / 60);
		this.textInterval.setText("" + Main.timeModel.getInterval() / 60);
		this.checkFullScreen.setSelected(Boolean.parseBoolean(Main.settings.getProperty("fullScreen")));
		this.checkCloseMonitor.setSelected(Boolean.parseBoolean(Main.settings.getProperty("closeMonitor")));

		this.addWindowListener(this);
		ButRemaining.addActionListener(e -> reset());
		ButRestNow.addActionListener(e -> rest(false));
		ButSet.addActionListener(e -> {
			try {
				int newInterval = Integer.parseInt(textInterval.getText());
				int newPeriod = Integer.parseInt(textPeriod.getText());
				int OldInterval = Integer.parseInt((String) Main.settings.get("interval"));
				Main.settings.put("interval", textInterval.getText());
				Main.settings.put("period", textPeriod.getText());
				this.interval += 60 * (newInterval - OldInterval);
				Main.timeModel.change(newInterval, newPeriod);
				if (interval <= 0) rest(true);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, Main.strings.getString("errorNumberFormat"), Main.strings.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		});
		checkCloseMonitor.addActionListener(e -> Main.settings.put("closeMonitor", "" + checkCloseMonitor.isSelected()));
		checkFullScreen.addActionListener(e -> Main.settings.put("fullScreen", "" + checkFullScreen.isSelected()));


		//мпел
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			popMenu = new PopupMenu();
			MenuItem itemRestNow = new MenuItem(Main.strings.getString("RestNow"));
			itemRestNow.addActionListener(e -> rest(false));
			MenuItem itemClear = new MenuItem(Main.strings.getString("ResetTime"));
			itemClear.addActionListener(e -> reset());
			MenuItem itemExit = new MenuItem(Main.strings.getString("exit"));
			itemExit.addActionListener(e -> System.exit(0));
			popMenu.add(itemRestNow);
			popMenu.add(itemClear);
			popMenu.add(itemExit);
			trayIcon = new TrayIcon(Main.icon.getImage(), Main.strings.getString("signature"), popMenu);
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

	public static String twoDigitStr(int a) {
		return (a > 9 ? Integer.toString(a) : "0" + a);
	}

	public static HomePage getInstance() {
		if (theInstance == null) theInstance = new HomePage();
		return theInstance;
	}

	public void reset() {
		if (JOptionPane.showConfirmDialog(this, Main.strings.getString("reset?"), Main.strings.getString("ResetTime"), JOptionPane.WARNING_MESSAGE)
				== JOptionPane.YES_OPTION) {
			setTime(Main.timeModel.getInterval());
		}
	}

	public void setTime(int interval) {
		this.interval = interval + 1;
		if (timer != null) timer.cancel();
		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new TimerTask() { //lambda doesn't work
			public void run() {
				HomePage.this.interval--;
				ButRemaining.setText(twoDigitStr(HomePage.this.interval / 60) + ":" + twoDigitStr(HomePage.this.interval % 60));
				if (HomePage.this.interval == 0) {
					HomePage.this.timer.cancel();
					rest(true);
				}
			}
		}, 0, 1000);
		HotKeyHandler.addOperation(Main.strings.getString("showWindow"), this);
		HotKeyHandler.addOperation(Main.strings.getString("RestNow"), this);
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

	private void rest(boolean automatic) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		HotKeyHandler.removeOperation(Main.strings.getString("showWindow"));
		HotKeyHandler.removeOperation(Main.strings.getString("RestNow"));
		this.setVisible(false);
		if (automatic) {
			if (skipCounter <= 2) HotKeyHandler.addOperation(Main.strings.getString("skipRest"), this);
			HotKeyHandler.addOperation(Main.strings.getString("startRest"), this);
			mainThread = Thread.currentThread();
			try {
				Main.playSound();
				Thread.sleep(15000);
				Main.playSound();
				Thread.sleep(15000);
				HotKeyHandler.removeOperation(Main.strings.getString("startRest"));
				HotKeyHandler.removeOperation(Main.strings.getString("skipRest"));
			} catch (InterruptedException e) {
				if (!restAfterWaiting) {
					skipCounter++;
					this.setTime(Main.timeModel.keepPlaying());
					return;
				}
			}
		}
		RestingWindow.getInstance();
	}

	public void onReceive(String requestCode) {
		if (requestCode.equals(Main.strings.getString("showWindow"))) {
			tray.remove(trayIcon);
			this.setVisible(true);
			this.setExtendedState(JFrame.NORMAL);
			this.toFront();
		}
		if (requestCode.equals(Main.strings.getString("startRest"))) {
			HotKeyHandler.removeOperation(Main.strings.getString("startRest"));
			HotKeyHandler.removeOperation(Main.strings.getString("skipRest"));
			restAfterWaiting = true;
			mainThread.interrupt();
		}
		if (requestCode.equals(Main.strings.getString("skipRest"))) {
			HotKeyHandler.removeOperation(Main.strings.getString("startRest"));
			HotKeyHandler.removeOperation(Main.strings.getString("skipRest"));
			restAfterWaiting = false;
			mainThread.interrupt();
		}
		if (requestCode.equals(Main.strings.getString("RestNow"))) {
			rest(false);
		}
	}
}