import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicButtonListener;

@SuppressWarnings({ "serial", "unused" })
public class MyTunesGUIPanel extends JPanel {
	private PlayList list;

	private JList<Song> songList;

	private JLabel name;
	private JLabel numSongs;
	private JLabel ttlPlayTime;
	private JLabel curSongInfo;

	private JButton up;
	private JButton down;
	private JButton add;
	private JButton remove;
	private JButton psAction;
	private JButton prev;
	private JButton next;

	private JButton[][] songGridButtons;

	private MyTunesGUIPanel(){
		// setting the layout
		setLayout(new BorderLayout());
		// hopefully setting up the panel for the list that'll be on the left
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		// setting up the grid panel that'll be on the right
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(songGridButtons.length, songGridButtons[0].length));
		// create listener
		PlayListListener listener = new PlayListListener();
	}
	
	private class PlayListListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton source = (JButton)(e.getSource());
			
			// do more stuff
			
		}
		
	}

}
