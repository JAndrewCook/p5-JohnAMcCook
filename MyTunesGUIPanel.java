import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicButtonListener;

@SuppressWarnings({ "serial", "unused" })
public class MyTunesGUIPanel extends JPanel {
	private PlayList list;
	private String playingTitle;
	private String playingArtist;

	private JList<Song> songList = new JList<Song>();
	
	private JPanel gridPanel;
	private JPanel controlPanel;

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

	private ImageIcon pIcon = new ImageIcon("images/play-32.gif");
	private ImageIcon sIcon = new ImageIcon("images/pause-32.gif");
	
	private JButton[][] grid;
	
	private int index;
	private Timer timer;

	public MyTunesGUIPanel() {
		// creating the timer
		timer = new Timer(0, new TimerListener());
		timer.setRepeats(false);
		// setting the layout
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		File file = new File("playlist.txt");
		list = new PlayList(file);
		// stuff in WEST section of layout
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		this.add(leftPanel, BorderLayout.WEST);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		leftPanel.add(container);
		JPanel moveButtons = new JPanel();
		moveButtons.setLayout(new BoxLayout(moveButtons, BoxLayout.Y_AXIS));
		container.add(moveButtons);
		up = new JButton("");
		down = new JButton("");
		ImageIcon uicon = new ImageIcon("images/move-up-24.gif");
		ImageIcon dicon = new ImageIcon("images/move-down-24.gif");
		up.setIcon(uicon);
		down.setIcon(dicon);
		up.addActionListener(new UpDownListener());
		down.addActionListener(new UpDownListener());
		moveButtons.add(up);
		moveButtons.add(down);
		JScrollPane scrollPane = new JScrollPane(songList, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		songList.setListData(list.getSongArray());
		songList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		container.add(scrollPane);
		JPanel addRemove = new JPanel();
		addRemove.setLayout(new BoxLayout(addRemove, BoxLayout.X_AXIS));
		leftPanel.add(addRemove);
		add = new JButton("Add");
		remove = new JButton("Remove");
		add.addActionListener(new ARListener());
		remove.addActionListener(new ARListener());
		addRemove.add(add);
		addRemove.add(remove);
		// stuff in CENTER section of layout
		gridPanel = new JPanel();
		int sqrt = (int) Math.ceil(Math.sqrt(list.getNumSongs()));
		gridPanel.setLayout(new GridLayout(sqrt, sqrt));
		this.add(gridPanel, BorderLayout.CENTER);
		JLabel numClicks = new JLabel();
		grid = new JButton[(int) sqrt][(int) sqrt];
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				String buttonName = (String) this.list.getSong(((row * sqrt) + col) % list.getNumSongs()).getTitle();
				grid[row][col] = new JButton(buttonName);
				grid[row][col].addActionListener(new SongSquareListener());
				gridPanel.add(grid[row][col]);
			}
		}
		controlPanel = new JPanel();
		this.add(controlPanel, BorderLayout.SOUTH);
		name = new JLabel("None by Noone");
		prev = new JButton("");
		psAction = new JButton("");
		next = new JButton("");
		ImageIcon bicon = new ImageIcon("images/media-skip-backward-32.gif");

