//Name: Brian Quach
//Date: 03/27/2024
//Description: Pellet class of Assignment 5

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pellet extends Sprite 
{
    private static BufferedImage image;

    public Pellet(int x, int y, int w, int h) 
    {
        super(x, y, w, h, "pacmanImages/pellet.png");
        loadImage();
    }

    private static void loadImage() 
    {
        try 
        {
            image = ImageIO.read(new File("pacmanImages/pellet.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int scrollPosY) 
    {
        g.drawImage(image, x, y - scrollPosY, w, h, null);
    }

    public void update() 
    {
    }

    public Pellet(Json ob) 
    {
        super(
        (int) ob.getLong("x"),
        (int) ob.getLong("y"),
        (int) ob.getLong("w"), 
        (int) ob.getLong("h"),
        "pacmanImages/pellet.png");
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
        return "Pellet (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
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
        return false;
    }

    boolean isFruit()
    {
        return false;
    }

    boolean isPellet()
    {
        return true;
    }
}
