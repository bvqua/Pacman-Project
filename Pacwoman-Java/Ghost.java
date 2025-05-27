//Name: Brian Quach
//Date: 03/27/2024
//Description: Ghost class of Assignment 5

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ghost extends Sprite 
{
    private static BufferedImage image;
    private static BufferedImage blueImage;
    private static BufferedImage eyesImage;

    boolean isBlue = false;
    boolean isEyes = false;
    boolean clear = false;

    int counter = 0;

    public Ghost(int x, int y, int w, int h) 
    {
        super(x, y, w, h, "pacmanImages/blinky1.png");
        loadImage();
    }

    private static void loadImage() 
    {
        try 
        {
            image = ImageIO.read(new File("pacmanImages/blinky1.png"));
            blueImage = ImageIO.read(new File("pacmanImages/ghost1.png"));
            eyesImage = ImageIO.read(new File("pacmanImages/ghost5.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int scrollPosY) 
    {
        if (isBlue) 
        {
            g.drawImage(blueImage, x, y - scrollPosY, w, h, null);
        } 
        else if (isEyes) 
        {
            g.drawImage(eyesImage, x, y - scrollPosY, w, h, null);
        } 
        else if (!clear)
        {
            g.drawImage(image, x, y - scrollPosY, w, h, null);
        }
    }

    public Ghost(Json ob) 
    {
        super(
        (int) ob.getLong("x"),
        (int) ob.getLong("y"),
        (int) ob.getLong("w"), 
        (int) ob.getLong("h"),
        "pacmanImages/blinky1.png");
    }

    public Json marshal() 
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    @Override 
    public String toString()
    {
        return "Ghost (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    public void update() 
    {
        if (isBlue) 
        {
            counter++;
            if (counter >= 20) 
            {
                isBlue = false;
                isEyes = true;
            }
        } 
        else if (isEyes) 
        {
            counter++;
            if (counter >= 40) 
            {
                isEyes = false;
                clear = true;
            }
        }
    }

    boolean isPacman()
    {
        return false;
    }

    boolean isWall()
    {
        return false;
    }
    
    boolean isGhost()
    {
        return true;
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