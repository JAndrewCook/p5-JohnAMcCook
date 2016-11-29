/**
 * @author john.cook
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayList implements MyTunesPlayListInterface {
	private String name;
	private Song playing;
	private ArrayList<Song> songList = new ArrayList<Song>();

	public PlayList(String name) {
		this.name = name;
		this.playing = null;
		this.songList = new ArrayList<Song>();
	}

	public String getName() {
		return name;
	}

	public Song getPlaying() {
		return playing;
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addSong(Song song) {
		songList.add(song);
	}

	public Song removeSong(int index) {
		if (index >= 0 && index < songList.size()) {
			return songList.remove(index);
		} else {
			return null;
		}
	}

	public int getNumSongs() {
		return songList.size();
	}

	public int getTotalPlayTime() {
		int ttlPlayTime = 0;
		for (Song song : songList) {
			ttlPlayTime += song.getPlayTime();
		}
		return ttlPlayTime;
	}

	public Song getSong(int index) {
		if (index >= 0 && index < songList.size()) {
			return songList.get(index);
		} else {
			return null;
		}
	}

	public void playSong(int index) {
		if (index >= 0 && index < songList.size()) {
			this.playing = songList.get(index);
			this.playing.play();
			System.out.println(songList.get(index));
		} else {
			System.out.println("do nothing");
		}
	}

	public String getInfo() {
		if (songList.size() == 0) {
			return "There are no songs.";
		} else {
			double avPlayTime = 0.00;
			int shortest = songList.get(0).getPlayTime();
			Song shrtstSong = songList.get(0);
			int longest = songList.get(0).getPlayTime();
			Song lngstSong = songList.get(0);
			for (int i = 0; i < songList.size(); i++) {
				avPlayTime += songList.get(i).getPlayTime();
				if (songList.get(i).getPlayTime() < shortest) {
					shortest = songList.get(i).getPlayTime();
					shrtstSong = songList.get(i);
				} else if (songList.get(i).getPlayTime() > longest) {
					longest = songList.get(i).getPlayTime();
					lngstSong = songList.get(i);
				}
			}
			avPlayTime = avPlayTime / songList.size();
			DecimalFormat formatter = new DecimalFormat("#.00");
			return "The average play time is: " + formatter.format(avPlayTime) + " seconds \nThe shortest song is: "
					+ shrtstSong + "\nThe longest song is: " + lngstSong + "\nTotal play time: " + getTotalPlayTime();
		}
	}

	public String toString() {
		System.out.println("------------------");
		System.out.println("Test List (" + songList.size() + ") songs");
		System.out.println("--------------------");
		if (songList.size() == 0) {
			return "------------------\nTest List (" + songList.size()
					+ " songs)\n------------------\nThere are no songs.\n------------------";
		} else {
			String sList = "";
			for (int i = 0; i < songList.size(); i++) {
				sList += "\n(" + i + ")" + getSong(i);
			}
			return "------------------\nTest List (" + songList.size() + " songs)\n------------------" + sList
					+ "\n------------------";
		}
	}
	
	/**
	 * constructor to add the songs
	 */
	public void addPlayList(File file) {
		//PlayList list = null;
		try {
			Scanner scan = new Scanner(file);
			String playListName = scan.nextLine().trim();
			//list = new PlayList(playListName);
			while (scan.hasNextLine()) {
				String title = scan.nextLine().trim();
				String artist = scan.nextLine().trim();
				String playtime = scan.nextLine().trim();
				String songPath = scan.nextLine().trim();

				int colon = playtime.indexOf(':');
				int minutes = Integer.parseInt(playtime.substring(0, colon));
				int seconds = Integer.parseInt(playtime.substring(colon + 1));
				int playtimesecs = (minutes * 60) + seconds;

				Song song = new Song(title, artist, playtimesecs, songPath);
				this.addSong(song);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.err.println("Failed to load playlist. " + e.getMessage());
		}
	}


	@Override
	public void playSong(Song song) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public Song[] getSongArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int moveUp(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveDown(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Song[][] getMusicSquare() {
		// TODO Auto-generated method stub
		return null;
	}

}
