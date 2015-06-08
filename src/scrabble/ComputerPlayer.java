package scrabble;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, Board board)
	{
		super(name, board);
	}
	@Override
	public void render(GameContainer arg0, Graphics arg1) {
		
	}

	public void takeTurn()
	{
		turn = true;
	}
}
