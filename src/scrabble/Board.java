package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Board {
	
	public enum WordDirection {
		DOWN,
		RIGHT
	}
	
	private Map<Character, Integer> letterPointVals = new HashMap<Character, Integer>();
	private int width;
	private int height;
	private Cell[][] tiles;
	private ArrayList<String> validWords = new ArrayList<String>();
	
	public Board(int width, int height)
	{
		this.width = width;
		this.height = height;
		tiles = new Cell[width][height];
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				tiles[x][y] = new Cell(1, x, y, false);
			}
		}
		
		letterPointVals.put('a', 1);
		letterPointVals.put('b', 5);
		letterPointVals.put('c', 4);
		letterPointVals.put('d', 3);
		letterPointVals.put('e', 1);
		letterPointVals.put('f', 5);
		letterPointVals.put('g', 4);
		letterPointVals.put('h', 4);
		letterPointVals.put('i', 1);
		letterPointVals.put('j', 9);
		letterPointVals.put('k', 6);
		letterPointVals.put('l', 2);
		letterPointVals.put('m', 4);
		letterPointVals.put('n', 2);
		letterPointVals.put('o', 1);
		letterPointVals.put('p', 4);
		letterPointVals.put('q', 10);
		letterPointVals.put('r', 2);
		letterPointVals.put('s', 1);
		letterPointVals.put('t', 1);
		letterPointVals.put('u', 2);
		letterPointVals.put('v', 5);
		letterPointVals.put('w', 5);
		letterPointVals.put('x', 8);
		letterPointVals.put('y', 5);
		letterPointVals.put('z', 10);
		
		Scanner s = null;
		try {
			s = new Scanner(new File("res/dictionaries/SCOWL-70.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (s.hasNext()){
			validWords.add(s.next().toLowerCase());
		}
		s.close();
	}
	
	public boolean placeWord(String word, int x, int y, WordDirection dir)//Returns true if valid move, false if not
	{
		word = word.toLowerCase();
		switch(dir) {
		case RIGHT:
			if(((x + word.length() - 1) < width) && (y < height)) {
				if(isWord(word)) {
					for(int z = 0; z < word.length(); z++) {
						tiles[x + z][y].setLetter(word.substring(z, z + 1).charAt(0));
					}
					return true;
				}
				else {
					System.out.println("Invalid word selection: " + word +  " is not an English word");
					return false;
				}
			}
			else {
				System.out.println("Invalid word placement: Entire word must be on board");
				return false;
			}
		case DOWN:
			if((x < width) && ((y + word.length() - 1) < height)) {
				if(isWord(word)) {
					for(int z = 0; z < word.length(); z++) {
						tiles[x][y + z].setLetter(word.substring(z, z + 1).charAt(0));
					}
					return true;
				}
				else {
					System.out.println("Invalid word selection: " + word +  " is not an English word");
					return false;
				}
			}
			else {
				System.out.println("Invalid word placement: Entire word must be on board");
				return false;
			}
		}
		return false;
	}
	
	public boolean placeLetter(int x, int y, char letter, boolean window)//Place a single letter on the board, true if window cords, false if board cords
	{
		if(!window) {
			if(tiles[x][y].getLetter() == '\0') {
				tiles[x][y].setLetter(letter);
				tiles[x][y].setTemporary(true);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(tiles[(int)(x / Cell.cellSize)][(int)(y / Cell.cellSize)].getLetter() == '\0') {
				tiles[(int)(x / Cell.cellSize)][(int)(y / Cell.cellSize)].setLetter(letter);
				tiles[(int)(x / Cell.cellSize)][(int)(y / Cell.cellSize)].setTemporary(true);
				return true;
			}
			else {
				return false;
			}
		}
	}
	public void removeLetter(int x, int y, boolean window)//Letter must be temporary
	{
		if(!window) {
			tiles[x][y].setLetter('\0');
		}
		else {
			tiles[(int)(x / Cell.cellSize)][(int)(y / Cell.cellSize)].setLetter('\0');
		}
	}
	public void removeAllTempLetters()
	{
		for(Cell[] a : tiles) {
			for(Cell b : a) {
				if(b.isTemporary()) {
					b.setLetter('\0');
					b.setTemporary(false);
				}
			}
		}
	}
	
	public boolean submitWord()
	{
		List<Cell> placed = new ArrayList<Cell>();
		for(Cell[] a : tiles) {
			for(Cell b : a) {
				if(b.isTemporary()) {
					placed.add(b);
				}
			}
		}
		
		if(placed.size() > 0) {
			boolean sameX = true;
			boolean sameY = true;
			for(Cell c : placed) {
				if(c.getBoardX() != placed.get(0).getBoardX()) {
					sameX = false;
				}
				if(c.getBoardY() != placed.get(0).getBoardY()) {
					sameY = false;
				}
			}
			if(sameX) {
				boolean before;
				int loop = 0;
				do {
					if(tiles[placed.get(0).getBoardX()][placed.get(0).getBoardY() - (1 + loop)].getLetter() != '\0') {
						placed.add(0, tiles[placed.get(0).getBoardX()][placed.get(0).getBoardY() - (1 + loop)]);
						loop++;
						before = true;
					}
					else {
						before = false;
					}
				} while(before);
				
				loop = 0;
				do {
					if(tiles[placed.get(placed.size() - 1).getBoardX()][placed.get(placed.size() - 1).getBoardY() + (1 + loop)].getLetter() != '\0') {
						placed.add(placed.size(), tiles[placed.get(placed.size() - 1).getBoardX()][placed.get(placed.size() - 1).getBoardY() + (1 + loop)]);
						loop++;
						before = true;
					}
					else {
						before = false;
					}
				} while (before);
				boolean nextTo = true;
				for(int x = 0; x < placed.size() - 1; x ++) {
					if(placed.get(x).getBoardY() != (placed.get(x + 1).getBoardY() - 1)) {
						if(tiles[placed.get(x).getBoardX()][placed.get(x).getBoardY() + 1].getLetter() == '\0') {
							nextTo = false;
						}
						else {
							placed.add(x + 1, tiles[placed.get(x).getBoardX()][placed.get(x).getBoardY() + 1]);
						}
					}
				}
				if(nextTo) {
					String word = "";
					for(Cell c : placed) {
						word += String.valueOf(c.getLetter());
					}
					System.out.println(word);
					if(isWord(word)) {
						for(Cell c : placed) {
							c.setTemporary(false);
						}
						return true;
					}
					else {
						System.out.println("Submitted letters are not a word");
						return false;
					}
				}
			}
			else if(sameY) {
				boolean before;
				int loop = 0;
				do {
					if(tiles[placed.get(0).getBoardX() - (1 + loop)][placed.get(0).getBoardY()].getLetter() != '\0') {
						placed.add(0, tiles[placed.get(0).getBoardX() - (1 + loop)][placed.get(0).getBoardY()]);
						before = true;
						System.out.println("adding beginning letter");
						loop++;
					}
					else {
						before = false;
					}
				} while(before);
				loop = 0;
				do {
					if(tiles[placed.get(placed.size() - 1).getBoardX() + (1 + loop)][placed.get(placed.size() - 1).getBoardY()].getLetter() != '\0') {
						placed.add(placed.size(), tiles[placed.get(placed.size() - 1).getBoardX() + (1 + loop)][placed.get(placed.size() - 1).getBoardY()]);
						before = true;
						System.out.println("adding end letter" + loop);
						for(Cell c : placed) {
							System.out.println(c.getBoardX());
						}
						loop++;
					}
					else {
						before = false;
					}
				} while(before);
				boolean nextTo = true;
				for(int x = 0; x < placed.size() - 1; x ++) {
					if(placed.get(x).getBoardX() != (placed.get(x + 1).getBoardX() - 1)) {
						if(tiles[placed.get(x).getBoardX() + 1][placed.get(x).getBoardY()].getLetter() == '\0') {
							nextTo = false;
						}
						else {
							placed.add(x + 1, tiles[placed.get(x).getBoardX() + 1][placed.get(x).getBoardY()]);
						}
					}
				}
				if(nextTo) {
					String word = "";
					for(Cell c : placed) {
						word += String.valueOf(c.getLetter());
					}
					System.out.println(word);
					if(isWord(word)) {
						for(Cell c : placed) {
							c.setTemporary(false);
						}
						return true;
					}
					else {
						System.out.println("Submitted letters are not a word");
						return false;
					}
				}
			}
		}
		System.out.println("Submitted letters are not linear");
		return false;
	}
	
	public int getScore()
	{
		int score = 0;
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				if(tiles[x][y].getLetter() != '\0') {
					score += (letterPointVals.get(tiles[x][y].getLetter())) * tiles[x][y].getMultiplier();
				}
			}
		}
		return score;
	}
	
	private boolean isWord(String word)
	{
		return validWords.contains(word);
	}
	
	public void render(GameContainer arg0, Graphics arg1)
	{
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				if(tiles[x][y].getLetter() != '\0') {
					if(tiles[x][y].isTemporary())
					{
						Scrabble.getImage(String.valueOf(tiles[x][y].getLetter()).toUpperCase()).draw(tiles[x][y].getWindowX(), tiles[x][y].getWindowY(), 40, 40, Color.transparent.red);
					}
					else {
						Scrabble.getImage(String.valueOf(tiles[x][y].getLetter()).toUpperCase()).draw(tiles[x][y].getWindowX(), tiles[x][y].getWindowY(), 40, 40);
					}
				}
			}
		}
	}
	
	private void setLetter(char letter, int x, int y)
	{
		tiles[x][y].setLetter(letter);
	}
}
