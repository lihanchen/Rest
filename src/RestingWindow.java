import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RestingWindow extends JDialog implements HotKeyReceiver {

	private static RestingWindow theInstance;
	private JPanel contentPane;
	private JLabel LabelRemainingTime;
	private JButton ButCloseMonitor;
	private JButton ButPause;
	private JButton ButStop;
	private JPanel mainPanel;
	private int secondRemainging;
	private Timer timer, pauseTimer;
	private int pauseCounter = 0;
	private boolean pendingExit = false;

	public RestingWindow(int restTime) {
		this.add(contentPane);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.pack();
		this.setLayout(null);

		ButCloseMonitor.setText(Main.strings.getString("checkCloseMonitor"));
		ButPause.setText(Main.strings.getString("butPause"));
		ButStop.setText(Main.strings.getString("butStop"));

		if (Boolean.parseBoolean(Main.settings.getProperty("fullScreen"))) {//FullScreen
			int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
			this.setSize(screenWidth, screenHeight);
			if (screenWidth>screenHeight*2.5) screenWidth=screenWidth/2;
			this.setLocation(0, 0);
			contentPane.setBounds(new Rectangle((screenWidth - 500) / 2, (screenHeight - 250) / 2, 500, 250));
		} else {
			this.setSize(500, 250);
			this.setLocationRelativeTo(null);
		}
		LabelRemainingTime.setFont(LabelRemainingTime.getFont().deriveFont(180F));
		if (!JNI.screenOff) ButCloseMonitor.setVisible(false);
		this.setVisible(true);
		secondRemainging = restTime;
		LabelRemainingTime.setText(twoDigitStr(restTime / 60) + ":" + twoDigitStr(restTime % 60));
		LabelRemainingTime.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				if (pendingExit) stop(false);
			}

			public void mouseExited(MouseEvent e) {
				if (pendingExit) stop(false);
			}
		});

		timer = new Timer(1000, e -> tick());
		timer.start();

		ButCloseMonitor.addActionListener(e -> JNI.closeMonitor());
		ButStop.addActionListener(e -> stop(false));
		ButPause.addActionListener(e -> pause());

		if (Boolean.parseBoolean(Main.settings.getProperty("closeMonitor")))
			if (JNI.screenOff) JNI.closeMonitor();

		if (JNI.gameMonitor) {
			GameMonitor.getInstance().timer.stop();
		}
	}

	public static RestingWindow getInstance() {
		if (theInstance == null) theInstance = new RestingWindow(Main.timeModel.startRest());
		return theInstance;
	}

	public static String twoDigitStr(int a) {
		return (a > 9 ? Integer.toString(a) : "0" + a);
	}

	private void tick() {
		secondRemainging--;
		LabelRemainingTime.setText(twoDigitStr(secondRemainging / 60) + ":" + twoDigitStr(secondRemainging % 60));
		if (secondRemainging == 0) {
			Main.playSound();
			stop(true);
		}
	}

	private void stop(boolean auto) {
		this.timer.stop();
		if (JNI.screenOff) JNI.openMonitor();
		if (auto) {
			pendingExit = true;
			return;
		}
		int nextInterval = Main.timeModel.stopRest();
		HomePage homePage = HomePage.getInstance();
		homePage.setTime(nextInterval);
		this.dispose();
		theInstance = null;
		if (homePage.getExtendedState() == JFrame.NORMAL)
			homePage.setVisible(true);
		else
			try {
				HomePage.getInstance().tray.add(HomePage.getInstance().trayIcon);
			} catch (Exception ignored) {
			}
		if (JNI.gameMonitor) GameMonitor.getInstance().timer.start();
	}

	public void onReceive(String requestCode) {
		unPause();
	}

	public void pause() {
		pauseCounter++;
		this.timer.stop();
		HotKeyHandler.addOperation(Main.strings.getString("ContinueResting"), this);
		pauseTimer = new Timer(60000, e -> unPause());
		pauseTimer.start();
		this.setVisible(false);
	}

	public void unPause() {
		pauseTimer.stop();
		HotKeyHandler.removeOperation(Main.strings.getString("ContinueResting"));
		this.setVisible(true);
		this.timer.start();
		if (pauseCounter >= 2) this.ButPause.setEnabled(false);
	}
}
