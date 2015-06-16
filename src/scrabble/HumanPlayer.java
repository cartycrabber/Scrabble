package scrabble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;;

public class HumanPlayer extends Player{

	private List<Cell> placed;
	private Cell selected;
	private int boardWidth;
	
	public HumanPlayer(String name, Board board, int boardWidth)
	{
		super(name, board);
		turn = false;
		placed = new ArrayList<Cell>();
		this.boardWidth = boardWidth;
		for(int x = 0; x < TRAY_SIZE; x++) {
			tray.get(x).setWindow(boardWidth + (50 * x) + (x * 10) + 50, 540);
		}
	}
	@Override
	public void render(GameContainer arg0, Graphics arg1) {
		if(turn) {
			for(int x = 0; x < tray.size(); x++) {
				Scrabble.getImage(String.valueOf(tray.get(x).getLetter())).draw(tray.get(x).getWindowX(), tray.get(x).getWindowY());
			}
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		if(turn) {
			for(Cell c : tray) {
				if(c.checkIfWithin(x, y)) {
					selected = c;
					break;
				}
			}
			
			if((selected != null) && (x < boardWidth)) {
				if(board.placeLetter(x, y, selected.getLetter(), true)) {;
					placed.add(selected);
					tray.remove(selected);
					selected = null;
				}
			}
		}
	}
	
	public void keyPressed(int key, char c) {
		if(turn) {
			if(key == Input.KEY_RETURN) {
				if(placed.size() > 0) {
					int submitted = board.submitWord();
					if(submitted >= 0) {
						score += submitted;
						/*Random r = new Random();
						for(int x = 0; x < placed.size(); x++) {
							char l = (char)(r.nextInt(26) + 'a');
							if(letterLimits.get(l) > 0) {
								placed.get(x).setLetter(l);;
							}
							else {
								System.out.println("No letters left: " + l);
								x -= 1;
							}
						}*/
						placed = newLetters(placed);
						tray.addAll(placed);
						placed.clear();
						turn = false;
					}
				}
			}
			if(key == Input.KEY_DELETE) {
				tray.addAll(placed);
				board.removeAllTempLetters();
				placed.clear();
				System.out.println("Returning");
			}
		}
	}
	@Override
	public void takeTurn() {
		System.out.println(name + "'s turn!");
		turn = true;
	}
}
