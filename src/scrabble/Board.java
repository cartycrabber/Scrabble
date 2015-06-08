package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
				tiles[x][y] = new Cell(1, x, y);
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
			s = new Scanner(new File("C:\\Users\\Will\\workspace\\Scrabble\\res\\dictionaries\\SCOWL-70.txt"));
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
	
	public void print()
	{
		System.out.println(" ___ ___ ___ ___ ___");
		for(int y = 0; y < tiles[0].length; y++) {
			System.out.print("|");
			for(int x = 0; x < tiles.length; x++) {
				if(tiles[x][y].getLetter() == '\0') {
					System.out.print("   |");
				}
				else {
					System.out.print(" " + tiles[x][y].getLetter() + " |");
				}
			}
			System.out.print("\n|___|___|___|___|___|\n");
		}
		
	}
	
	public void render(GameContainer arg0, Graphics arg1)
	{
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				if(tiles[x][y].getLetter() != '\0') {
					Scrabble.getImage(String.valueOf(tiles[x][y].getLetter()).toUpperCase()).draw(tiles[x][y].getWindowX(), tiles[x][y].getWindowY(), 40, 40);
				}
			}
		}
	}
	
	private void setLetter(char letter, int x, int y)
	{
		tiles[x][y].setLetter(letter);
	}
}
