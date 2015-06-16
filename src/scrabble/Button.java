package scrabble;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Button {
	
	private static List<Button> register = new ArrayList<Button>();
	
	//Coordinates on screen
	private int x;
	private int y;
	
	//Width and height of button
	private int width;
	private int height;
	
	//Image to render
	private Image image;
	
	//Whether or not it has been clicked
	private boolean clicked = false;
	//Whether or not it was clicked last frame
	private boolean prevClicked = false;
	//Whether or not it is set to render
	private boolean render = false;

	public Button(int xPosition, int yPosition, Image image)
	{
		x = xPosition;
		y = yPosition;
		width = image.getWidth();
		height = image.getHeight();
		this.image = image;
		register.add(this);
	}
	public Button(int xPosition, int yPosition, int width, int height)
	{
		x = xPosition;
		y = yPosition;
		this.width = width;
		this.height = height;
		register.add(this);
	}
	public Button(int xPosition, int yPosition, int width, int height, Image image)
	{
		x = xPosition;
		y = yPosition;
		this.width = width;
		this.height = height;
		this.image = image;
		register.add(this);
	}
	
	//if render is true, draws the image
	private void render(GameContainer arg0, Graphics arg1)
	{
		if(render){
			if(image != null) {
				image.draw(x, y, width, height);
			}
		}
	}
	
	//Checks to see if the button is being clicked
	private void update(GameContainer arg0, int arg1)
	{
		Input input = arg0.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		if(((mx > x) && (mx < (x + width))) && ((my > y) && (my < (y + height)))) {
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				
				clicked = true;
			}
			else {
				clicked = false;
			}
		}
		else {
			clicked = false;
		}
	}
	
	//Whether the button is currently being pressed this frame
	public boolean isPressedEvent()
	{
		return clicked;
	}
	
	//Returns true for the first frame the button is pressed
	public boolean isDownEvent()
	{
		if(clicked) {
			if(!prevClicked) {
				prevClicked = true;
				return true;
			}
		}
		else {
			if(prevClicked) {
				prevClicked = false;
			}
		}
		return false;
	}
	
	//Whether or not to render the button
	public void setRender(boolean render)
	{
		this.render = render;
	}
	public boolean isRender()
	{
		return render;
	}
	
	//To render all existing buttons
	public static void renderAll(GameContainer arg0, Graphics arg1)
	{
		for(Button b : register) {
			b.render(arg0, arg1);
		}
	}
	//To update all existing buttons
	public static void updateAll(GameContainer arg0, int arg1)
	{
		for(Button b : register) {
			b.update(arg0, arg1);
		}
	}
}
