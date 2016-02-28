import javax.swing.*;

public class HomePage extends JFrame {
	private static HomePage theInstance = null;
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
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle(Main.strings.getString("title"));
		this.setAlwaysOnTop(false);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);


	}

	public static HomePage getInstance() {
		if (theInstance == null) {
			theInstance = new HomePage();
		}

		return theInstance;
	}

}