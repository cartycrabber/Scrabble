package scrabble;

import org.newdawn.slick.Image;

public class Cell {
	
	private int multiplier;
	private char letter;
	private int boardX;
	private int boardY;
	private int windowX;
	private int windowY;
	private boolean temporary;
	
	private Image img;
	
	public static final int CELL_SIZE = 40;
	public static final char DEFAULT_CHAR = '\0';
	
	
	public Cell()
	{
		boardX = -1;
		boardY = -1;
		windowX = -1;
		windowY = -1;
		letter = DEFAULT_CHAR;
	}
	public Cell(int boardX, int boardY, boolean temporary)
	{
		this.boardX = boardX;
		this.boardY = boardY;
		this.windowX = boardX * CELL_SIZE;
		this.windowY = boardY * CELL_SIZE;
		this.letter = DEFAULT_CHAR;
		this.temporary = temporary;
	}
	public Cell(int boardX, int boardY, char letter, boolean temporary)
	{
		this.boardX = boardX;
		this.boardY = boardY;
		this.windowX = boardX * CELL_SIZE;
		this.windowY = boardY * CELL_SIZE;
		this.letter = letter;
		this.temporary = temporary;
	}
	public Cell(int multiplier, int boardX, int boardY, boolean temporary)
	{
		this.multiplier = multiplier;
		this.boardX = boardX;
		this.boardY = boardY;
		this.windowX = boardX * CELL_SIZE;
		this.windowY = boardY * CELL_SIZE;
		this.letter = DEFAULT_CHAR;
		this.temporary = temporary;
	}
	public Cell(int multiplier, int boardX, int boardY, char letter, boolean temporary)
	{
		this.multiplier = multiplier;
		this.boardX = boardX;
		this.boardY = boardY;
		this.windowX = boardX * CELL_SIZE;
		this.windowY = boardY * CELL_SIZE;
		this.letter = letter;
		this.temporary = temporary;
	}
	
	public boolean checkIfWithin(int x, int y)//Check if a point is within the border of the cell
	{
		if((x > windowX) && (x < (windowX + CELL_SIZE))) {
			if((y > windowY) && (y < (windowY + CELL_SIZE))) {
				return true;
			}
		}
		return false;
	}
	
	public void setLetter(char l)
	{
		letter = l;
	}
	public char getLetter()
	{
		return letter;
	}
	public int getMultiplier()
	{
		return multiplier;
	}
	public void setMultiplier(int mult)
	{
		multiplier = mult;
	}
	
	public int getBoardX()
	{
		return boardX;
	}
	public void setBoardX(int x)
	{
		boardX = x;
	}
	public int getBoardY()
	{
		return boardY;
	}
	public void setBoardY(int y)
	{
		boardY = y;
	}
	public void setBoard(int x, int y)
	{
		boardX = x;
		boardY = y;
	}
	
	public void setWindowX(int x)
	{
		windowX = x;
	}
	public int getWindowX()
	{
		return windowX;
	}
	
	public void setWindowY(int y)
	{
		windowY = y;
	}
	public int getWindowY()
	{
		return windowY;
	}
	
	public void setWindow(int x, int y)
	{
		windowX = x;
		windowY = y;
	}
	
	public boolean isTemporary()
	{
		return temporary;
	}
	public void setTemporary(boolean temporary)
	{
		this.temporary = temporary;
	}
}
