//Name: Brian Quach
//Date: 03/27/2024
//Description: Pacman class of Assignment 5

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pacman extends Sprite
{
    public int direction;
    public int currentImage;
    public int prevX;
    public int prevY;
    private static BufferedImage image;
    BufferedImage[] pacmanImages;

    public Pacman(int x, int y, int w, int h)
    {
        super(x, y, w, h, "pacmanImages/pacman1.png");
        this.direction = 0;
        this.currentImage = 0;
        this.prevX = x;
        this.prevY = y;

        try 
		{
            pacmanImages = new BufferedImage[12];
            for (int i = 0; i < 12; i++) 
			{
                pacmanImages[i] = ImageIO.read(new File("pacmanImages/pacman" + (i+1) + ".png"));
            }
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
            System.exit(1);
        }
        
        if (image == null) 
        {
            loadImage();
        }
    }

    private static void loadImage() 
    {
        try 
        {
            image = ImageIO.read(new File("pacmanImages/pacman1.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int scrollPosY) 
    {
        if (currentImage > 2) 
        {
            currentImage = 0;
        }
        g.drawImage(pacmanImages[currentImage + 3 * direction], x, y - scrollPosY, null);
    }   

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public void update()
    {

    }
    
    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    public Json marshal()
	{
		return null;
	}

    boolean isPacman()
    {
        return true;
    }

    boolean isWall()
    {
        return false;
    }

    boolean isGhost()
    {
        return false;
    }

    boolean isFruit()
    {
        return false;
    }

    boolean isPellet()
    {
        return false;
    }
}