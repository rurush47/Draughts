import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Class that contains maion menu panel with buttons.
 */
public class Menu {
	private JPanel menu;
	private JButton playButton;
	private JButton quitButton;
	private JButton hostButton;
	private JButton joinButton;
	
	/*
	 * Menu constructor
	 */
	Menu(JPanel cards)
	{
		menu = new JPanel();
		playButton = new JButton("Play local game");
		quitButton = new JButton("Quit");
		hostButton = new JButton("Host game");
		joinButton = new JButton("Join game");
		System.out.println("buttons initialized");
		
		menu.add(playButton);
		menu.add(hostButton);
		menu.add(joinButton);
		menu.add(quitButton);
	}
	
	/*
	 * Returns menu object.
	 */
	public JPanel getMenu()
	{
		return menu;
	}
	
	/*
	 * Returns quit button.
	 */
	public JButton getQuit()
	{
		return quitButton;
	}
	
	/*
	 * Returns play button.
	 */
	public JButton getPlayButton()
	{
		return playButton;
	}
	
	/*
	 * Returns host button.
	 */
	public JButton getHostButton()
	{
		return hostButton;
	}
	
	/*
	 * Returns join button.
	 */
	public JButton getJoinButton()
	{
		return joinButton;
	}
}
