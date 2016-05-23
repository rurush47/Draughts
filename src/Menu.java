import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu {
	private JPanel menu;
	private JButton playButton;
	private JButton quitButton;
	private JButton hostButton;
	private JButton joinButton;
	
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
	
	public JPanel getMenu()
	{
		return menu;
	}
	
	public JButton getQuit()
	{
		return quitButton;
	}
	
	public JButton getPlayButton()
	{
		return playButton;
	}
	
	public JButton getHostButton()
	{
		return hostButton;
	}
	
	public JButton getJoinButton()
	{
		return joinButton;
	}
}
