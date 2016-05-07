import java.awt.Button;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Menu {
	private JPanel menu;
	private Button playButton;
	private Button quitButton;
	
	Menu(JPanel cards)
	{
		menu = new JPanel();
		playButton = new Button("Play");
		quitButton = new Button("Quit");
		
		playButton.addActionListener(new ActionListener() 
		{
			  public void actionPerformed(ActionEvent evt) 
			  {
			    CardLayout cl = (CardLayout)(cards.getLayout());
			    cl.next(cards);
			  }
	    });
		
		quitButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			  {
				System.exit(0);
			  }
	    });
		
		menu.add(playButton);
		menu.add(quitButton);
	}
	
	public JPanel getMenu()
	{
		return menu;
	}
}
