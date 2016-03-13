import javax.swing.*;
import java.awt.*;

public class RestingWindow extends JDialog {
	private static RestingWindow theInstance;
	private JPanel contentPane;
	private JLabel LabelRemainingTime;
	private JButton ButCloseMonitor;
	private JButton ButPause;
	private JButton ButStop;
	private JPanel mainPanel;
	private int secondRemainging;

	public RestingWindow(int restTime) {
		this.add(contentPane);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.pack();
		this.setLayout(null);
		//FullScreen
		if (Boolean.parseBoolean(Main.settings.getProperty("fullScreen"))) {
			int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
			int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
			this.setSize(screenWidth, screenHeight);
			this.setLocation(0, 0);
			contentPane.setBounds(new Rectangle((screenWidth - 500) / 2, (screenHeight - 250) / 2, 500, 250));
		} else {
			this.setSize(500, 250);
			this.setLocationRelativeTo(null);
		}
		LabelRemainingTime.setFont(LabelRemainingTime.getFont().deriveFont(180F));
		if (!JNI.success) ButCloseMonitor.setVisible(false);
		this.setVisible(true);
		this.secondRemainging = restTime;
	}

	public static RestingWindow getInstance() {
		if (theInstance == null) theInstance = new RestingWindow(10);
		return theInstance;
	}

}