		ImageIcon ficon = new ImageIcon("images/media-skip-forward-32.gif");
		prev.setIcon(bicon);
		psAction.setIcon(pIcon);
		next.setIcon(ficon);
		prev.setMnemonic('p');
		psAction.setMnemonic('s');
		next.setMnemonic('n');
		prev.addActionListener(new ButtonListener());
		psAction.addActionListener(new ButtonListener());
		next.addActionListener(new ButtonListener());
		controlPanel.add(name);
		controlPanel.add(prev);
		controlPanel.add(psAction);
		controlPanel.add(next);

	}

	private class PlayListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent event) {
			// TODO Auto-generated method stub
			
		}

	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			index = songList.getSelectedIndex();

			if (event.getSource() == next) {
				if (index + 2 > list.getSongArray().length) {
					index = 0;
					list.stop();
					list.playSong(index);
				} else {
					index++;
					list.stop();
					list.playSong(index);
				}
			} else if (event.getSource() == psAction) {
				if(list.getPlaying() != null){
					list.stop();
					psAction.setIcon(pIcon);
				}else{
					list.stop();
					list.playSong(index);
					psAction.setIcon(sIcon);
				}
			} else {
				if(index - 1 < 0) {
					list.stop();
					index = list.getSongArray().length - 1;
					list.playSong(index);
				}else {
					list.stop();
					index--;
					list.playSong(index);
				}
			}
			songList.setSelectedIndex(index);
			playingTitle = list.getSongArray()[index].getTitle();
			playingArtist = list.getSongArray()[index].getArtist();
			name = new JLabel(playingTitle + " by " + playingArtist);
			controlPanel.remove(name);
			controlPanel.add(name);
		}

	}
	
	private class SongSquareListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			index = songList.getSelectedIndex();
			int sqrt = (int) Math.ceil(Math.sqrt(list.getNumSongs()));
			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid[row].length; col++) {
					if(grid[row][col] == event.getSource()){
						index = list.get(((row*sqrt)+col)%list.getSongArray().length);;
					}
				}
			}
			list.stop();
			songList.setSelectedIndex(index);
			playingTitle = list.getSongArray()[index].getTitle();
			playingArtist = list.getSongArray()[index].getArtist();
			name = new JLabel(playingTitle + " by " + playingArtist);
			
			list.playSong(index);
			psAction.setIcon(sIcon);
		}
		
	}
	
	private class UpDownListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == up){
				int newIndex = list.moveUp(songList.getSelectedIndex());
				songList.setListData(list.getSongArray());
				songList.setSelectedIndex(newIndex);
			}else {
				int newIndex = list.moveDown(songList.getSelectedIndex());
				songList.setListData(list.getSongArray());
				songList.setSelectedIndex(newIndex);
			}
		}
		
	}
	
	private class ARListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == remove){
				int index = songList.getSelectedIndex();
				list.removeSong(index);
				songList.setListData(list.getSongArray());
				if(index > 0){
					songList.setSelectedIndex(index);
				}if(index - 1 < 0){
					songList.setSelectedIndex(index);
				}else{
					songList.setSelectedIndex(index-1);
				}
			}else {
				showForm();
			}
		}
		
	}
	
	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(list.getPlaying() != null){
				list.stop();
				psAction.setIcon(pIcon);
			}else{
				list.stop();
				list.playSong(index);
				psAction.setIcon(sIcon);
			}
		}
		
	}
	
	private void showForm() {
		JPanel addSongInputPanel = new JPanel();
		addSongInputPanel.setLayout(new BoxLayout(addSongInputPanel, BoxLayout.Y_AXIS));
		
		JTextField titleField = new JTextField(20);
		JTextField artistField = new JTextField(20);
		JTextField playTimeField = new JTextField(4);
		JTextField filePathField = new JTextField(40);
		
		addSongInputPanel.add(new JLabel("Song title: "));
		addSongInputPanel.add(titleField);
		addSongInputPanel.add(new JLabel("Song artist: "));
		addSongInputPanel.add(artistField);
		addSongInputPanel.add(new JLabel("Song time(in seconds): "));
		addSongInputPanel.add(playTimeField);
		addSongInputPanel.add(new JLabel("Path to song: "));
		addSongInputPanel.add(filePathField);
		
		int result = JOptionPane.showConfirmDialog(null, addSongInputPanel, "Add User",
    			JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		//String title, String artist, int playTime, String filePath
		
		if (result == JOptionPane.OK_OPTION)
		{
			String title = titleField.getText();
			String artist = artistField.getText();
			int playTime = 0;
			String filePath = filePathField.getText();
			try
			{
				playTime = Integer.parseInt(playTimeField.getText());
				if(playTime <= 0) {
					JOptionPane.showMessageDialog(null, "Song has to be greater than zero(0)");
					
				}
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "The play time must be a number");
			}
			
			Song newSong = new Song(title, artist, playTime, filePath);
			list.addSong(newSong);
			songList.setListData(list.getSongArray());
			resetSongGrid();
		}
	}
	
	private void resetSongGrid(){
		gridPanel.removeAll();
		
		int sqrt = (int) Math.ceil(Math.sqrt(list.getNumSongs()));
		gridPanel.setLayout(new GridLayout(sqrt, sqrt));
		grid = new JButton[(int) sqrt][(int) sqrt];
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				String buttonName = (String) this.list.getSong(((row * sqrt) + col) % list.getNumSongs()).getTitle();
				grid[row][col] = new JButton(buttonName);
				grid[row][col].addActionListener(new SongSquareListener());
				gridPanel.add(grid[row][col]);
			}
		}
	}

}
