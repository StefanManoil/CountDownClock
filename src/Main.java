import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import data.Event;
import data.EventsList;
import gui.InputOutput;
import gui.UI;
import gui.Display;
import utilities.ConfigManager;

import javax.imageio.ImageIO;

/**
 * @Stefan Manoil
 * Simple GUI to select either inputting or displaying events
 */
public class Main extends JFrame{
	JLabel labelTitle, labelInput, labelDisplay, lblLogo1, lblLogo2;
	Font titleFont;
	JFileChooser jfc;
	public ArrayList<Event> events = new ArrayList<Event>();

	PrintStream  errorsPS = null;
	JButton btnDisplay, btnInput;
	JLabel lblTitle, lblInput, lblDisplay;
	Font fontTitle, fontBody, ncaa, montserrat, commodore;
	ImageIcon marcyLogo;


	public Main(){
		ConfigManager.initiateProperties();
		FileOutputStream fos = null;
		File file = new File(System.getProperty("user.home"), "/Desktop/CountdownClock/"+"errors.txt");
		try {
			fos = new FileOutputStream(file);
		}
		catch (IOException ioe) {
			System.err.println("redirection not possible:"+ ioe);
		}
		errorsPS = new PrintStream(fos);
		//System.setErr(errorsPS); // trying to redirect the errors stream to a file
		System.err.println("errors go to file");
		ConfigManager.loadProperties();
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		ncaa = loadFont("ncaa");
		montserrat = loadFont("montserrat");
		commodore = loadFont("Commodore Rounded v1.2");
		fontTitle = ncaa;
		fontBody = ncaa;
		fontTitle = fontTitle.deriveFont(Font.BOLD, (int)(ConfigManager.eventNameSize*0.6));
		fontBody = fontBody.deriveFont(Font.BOLD, (int)(ConfigManager.eventNameSize*0.4));

		try {
			marcyLogo = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/logo.png")));
		}
		catch (IOException ioe) {
			System.out.println("Couldn't load the logo");
			System.err.println("Couldn't load the logo");
			ioe.printStackTrace();
		}  

		setTitle("Marcy's Athletic Events");
		setLayout(new GridBagLayout());

		lblLogo1 = new JLabel(marcyLogo);
		lblLogo2 = new JLabel(marcyLogo);

		lblTitle = new JLabel("MARCY ATHETIC EVENTS - COUNTDOWN CLOCK");
		lblTitle.setFont(fontTitle);
		lblDisplay = new JLabel ("Select the event.json file to display");
		lblDisplay.setFont(fontBody);
		lblInput = new JLabel ("Input / edit / delete events");
		lblInput.setFont(fontBody);


		btnDisplay = new JButton ("DISPLAY  EVENTS");
		btnDisplay.setFont(fontBody);
		btnInput = new JButton ("ADD/EDIT EVENTS");
		btnInput.setFont(fontBody);


		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth =1;
		gc.gridheight = 1;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.NONE;
		//imageLabel1.setBackground(Color.GREEN);
		getContentPane().add(lblLogo1, gc);
		gc.gridx = 1;
		gc.gridwidth = 2;
		getContentPane().add(lblTitle, gc);

		gc.gridx = 3;//or 3
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.NORTHWEST;
		getContentPane().add(lblLogo2, gc);

		gc.gridy = 1;
		gc.gridx = 1;
		gc.gridwidth = 1;
		getContentPane().add(lblInput, gc);

		gc.gridx = 2;
		btnInput.addActionListener(a -> initInput());
		getContentPane().add(btnInput, gc);

		gc.gridy = 2;
		gc.gridx = 1;
		gc.gridwidth = 1;
		getContentPane().add(lblDisplay, gc);

		gc.gridx = 2;
		gc.gridwidth = 1;
		btnDisplay.addActionListener(a -> initDisplay());
		getContentPane().add(btnDisplay, gc);

		getContentPane().setBackground(Color.RED);
		setSize(200, 100);
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void initDisplay(){
		//setVisible(false);
		/*jfc = new JFileChooser();
    jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/CountdownClock"));
    int result = jfc.showOpenDialog(this);

    if(result == JFileChooser.APPROVE_OPTION){
        File f = jfc.getSelectedFile();
        System.out.println(f.toString());
        String path = f.getAbsolutePath();
        if(path.substring(path.length() - 4).equals("json")){
           try {
        	    events.clear();
                events = InputOutput.input(jfc.getSelectedFile().getAbsolutePath());
            } catch (Exception ex) {}
        }
     }*/
		readFromFile(); 
		new Display(events, ncaa, montserrat, commodore);

	}
	/**
	 * Loads True Type Fonts of the desired name
	 * @param fontName filename in folder resources/resources/
	 */
	private Font loadFont(String fontName) {
		InputStream fontStream = new BufferedInputStream(Main.class.getResourceAsStream("/other_resources/" + fontName + ".ttf"));
		System.out.println();
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		} catch(FontFormatException | IOException e) {
			System.err.println("Error loading font");
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		return font;
	}

	public void initInput(){
		//setVisible(false);
		new UI(montserrat, marcyLogo);
	}

	public void readFromFile() {
		File f = new File(System.getProperty("user.home") + "/Desktop/CountdownClock/events.json");
		String path = f.getAbsolutePath();
		if(path.substring(path.length() - 4).equals("json")){
			try {
				events.clear();
				events = InputOutput.input(path);
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR READING THE FILE!!!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

	public static void main(String[] args){
		Main selection = new Main();
	}

}
