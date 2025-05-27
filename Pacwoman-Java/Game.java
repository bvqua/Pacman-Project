//Name: Brian Quach
//Date: 03/27/2024
//Description: Game class of Assignment 5

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	private Model model;
	private Controller controller;
	private View view;

	public static final int worldW = 500;
    public static final int worldH = 3000;
    public static final int viewW = 500;
    public static final int viewH = 1000;

	public Game()
	{
		model = new Model();
		Json modelJson = Json.load("map.json");
		model.unmarshal(modelJson);
		controller = new Controller(model);
		new Wall(0, 0, 50, 50);
		view = new View(controller, model);

		this.setTitle("A5 - Pacman!");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}