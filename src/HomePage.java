import javax.swing.*;

public class HomePage extends JFrame {
	private static HomePage theInstance = null;
	private JPanel MainPane;
	private JTabbedPane Tabs;
	private JPanel HomePane;
	private JPanel HistoryPane;
	private JPanel GamePane;
	private JPanel GamePanel;

	private HomePage() {
		this.setContentPane(this.MainPane);
		this.setDefaultCloseOperation(0);
		this.pack();
		this.setAlwaysOnTop(true);
		this.setSize(250, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle(Main.strings.getString("title"));
		this.setResizable(false);
	}

	public static HomePage getInstance() {
		if (theInstance == null) {
			theInstance = new HomePage();
		}

		return theInstance;
	}
}