package gui;
import javax.imageio.ImageIO;
import javax.swing.*;

import data.Event;
import data.EventsList;
import utilities.ConfigManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Display extends JFrame{

	static int EVENT_NAME_SIZE = ConfigManager.eventNameSize;
	static int CLOCK_SIZE = ConfigManager.clockSize;
	static int CLOCK_TEXT_SIZE = ConfigManager.hourMinSecSize;
	ImageIcon image1;
	ImageIcon image2;
	JLabel imageLabel1;
	JLabel imageLabel2;
	JLabel eventName;
	JLabel daysText;
	JLabel days;
	JLabel hoursText;
	JLabel hours;
	JLabel minsText;
	JLabel mins;
	JLabel secsText;
	JLabel secs;
	JPanel clockPanel;
	ImagesPanel centerPanel;
	Timer myTimer;//a Timer used to generate action events

	Font fontEvent, fontClock, fontClockText;
	public ArrayList<Event> events;
	public ArrayList<Event> eventsToDisplay;
	int currentEvent = 0;
	int tick = 0;
	int transitionTime = ConfigManager.transitionTime;

	private Font ncaa, montserrat;

	//the constructor
	public Display(ArrayList<Event> events, Font ncaa, Font montserrat, Font commodore){
		this.events = events;
		eventsToDisplay = new ArrayList<Event>();
		this.ncaa = ncaa;
		this.montserrat = montserrat;
		filterEvents();
		setTitle("Marcy Athletic Events");//sets the title for the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//??????????????????
		events = new ArrayList<Event>();

		try {
			//image1 = new ImageIcon(ImageIO.read(Main.class.getResourceAsStream("/images/MARCL.jpg")));
			image1 = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/MARCL.jpg")));
			image2 = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/MARCL.jpg")));
		}
		catch (IOException ioe) {
			System.out.println("Couldn't load the images");
			ioe.printStackTrace();
		}    
		imageLabel1 = new JLabel(image1);
		imageLabel2 = new JLabel(image2);
		eventName = new JLabel ("GO SPIRIT!!!");
		daysText = new JLabel ("DAYS");
		hoursText = new JLabel ("HOURS");
		minsText = new JLabel ("MIN");
		secsText = new JLabel (" SEC");
		days = new JLabel ("00");
		hours = new JLabel ("00");
		mins = new JLabel ("00");
		secs = new JLabel ("00");
		clockPanel = new JPanel();
		centerPanel = new ImagesPanel();

		fontEvent = ncaa;
		fontClock = commodore;
		fontClockText = commodore;
		fontEvent = fontEvent.deriveFont(Font.BOLD, EVENT_NAME_SIZE);
		fontClock = fontClock.deriveFont(Font.BOLD, CLOCK_SIZE);
		fontClockText = fontClockText.deriveFont(Font.BOLD, CLOCK_TEXT_SIZE);

		//display the first event if any
		if (eventsToDisplay.size()>1) {
			currentEvent++;
			switchEvent(eventsToDisplay.get(0));
		}

		myTimer = new Timer (1000, new ActionListener(){
			public void actionPerformed (ActionEvent evt){
				tick++;
				if (tick > transitionTime) {//switch events
					tick = 0;
					currentEvent++;
					if (currentEvent > eventsToDisplay.size()-1){
						currentEvent = 0;
					}
					switchEvent(eventsToDisplay.get(currentEvent));
				}
				else {//just update the seconds
					updateClock(eventsToDisplay.get(currentEvent));
				}
			}
		});
		myTimer.start();
		//border layout for the frame:
		//centerPanel - the panel with images will be placed center   
		//clockPanel - The panel with the countdown clock will be placed south
		this.setLayout(new BorderLayout());

		//centerPanel
		//JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints centerPanelGC = new GridBagConstraints();

		//imageLabel1.setBackground(Color.GREEN);
		//imageLabel1.setOpaque(true);
		centerPanelGC.gridx = 0;
		centerPanelGC.gridy = 0;
		centerPanelGC.gridwidth = 1;
		centerPanelGC.gridheight = 1;
		centerPanelGC.weightx = 100.0;
		centerPanelGC.weighty = 100.0;
		centerPanelGC.ipady = 50;
		centerPanelGC.ipadx = 150;
		centerPanelGC.insets = new Insets(0, 0, 0, 0);
		centerPanelGC.anchor = GridBagConstraints.CENTER;
		centerPanelGC.fill = GridBagConstraints.NONE;
		centerPanel.add(imageLabel1, centerPanelGC);

		//imageLabel2.setBackground(Color.GREEN);
		//imageLabel2.setOpaque(true);
		centerPanelGC.gridx = 1;
		centerPanel.add(imageLabel2, centerPanelGC);
		this.add(centerPanel, BorderLayout.CENTER);
		//end centerPanel

		//clockPanel
		clockPanel.setBackground(Color.RED);

		clockPanel.setLayout(new GridBagLayout());
		GridBagConstraints clockPanelGC = new GridBagConstraints();

		clockPanelGC.gridx = 0;
		clockPanelGC.gridy = 0;
		clockPanelGC.gridwidth = 5;
		clockPanelGC.anchor = GridBagConstraints.WEST;
		clockPanelGC.fill = GridBagConstraints.NONE;
		clockPanelGC.weightx = 100.0;
		clockPanelGC.weighty = 100.0;
		clockPanelGC.insets = new Insets(30, 20, 0, 0);
		eventName.setOpaque(true);
		//eventName.setBackground(Color.BLACK);
		eventName.setBackground(Color.RED);
		eventName.setForeground(Color.WHITE);
		eventName.setHorizontalAlignment(SwingConstants.LEFT);
		eventName.setFont(fontEvent);
		clockPanel.add(eventName, clockPanelGC);

		formatLabelClock(days, fontClock); 
		clockPanelGC.gridx = 0;
		clockPanelGC.gridy = 1;
		clockPanelGC.gridwidth = 1;
		clockPanelGC.fill = GridBagConstraints.NONE;
		clockPanelGC.anchor = GridBagConstraints.CENTER;
		clockPanelGC.insets = new Insets(0, 20, 0, 0);
		//days.setBackground(Color.GREEN);
		clockPanel.add(days, clockPanelGC);

		JLabel afterDaysLabel = new JLabel("                                                                ");
		clockPanelGC.gridx = 1;
		clockPanelGC.ipadx= 0;
		clockPanelGC.insets = new Insets(0, 0, 0, 0);
		clockPanelGC.fill = GridBagConstraints.BOTH;
		afterDaysLabel.setBackground(Color.GRAY);	
		clockPanel.add(afterDaysLabel, clockPanelGC);

		formatLabelClock(hours, fontClock ); 
		clockPanelGC.anchor = GridBagConstraints.EAST;
		clockPanelGC.gridx = 2;
		clockPanelGC.fill = GridBagConstraints.NONE;
		clockPanelGC.weightx = 10.0;
		//clockPanelGC.ipadx=40;
		//hours.setBackground(Color.YELLOW);
		//hours.setHorizontalAlignment(SwingConstants.RIGHT);
		clockPanel.add(hours, clockPanelGC);

		formatLabelClock(mins, fontClock); 
		clockPanelGC.gridx = 3;
		//mins.setBackground(Color.GREEN);
		clockPanel.add(mins, clockPanelGC);

		formatLabelClock(secs, fontClock); 
		clockPanelGC.gridx = 4;
		//secs.setBackground(Color.CYAN);
		clockPanel.add(secs, clockPanelGC);

		clockPanelGC.gridx = 0;
		clockPanelGC.gridy = 2;
		clockPanelGC.ipadx=0;
		clockPanelGC.anchor = GridBagConstraints.CENTER;
		clockPanelGC.insets = new Insets(0, 50, 50, 0);
		daysText.setHorizontalAlignment(SwingConstants.CENTER);
		formatLabelClock(daysText, fontClockText); 
		clockPanel.add(daysText, clockPanelGC);

		JLabel afterDaysTextLabel = new JLabel("                                                                 ");
		clockPanelGC.gridx = 1;
		clockPanelGC.gridy = 2;
		clockPanelGC.insets = new Insets(0, 0, 50, 0);
		clockPanelGC.anchor = GridBagConstraints.CENTER;
		clockPanelGC.fill = GridBagConstraints.NONE;
		clockPanel.add(afterDaysTextLabel, clockPanelGC);

		formatLabelClock(hoursText, fontClockText); 
		clockPanelGC.gridx = 2;
		clockPanel.add(hoursText, clockPanelGC);

		formatLabelClock(minsText, fontClockText);
		clockPanelGC.gridx = 3;
		clockPanel.add(minsText, clockPanelGC);

		formatLabelClock(secsText, fontClockText);
		clockPanelGC.gridx = 4;
		clockPanel.add(secsText, clockPanelGC);

		this.add(clockPanel, BorderLayout.SOUTH);
		//end clockPanel


		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1420, 880);
		setVisible(true);
	}

	void formatLabelClock(JLabel toFormat, Font font) {
		toFormat.setOpaque(true);
		toFormat.setBackground(Color.RED);
		toFormat.setForeground(Color.WHITE);
		toFormat.setAlignmentX(CENTER_ALIGNMENT);
		toFormat.setFont(font); 
	}

	/*a method that removes past event from event_list*/   
	void filterEvents() {
		for(int i=0; i<events.size(); i++) {
			Date da = new Date();
			
			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.YEAR,events.get(i).getYear());
			cal.set(Calendar.MONTH,events.get(i).getMonth()-1);
			cal.set(Calendar.DAY_OF_MONTH,events.get(i).getDay());
			cal.set(Calendar.HOUR_OF_DAY,events.get(i).getHour());
			cal.set(Calendar.MINUTE,events.get(i).getMinute());
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);

			Date eventDate = cal.getTime();
			long secToEvent = (eventDate.getTime() - da.getTime()) /1000;

			long daysToDisplay = secToEvent / (24 * 60 * 60);
			long hoursToDisplay = (secToEvent % (24 * 60 * 60)) / (60 * 60);
			long minsToDisplay = ((secToEvent % (24 * 60 * 60)) % (60 * 60)) / 60;
			long secsToDisplay = ((secToEvent % (24 * 60 * 60)) % (60 * 60)) % 60;

			if(eventDate.after(da)) {
				eventsToDisplay.add(events.get(i));
			}
		}
	}

	void switchEvent(Event e) {
		centerPanel.setBackground(e.getBackgroundColour());
		clockPanel.setBackground(e.getBannerColour());
		eventName.setBackground(e.getBannerColour());
		days.setBackground(e.getBannerColour());
		hours.setBackground(e.getBannerColour());
		mins.setBackground(e.getBannerColour());
		secs.setBackground(e.getBannerColour());
		daysText.setBackground(e.getBannerColour());
		hoursText.setBackground(e.getBannerColour());
		minsText.setBackground(e.getBannerColour());
		secsText.setBackground(e.getBannerColour());
		try {
			image1 = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/"+e.getImageLeft())));
			image2 = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/"+e.getImageRight())));
		}
		catch (IOException ioe) {
			System.out.println("Couldn't load the images");
			ioe.printStackTrace();
		}
		imageLabel1.setIcon(image1);
		imageLabel2.setIcon(image2);
		eventName.setText(e.getName());

		int min = e.getMinute();
		int h = e.getHour();
		int d = e.getDay();
		int m = e.getMonth();
		int y = e.getYear();

		Date da = new Date();
		/*        Date testDate = new Date(y, m - 1, d, h, min);
        System.out.println("then " +testDate.getTime());*/
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR,e.getYear());
		cal.set(Calendar.MONTH,e.getMonth()-1);
		cal.set(Calendar.DAY_OF_MONTH,e.getDay());
		cal.set(Calendar.HOUR_OF_DAY,e.getHour());
		cal.set(Calendar.MINUTE,e.getMinute());
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		Date eventDate = cal.getTime();
		long secToEvent = (eventDate.getTime() - da.getTime()) /1000;

		long daysToDisplay = secToEvent / (24 * 60 * 60);
		long hoursToDisplay = (secToEvent % (24 * 60 * 60)) / (60 * 60);
		long minsToDisplay = ((secToEvent % (24 * 60 * 60)) % (60 * 60)) / 60;
		long secsToDisplay = ((secToEvent % (24 * 60 * 60)) % (60 * 60)) % 60;

		//days.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(daysToDisplay+"", 3)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">DAYS</font></div></html>");
		//hours.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(hoursToDisplay+"", 2)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">HOUR</font></div></html>");
		//mins.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(minsToDisplay+"", 2)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">MIN</font></div></html>");
		//secs.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(secsToDisplay+"", 2)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">SEC</font></div></html>");

		days.setText(zFill(daysToDisplay+"", 3));
		hours.setText(zFill(hoursToDisplay+"", 2)+":");
		mins.setText(zFill(minsToDisplay+"", 2)+":");
		secs.setText(zFill(secsToDisplay+"", 2));
	}

	void updateClock(Event e) {
		int min = e.getMinute();
		int h = e.getHour();
		int d = e.getDay();
		int m = e.getMonth();
		int y = e.getYear();

		Date da = new Date();
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR,e.getYear());
		cal.set(Calendar.MONTH,e.getMonth()-1);
		cal.set(Calendar.DAY_OF_MONTH,e.getDay());
		cal.set(Calendar.HOUR_OF_DAY,e.getHour());
		cal.set(Calendar.MINUTE,e.getMinute());
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		Date eventDate = cal.getTime();
		long secToEvent = (eventDate.getTime() - da.getTime()) /1000;

		long daysToDisplay = secToEvent / (24 * 60 * 60);
		long hoursToDisplay = (secToEvent % (24 * 60 * 60)) / (60 * 60);
		long minsToDisplay = ((secToEvent % (24 * 60 * 60)) % (60 * 60)) / 60;
		long secsToDisplay = ((secToEvent % (24 * 60 * 60)) % (60 * 60)) % 60;
		//days.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(daysToDisplay+"", 3)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">DAYS</font></div></html>");
		//hours.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(hoursToDisplay+"", 2)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">HOUR</font></div></html>");
		//mins.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(minsToDisplay+"", 2)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">MIN</font></div></html>");
		// secs.setText("<html><div align = center style= font-size:"+CLOCK_SIZE+"><b>"+zFill(secsToDisplay+"", 2)+"</b></font></div><div align = center style= font-size:"+CLOCK_TEXT_SIZE+">SEC</font></div></html>");
		days.setText(zFill(daysToDisplay+"", 3));
		hours.setText(zFill(hoursToDisplay+"", 2)+":");
		mins.setText(zFill(minsToDisplay+"", 2)+":");
		secs.setText(zFill(secsToDisplay+"", 2));
	}

	String zFill(String str, int n){
		String out = "";
		for(int i = 0; i < n - str.length(); i++){
			out += "0";
		}
		return out + str;
	}
}