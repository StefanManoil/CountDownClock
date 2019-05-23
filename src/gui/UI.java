package gui;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.Calender;
import data.Event;
import utilities.ConfigManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class UI extends JFrame implements ListSelectionListener{
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newFile, save, open;

	public JButton addEvent, selectImageLeft, selectImageRight, updateEvent, removeEvent;
	JList<String> eventsSelection;
	DefaultListModel listModel;//used to make the JList
	JScrollPane scrollAwardList;
	public JComboBox hourBox, minuteBox;
	public JSlider rBackground, gBackground, bBackground;
	public JSlider rBanner, gBanner, bBanner;
	public JTextField txtNameEvent;
	private JLabel lblEvents, lblImageLeft, lblImageRight, lblImgRight, lblImgLeft, r1l, g1l, b1l, r2l, g2l, b2l, nameL;
	public JLabel colourBackgroundBlock, colourBannerBlock, statusLabel, lblLogo1, lblLogo2;
	private JTextArea txtInstructions;

	public Calender calender;

	public ArrayList<Event> events = new ArrayList<Event>();

	private ActionEvents actionEvents;

	public String imageLeft_path;
	public String imageRight_path;
	int indexEvent = 0;

	private JFrame frame;

	Font fontEvent, fontClock, fontClockText;

	private Color bannerColor, backgroundColor;
	ImageIcon marcyLogo;

	JPanel all, slidersBanner, slidersBackground, colourBackgroundPanel, colourBannerPanel, datePanel, butPanel;

	public UI(Font montserrat, ImageIcon marcyLogo){
		all = new JPanel();
		slidersBanner = new JPanel();
		slidersBackground = new JPanel();
		colourBackgroundPanel = new JPanel();
		colourBannerPanel = new JPanel();
		datePanel = new JPanel();
		butPanel = new JPanel();
		butPanel.setBackground(new Color(255, 255, 255));
		slidersBanner.setLayout(new BoxLayout(slidersBanner, BoxLayout.Y_AXIS));
		slidersBackground.setLayout(new BoxLayout(slidersBackground, BoxLayout.Y_AXIS));
		all.setLayout(new GridBagLayout());
		this.marcyLogo = marcyLogo;

		//actionEvents = new ActionEvents(frame, this);

		Font fontInstructions = montserrat;
		fontInstructions = fontInstructions.deriveFont(Font.BOLD, (int)(ConfigManager.eventNameSize/6));

		lblLogo1 = new JLabel(marcyLogo);
		lblLogo2 = new JLabel(marcyLogo);

		txtInstructions = new JTextArea("Follow these steps to add/edit/delete events:\n"
				+ " - To ADD a new event:\n"
				+ "           1. Select New Event in the Events list.\n"
				+ "           2  Fill out the information: description, left/right images, date and time,\n"
				+ "           banner/background color, and press 'Add Event'.\n"
				+ " - To UPDATE the info for an event already entered:\n"
				+ "           1. Select the specific event in the 'Events List',\n"
				+ "           2. Update the event details as appropriae, and press 'Update Event'.\n"
				+ " - To DELETE an event from the 'Events List':\n"
			    + "           1. Select the specific event in the 'Events List' and press 'Delete Event',\n"
				+ "           2. !!! An events cannot be retrieved once deleted, it needs to be added again.");
		txtInstructions.setEditable(false);
		txtInstructions.setOpaque(false);
		txtInstructions.setFont(fontInstructions);

		//Inits
		//#1 Background
		//#2 Banner
		lblEvents = new JLabel ("Events:");
		lblEvents.setFont(fontInstructions);
		bannerColor = new Color(255,0,0);//banner default red
		backgroundColor = new Color(255,255,255);//background default white

		hourBox = new JComboBox(new String[]{"0", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"});
		minuteBox = new JComboBox(new String[]{"0", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"});

		calender = new Calender();

		addEvent = new JButton("Add Event");
		addEvent.setFont(fontInstructions);
		updateEvent = new JButton("Update Event");
		updateEvent.setFont(fontInstructions);
		removeEvent = new JButton("Remove Event");
		removeEvent.setFont(fontInstructions);

		lblImageLeft = new JLabel ("Left Image:");
		lblImageLeft.setFont(fontInstructions);
		lblImageRight = new JLabel ("Right Image:");
		lblImageRight.setFont(fontInstructions);
		selectImageLeft = new JButton("Select Left Image");
		selectImageLeft.setFont(fontInstructions);
		selectImageRight = new JButton("Select Right Image");
		selectImageRight.setFont(fontInstructions);

		lblImgLeft = new JLabel(marcyLogo);
		lblImgRight = new JLabel(marcyLogo);

		colourBackgroundBlock = new JLabel(new ImageIcon(getBufferedImage(new Color(0,0,0))));
		colourBannerBlock = new JLabel(new ImageIcon(getBufferedImage(new Color(255,0,0))));
		statusLabel = new JLabel();
		statusLabel.setVisible(false);

		rBanner = new JSlider(0, 255, 255);
		gBanner = new JSlider(0, 255, 0);
		bBanner = new JSlider(0, 255, 0);
		rBackground = new JSlider(0, 255, 255);
		gBackground = new JSlider(0, 255, 255);
		bBackground = new JSlider(0, 255, 255);
		
		r1l = new JLabel("R");
		r1l.setFont(fontInstructions);
		g1l = new JLabel("G");
		g1l.setFont(fontInstructions);
		b1l = new JLabel("B");
		b1l.setFont(fontInstructions);
		r2l = new JLabel("R");
		r2l.setFont(fontInstructions);
		g2l = new JLabel("G");
		g2l.setFont(fontInstructions);
		b2l = new JLabel("B");
		b2l.setFont(fontInstructions);

		txtNameEvent = new JTextField(30);
		txtNameEvent.setFont(fontInstructions);
		nameL = new JLabel("Event Name:");
		nameL.setFont(fontInstructions);

		//Menu Bar Init
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setFont(fontInstructions);

		newFile = new JMenuItem("New-This deletes all the events!!!");
		newFile.setFont(fontInstructions);
		open = new JMenuItem("Open");
		open.setFont(fontInstructions);
		save = new JMenuItem("Save");
		save.setFont(fontInstructions);

		fileMenu.add(newFile);
		fileMenu.add(open);
		fileMenu.add(save);
		menuBar.add(fileMenu);

		//Add Listeners
		rBanner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colourBannerBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue()))));
			}
		});
		gBanner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colourBannerBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue()))));
			}
		});
		bBanner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colourBannerBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue()))));
			}
		});
		rBackground.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colourBackgroundBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue()))));
			}
		});
		gBackground.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colourBackgroundBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue()))));
			}
		});
		bBackground.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colourBackgroundBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue()))));
			}
		});
		newFile.addActionListener(nf -> newFile());

		open.addActionListener(of-> openFileDialog());

		save.addActionListener(sf -> saveToFile());

		selectImageLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/CountdownClock/resources/images"));
				int result = jfc.showOpenDialog(frame);

				if(result == JFileChooser.APPROVE_OPTION){
					File f = jfc.getSelectedFile();
					String path = f.getAbsolutePath();
					if(path.substring(path.length() - 3).equals("png") || path.substring(path.length() - 3).equals("jpg")){
						imageLeft_path = f.getName();
						lblImageLeft.setText("Left Image: "+ imageLeft_path);
						ImageIcon leftImgIcon = marcyLogo;
						try {
							leftImgIcon = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/"+imageLeft_path)));
						}
						catch (IOException ioe) {
							System.out.println("Couldn't load the logo");
							System.err.println("Couldn't load the logo");
							ioe.printStackTrace();
						}  
						lblImgLeft.setIcon(new ImageIcon(getBufferedImage(leftImgIcon)));
						pack();
					}
				}
			}
		});

		selectImageRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/CountdownClock/resources/images"));
				int result = jfc.showOpenDialog(frame);

				if(result == JFileChooser.APPROVE_OPTION){
					File f = jfc.getSelectedFile();
					String path = f.getAbsolutePath();
					if(path.substring(path.length() - 3).equals("png") || path.substring(path.length() - 3).equals("jpg")){
						imageRight_path = f.getName();
						lblImageRight.setText("Right Image: "+ imageRight_path);
						ImageIcon rightImgIcon = marcyLogo;
						try {
							rightImgIcon = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/"+imageRight_path)));
						}
						catch (IOException ioe) {
							System.out.println("Couldn't load the logo");
							System.err.println("Couldn't load the logo");
							ioe.printStackTrace();
						}  
						lblImgRight.setIcon(new ImageIcon(getBufferedImage(rightImgIcon)));
						pack();
					}
				}
			}
		});


		addEvent.addActionListener(a -> addEvent());
		updateEvent.addActionListener(u -> updateEvent());
		removeEvent.addActionListener(r -> removeEvent());

		listModel = new DefaultListModel();
		listModel.addElement("New Event                             ");

		eventsSelection = new JList<String>(listModel);
		scrollAwardList = new JScrollPane(eventsSelection, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		eventsSelection.setFont(fontInstructions);
		eventsSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		eventsSelection.addListSelectionListener(this);
		eventsSelection.setSelectedIndex(0);

		//Adding Compenents
		JPanel r1p = new JPanel();
		JPanel g1p = new JPanel();
		JPanel b1p = new JPanel();
		r1p.add(r1l);
		r1p.add(rBanner);
		g1p.add(g1l);
		g1p.add(gBanner);
		b1p.add(b1l);
		b1p.add(bBanner);
		slidersBanner.add(r1p);
		slidersBanner.add(Box.createVerticalStrut(10));
		slidersBanner.add(g1p);
		slidersBanner.add(Box.createVerticalStrut(10));
		slidersBanner.add(b1p);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		TitledBorder  title = BorderFactory.createTitledBorder(blackline, "Banner Color");
		title.setTitleJustification(TitledBorder.LEFT);
		title.setTitleFont(fontInstructions);
		colourBannerPanel.setBorder(title);
		colourBannerPanel.add(slidersBanner);
		colourBannerPanel.add(colourBannerBlock);

		JPanel r2p = new JPanel();
		JPanel g2p = new JPanel();
		JPanel b2p = new JPanel();
		r2p.add(r2l);
		r2p.add(rBackground);
		g2p.add(g2l);
		g2p.add(gBackground);
		b2p.add(b2l);
		b2p.add(bBackground);
		slidersBackground.add(r2p);
		slidersBackground.add(Box.createVerticalStrut(10));
		slidersBackground.add(g2p);
		slidersBackground.add(Box.createVerticalStrut(10));
		slidersBackground.add(b2p);
		title = BorderFactory.createTitledBorder(blackline, "Backgroung Color");
		title.setTitleJustification(TitledBorder.LEFT);
		title.setTitleFont(fontInstructions);
		colourBackgroundPanel.setBorder(title);
		colourBackgroundPanel.add(slidersBackground);
		colourBackgroundPanel.add(colourBackgroundBlock);

		title = BorderFactory.createTitledBorder(blackline, "Select The Date");
		title.setTitleJustification(TitledBorder.LEFT);
		title.setTitleFont(fontInstructions);
		datePanel.setBorder(title);
		datePanel.setBackground(new Color(255, 255, 255));
		datePanel.add(calender);
		datePanel.add(hourBox);
		datePanel.add(minuteBox);

		butPanel.add(addEvent);
		butPanel.add(updateEvent);
		butPanel.add(removeEvent);

		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.insets = new Insets(20, 20, 20, 20);
		gc.anchor = GridBagConstraints.NORTHWEST;
		all.add(lblLogo1, gc);

		gc.gridx = 6;
		gc.anchor = GridBagConstraints.NORTHEAST;
		all.add(lblLogo2, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 3;
		gc.gridheight = 1;
		gc.anchor = GridBagConstraints.NORTH;
		all.add(txtInstructions, gc);

		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.insets = new Insets(20, 0, 0, 0);
		gc.anchor = GridBagConstraints.NORTHWEST;
		all.add(lblEvents, gc);

		gc.gridx = 2;
		gc.gridwidth = 2;
		gc.insets = new Insets(20, 20, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		all.add(nameL, gc);

		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridheight = 6;
		all.add(scrollAwardList, gc);

		gc.gridx = 2;
		gc.gridwidth = 2;
		gc.gridheight = 1;
		gc.insets = new Insets(0, 20, 0, 0);
		gc.fill = GridBagConstraints.NONE;
		all.add(txtNameEvent, gc);

		gc.gridy = 3;
		gc.gridwidth = 1;
		gc.insets = new Insets(20, 20, 0, 0);
		all.add(selectImageLeft, gc);

		gc.gridy = 4;
		gc.insets = new Insets(0, 20, 0, 0);
		all.add(lblImageLeft, gc);

		gc.gridy = 5;
		all.add(lblImgLeft, gc);

		gc.gridy = 3;
		gc.gridx = 3;
		gc.insets = new Insets(20, 20, 0, 0);
		all.add(selectImageRight, gc);

		gc.gridy = 4;
		gc.insets = new Insets(0, 20, 0, 0);
		all.add(lblImageRight, gc);

		gc.gridy = 5;
		all.add(lblImgRight, gc);

		gc.gridy =7;
		gc.gridx = 2;
		gc.gridwidth = 2;
		all.add(datePanel, gc);

		gc.gridy = 8;
		all.add(colourBannerPanel, gc);

		gc.gridy = 9;
		all.add(colourBackgroundPanel, gc);

		gc.gridy = 10;
		all.add(butPanel, gc);

		//Adding Main Components
		this.setTitle("COUNTDOWN CLOCK EDITOR");
		all.setBackground(new Color(255, 255,255));
		this.setJMenuBar(menuBar);
		this.add(all);
		this.pack();
		this.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(new Dimension(640, 480));
		//frame.setResizable(false);
		this.setLocationRelativeTo(null);
		readFromFile();
	}
	public BufferedImage getBufferedImage(Color c) {
		BufferedImage buffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffer.createGraphics();
		g.setColor(c);
		g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		g.dispose();

		return buffer;
	}

	public BufferedImage getBufferedImage(ImageIcon i) {
		BufferedImage buffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffer.createGraphics();
		g.drawImage(i.getImage(), 0, 0, buffer.getWidth(), buffer.getHeight(), this);
		g.dispose();

		return buffer;
	}

	public void newFile() {
		events.clear();
		String jsonFilePath = (new File(System.getProperty("user.home") + "/Desktop/CountdownClock/events.json")).getAbsolutePath();
		InputOutput.output(events, jsonFilePath);
		refreshList();
		clearForm();
		txtNameEvent.requestFocus();
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
		refreshList();
	}

	public void openFileDialog() {
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/CountdownClock/"));
		int result = jfc.showOpenDialog(frame);

		if(result == JFileChooser.APPROVE_OPTION){
			File f = jfc.getSelectedFile();
			String path = f.getAbsolutePath();
			if(path.substring(path.length() - 4).equals("json")){
				try {
					events.clear();
					events = InputOutput.input(jfc.getSelectedFile().getAbsolutePath());
				} 
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "INCORRECT FILE!!!", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		refreshList();
	}

	public void saveToFile() {
		String jsonFilePath = (new File(System.getProperty("user.home") + "/Desktop/CountdownClock/events.json")).getAbsolutePath();
		InputOutput.output(events, jsonFilePath);
	}

	public void clearForm() {
		System.out.println("clear form");
		//Clear Form
		lblImageLeft.setText("Left Image: ");
		lblImageRight.setText("Right Image: ");
		lblImgLeft.setIcon(new ImageIcon(getBufferedImage(marcyLogo)));
		lblImgRight.setIcon(new ImageIcon(getBufferedImage(marcyLogo)));
		txtNameEvent.setText("");
		calender.jp.getModel().setSelected(false);
		rBanner.setValue(255);
		gBanner.setValue(0);
		bBanner.setValue(0);
		bannerColor = new Color(255,0,0);
		colourBannerBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue()))));
		rBackground.setValue(255);
		gBackground.setValue(255);
		bBackground.setValue(255);
		backgroundColor = new Color(255,255,255);
		colourBackgroundBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue()))));
		hourBox.setSelectedIndex(0);
		minuteBox.setSelectedIndex(0);
	}

	//pulls from the database all the awards for a specific student and fills out the list model
	//invoked by many methods
	public void refreshList(){
		eventsSelection.removeListSelectionListener(this);
		listModel.clear();
		listModel.addElement("New Event                        ");  
		for (int i=0; i<events.size();i++) {
			listModel.addElement(events.get(i).getName());
		}	 
		eventsSelection.setModel(listModel);
		eventsSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		eventsSelection.addListSelectionListener(this);
		eventsSelection.setSelectedIndex(0);
		pack();
	}

	//valueChanged event in JList - update the fields with info from the student selectd
	//clear the field if New Award is selected
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("val changed");
		if (!e.getValueIsAdjusting()) {
			JList list = (JList) e.getSource();
			int selections[] = list.getSelectedIndices();
			int index = selections[0];
			//if new award was selected, reset the textfields, and disable btnEdit, btnDelete
			if (index == 0) { //new award
				clearForm();
				txtNameEvent.requestFocus();
				removeEvent.setEnabled(false);
				updateEvent.setEnabled(false);
				addEvent.setEnabled(true);
			}
			//update the textFields with appropriate info, and disable btnAdd
			else {	
				indexEvent = index-1;
				eventsSelection.requestFocus();
				txtNameEvent.setText(events.get(index-1).getName());
				calender.jp.getModel().setSelected(true);
				calender.jp.getModel().setDay(events.get(index-1).getDay());
				calender.jp.getModel().setMonth(events.get(index-1).getMonth() - 1);
				calender.jp.getModel().setYear(events.get(index-1).getYear());

				bannerColor = new Color(events.get(index-1).getBannerColour().getRGB());
				rBanner.setValue(bannerColor.getRed());
				gBanner.setValue(bannerColor.getGreen());
				bBanner.setValue(bannerColor.getBlue());
				colourBannerBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue()))));

				backgroundColor = new Color(events.get(index-1).getBackgroundColour().getRGB());
				rBackground.setValue(backgroundColor.getRed());
				gBackground.setValue(backgroundColor.getGreen());
				bBackground.setValue(backgroundColor.getBlue());
				colourBackgroundBlock.setIcon(new ImageIcon(getBufferedImage(new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue()))));

				hourBox.setSelectedItem("" + events.get(index-1).getHour());
				minuteBox.setSelectedItem("" + events.get(index-1).getMinute());
				if(events.get(index-1).getImageLeft() != null) {
					ImageIcon imgL=marcyLogo;
					lblImageLeft.setText("Left Image: " + events.get(index-1).getImageLeft());
					imageLeft_path = events.get(index-1).getImageLeft();
					lblImageLeft.setText("Left Image: "+ imageLeft_path);
					ImageIcon leftImgIcon = marcyLogo;
					try {
						leftImgIcon = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/"+imageLeft_path)));
					}
					catch (IOException ioe) {
						System.out.println("Couldn't load the logo");
						System.err.println("Couldn't load the logo");
						ioe.printStackTrace();
					}  
					lblImgLeft.setIcon(new ImageIcon(getBufferedImage(leftImgIcon)));
				}
				if(events.get(index-1).getImageRight() != null) {
					ImageIcon imgR=marcyLogo;
					imageRight_path = events.get(index-1).getImageRight();
					lblImageRight.setText("Right Image: "+ imageRight_path);
					ImageIcon rightImgIcon = marcyLogo;
					try {
						rightImgIcon = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/"+imageRight_path)));
					}
					catch (IOException ioe) {
						System.out.println("Couldn't load the logo");
						System.err.println("Couldn't load the logo");
						ioe.printStackTrace();
					}  
					lblImgRight.setIcon(new ImageIcon(getBufferedImage(rightImgIcon)));
				}
				this.pack();
				removeEvent.setEnabled(true);
				updateEvent.setEnabled(true);
				addEvent.setEnabled(false);
			}
		}
	}

	public void addEvent() {
		if (txtNameEvent.getText().trim().length()>50) {//check if the event name has more than 50 characters
			JOptionPane.showMessageDialog(null, "The event description can have maximum 50 characters!!!", "WARNING",
					JOptionPane.INFORMATION_MESSAGE);
			txtNameEvent.requestFocus();
			return;
		}
		if (txtNameEvent.getText().trim().length()==0) {//check if the first name has more than 50 characters
			JOptionPane.showMessageDialog(null, "The event description cannot be empty!!!", "WARNING",
					JOptionPane.INFORMATION_MESSAGE);
			txtNameEvent.requestFocus();
			return;
		}
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getName().equals(txtNameEvent.getText().trim())) {
				JOptionPane.showMessageDialog(null, "An event with this descrition already exists!!!", "WARNING",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		Event newEvent = new Event (txtNameEvent.getText().trim());
		newEvent.setImageLeft(imageLeft_path);
		newEvent.setImageRight(imageRight_path);

		Date selectedDate = (Date) calender.jp.getModel().getValue();
		String s = selectedDate.toString();
		int[] formatted_date = getDate(s);
		newEvent.setDay(formatted_date[0]);
		newEvent.setMonth(formatted_date[1]);
		newEvent.setYear(formatted_date[2]);
		newEvent.setHour(Integer.parseInt((String)hourBox.getSelectedItem()));
		newEvent.setMinute(Integer.parseInt((String)minuteBox.getSelectedItem()));

		bannerColor = new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue());
		backgroundColor = new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue());

		newEvent.setBannerColour(bannerColor.getRed(), bannerColor.getGreen(), bannerColor.getBlue());
		newEvent.setBackgroundColour(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
		events.add(newEvent);
		String jsonFilePath = (new File(System.getProperty("user.home") + "/Desktop/CountdownClock/events.json")).getAbsolutePath();

		InputOutput.output(events, jsonFilePath);
		refreshList();
	}
	
	public void updateEvent() {
		if (txtNameEvent.getText().trim().length()>50) {//check if the event name has more than 50 characters
			JOptionPane.showMessageDialog(null, "The event description can have maximum 50 characters!!!", "WARNING",
					JOptionPane.INFORMATION_MESSAGE);
			txtNameEvent.requestFocus();
			return;
		}
		if (txtNameEvent.getText().trim().length()==0) {//check if the first name has more than 50 characters
			JOptionPane.showMessageDialog(null, "The event description cannot be empty!!!", "WARNING",
					JOptionPane.INFORMATION_MESSAGE);
			txtNameEvent.requestFocus();
			return;
		}
		Event newEvent = new Event (txtNameEvent.getText().trim());
		newEvent.setImageLeft(imageLeft_path);
		newEvent.setImageRight(imageRight_path);

		Date selectedDate = (Date) calender.jp.getModel().getValue();
		String s = selectedDate.toString();
		int[] formatted_date = getDate(s);
		newEvent.setDay(formatted_date[0]);
		newEvent.setMonth(formatted_date[1]);
		newEvent.setYear(formatted_date[2]);
		newEvent.setHour(Integer.parseInt((String)hourBox.getSelectedItem()));
		newEvent.setMinute(Integer.parseInt((String)minuteBox.getSelectedItem()));

		bannerColor = new Color(rBanner.getValue(), gBanner.getValue(), bBanner.getValue());
		backgroundColor = new Color(rBackground.getValue(), gBackground.getValue(), bBackground.getValue());

		newEvent.setBannerColour(bannerColor.getRed(), bannerColor.getGreen(), bannerColor.getBlue());
		newEvent.setBackgroundColour(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
		events.set(indexEvent, newEvent);
		String jsonFilePath = (new File(System.getProperty("user.home") + "/Desktop/CountdownClock/events.json")).getAbsolutePath();
		InputOutput.output(events, jsonFilePath);
		refreshList();
		System.out.println("index"+(indexEvent+1));
		eventsSelection.setSelectedIndex(indexEvent+1);
	}

	public void removeEvent() {
		events.remove(indexEvent);
		String jsonFilePath = (new File(System.getProperty("user.home") + "/Desktop/CountdownClock/events.json")).getAbsolutePath();
		InputOutput.output(events, jsonFilePath);
		refreshList();
	}

	public int[] getDate(String s){
		//Thu Jun 08 10:02:42 EDT 2017

		//0 - Day
		//1 - Month
		//2 - Year
		int[] date = new int[3];

		date[0] = Integer.parseInt(s.substring(8, 10));

		String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

		for(int i = 0; i < months.length; i++){
			if(s.substring(4, 7).equals(months[i])){
				date[1] = i + 1;
				break;
			}
		}

		date[2] = Integer.parseInt(s.substring(24));
		return date;
	}
}