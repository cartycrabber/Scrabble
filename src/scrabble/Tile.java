package scrabble;

public class Tile {
	
	private int multiplier;
	private char letter;
	private int boardX;
	private int boardY;
	
	public Tile(int multiplier, int boardX, int boardY)
	{
		this.multiplier = multiplier;
		this.boardX = boardX;
		this.boardY = boardY;
		this.letter = '\0';
	}
	public Tile(int multiplier, int boardX, int boardY, char letter)
	{
		this.multiplier = multiplier;
		this.boardX = boardX;
		this.boardY = boardY;
		this.letter = letter;
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
}
