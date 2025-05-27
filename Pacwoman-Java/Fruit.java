//Name: Brian Quach
//Date: 03/27/2024
//Description: Fruit class of Assignment 5

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Fruit extends Sprite 
{
    double speed = 10;
    private static BufferedImage image;

    public Fruit(int x, int y, int w, int h) 
    {
        super(x, y, w, h, "pacmanImages/fruit2.png");
        System.out.print("printed");
        loadImage();
    }

    private static void loadImage() 
    {
        try 
        {
            image = ImageIO.read(new File("pacmanImages/fruit2.png"));
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
        y += speed;
    }

    public Fruit(Json ob) 
    {
        super(
        (int) ob.getLong("x"),
        (int) ob.getLong("y"),
        (int) ob.getLong("w"), 
        (int) ob.getLong("h"),
        "pacmanImages/fruit2.png");
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
        return "Fruit (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
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
        return true;
    }

    boolean isPellet()
    {
        return false;
    }
}