package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	
	private Map<Character, Integer> letterPointVals = new HashMap<Character, Integer>();
	private int width;
	private int height;
	private Tile[][] tiles;
	private ArrayList<String> validWords = new ArrayList<String>();
	
	public Board(int width, int height)
	{
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				tiles[x][y] = new Tile(1, x, y);
			}
		}
		
		letterPointVals.put('a', 1);
		letterPointVals.put('b', 3);
		letterPointVals.put('c', 3);
		letterPointVals.put('d', 2);
		letterPointVals.put('e', 1);
		letterPointVals.put('f', 4);
		letterPointVals.put('g', 2);
		letterPointVals.put('h', 4);
		letterPointVals.put('i', 1);
		letterPointVals.put('j', 8);
		letterPointVals.put('k', 5);
		letterPointVals.put('l', 1);
		letterPointVals.put('m', 3);
		letterPointVals.put('n', 1);
		letterPointVals.put('o', 1);
		letterPointVals.put('p', 3);
		letterPointVals.put('q', 10);
		letterPointVals.put('r', 1);
		letterPointVals.put('s', 1);
		letterPointVals.put('t', 1);
		letterPointVals.put('u', 1);
		letterPointVals.put('v', 4);
		letterPointVals.put('w', 4);
		letterPointVals.put('x', 8);
		letterPointVals.put('y', 4);
		letterPointVals.put('z', 10);
		
		Scanner s = null;
		try {
			s = new Scanner(new File("C:\\Users\\Will\\workspace\\Scrabble\\rescources\\dictionaries\\SCOWL-70.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (s.hasNext()){
			validWords.add(s.next());
		}
		s.close();
	}
	
	public boolean placeWord(String word, int xStart, int xEnd, int yStart, int yEnd)//Returns true if valid move, false if not
	{
		if((xStart == xEnd) || (yStart == yEnd)) { //Check to make sure that it is a line, not a rectangle
			if(isWord(word)) {
				boolean horizOriented = (xEnd - xStart) > 0 ? true : false;
				for(int y = yStart; y <= yEnd; y++) {
					for(int x = xStart; x <= xEnd; x++) {
						if(horizOriented) {
							tiles[x][y].setLetter(word.substring(x, (x + 1)).charAt(0));
						}
						else {
							tiles[x][y].setLetter(word.substring(y, (y + 1)).charAt(0));
						}
					}
				}
				return true;
			}
			else {
				System.out.println("Invalid word");
				return false;
			}
		}
		else
		{
			System.out.println("Word placement must be in a line");
			return false;
		}
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
	
	private void setLetter(char letter, int x, int y)
	{
		tiles[x][y].setLetter(letter);
	}
}
