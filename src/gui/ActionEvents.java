package gui;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import data.Event;

public class ActionEvents {
    private JFileChooser jfc;
    private JFrame frame;
    private UI ui;

    private boolean eventBeingAdded = false;
    private boolean eventBeingRemoved = false;

    private ArrayList<Timer> timers = new ArrayList<Timer>();

    public ActionEvents(JFrame frame, UI ui){
        this.frame = frame;
        this.ui = ui;
        jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
    }

   /* public void event(ActionEvent e){

        //Open Command
        if(e.getActionCommand().equals("open")) {
            jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            int result = jfc.showOpenDialog(frame);

            if(result == JFileChooser.APPROVE_OPTION){
                File f = jfc.getSelectedFile();
                System.out.println(f.toString());
                String path = f.getAbsolutePath();
                if(path.substring(path.length() - 4).equals("json")){
                  //System.out.println("in if");
                    try {
                        ui.eventsList.events.clear();
                        InputOutput.input(ui.eventsList, jfc.getSelectedFile().getAbsolutePath());

                        ui.statusLabel.setText("File Opened.");
                        ui.statusLabel.setVisible(true);
                        Timer t = new Timer();

                        t.schedule(new TimerTask() {
                            public void run() {
                                ui.statusLabel.setVisible(false);
                            }
                        }, 2000);

                        reload_Event_File();
                    } catch (Exception ex) {}
                }
            }
        }

        else if(e.getActionCommand().equals("save")){
            if(ui.eventsList.events.size() != 0){
                jfc = new JFileChooser();
                jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
                int result = jfc.showSaveDialog(frame);

                if(result == JFileChooser.APPROVE_OPTION){
                    try {
                        InputOutput.output(ui.eventsList, jfc.getSelectedFile().getAbsolutePath());

                        ui.statusLabel.setText("File Saved.");
                        ui.statusLabel.setVisible(true);
                        Timer t = new Timer();

                        t.schedule(new TimerTask() {
                            public void run() {
                                ui.statusLabel.setVisible(false);
                            }
                        }, 2000);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }

        //New File Command
        else if(e.getActionCommand().equals("new_file")){
            eventBeingRemoved = true;
            ui.eventsBox.removeAllItems();
            ui.eventsList.events.clear();

            //Clear Form
            ui.selectImage1.setText("Select Left Image");
            ui.selectImage2.setText("Select Right Image");
            ui.nameField.setText("");
            ui.calender.jp.getModel().setSelected(false);
            ui.r.setValue(0);
            ui.g.setValue(0);
            ui.b.setValue(0);
            ui.colors[0] = new Color(0,0,0);
            ui.colors[1] = new Color(0,0,0);
            ui.colourBox.setSelectedIndex(0);
            ui.colourBlock.setIcon(new ImageIcon(ui.getBufferedImage(new Color(0,0,0))));
            ui.hourBox.setSelectedIndex(0);
            ui.minuteBox.setSelectedIndex(0);

            ui.statusLabel.setText("New File.");
            ui.statusLabel.setVisible(true);
            Timer t = new Timer();

            t.schedule(new TimerTask() {
                public void run() {
                    ui.statusLabel.setVisible(false);
                }
            }, 2000);
        }

        //Select Image1 Command
        else if(e.getActionCommand().equals("select_image1")) {
            jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            int result = jfc.showOpenDialog(frame);

            if(result == JFileChooser.APPROVE_OPTION){
                File f = jfc.getSelectedFile();
                String path = f.getAbsolutePath();
                if(path.substring(path.length() - 3).equals("png") || path.substring(path.length() - 3).equals("jpg")){
                    ui.image1_path = f.getName();
                }

                ui.selectImage1.setText(f.getName());
            }
        }
        
        //Select Image2 Command
        else if(e.getActionCommand().equals("select_image2")) {
            jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            int result = jfc.showOpenDialog(frame);

            if(result == JFileChooser.APPROVE_OPTION){
                File f = jfc.getSelectedFile();
                String path = f.getAbsolutePath();
                if(path.substring(path.length() - 3).equals("png") || path.substring(path.length() - 3).equals("jpg")){
                    ui.image2_path = f.getName();
                }

                ui.selectImage2.setText(f.getName());
            }
        }


        //Add Event Command
        else if(e.getActionCommand().equals("add_event")) {
            if (checkCompletion()) {
                boolean eventAdded = ui.eventsList.addEvent(ui.nameField.getText());
                Event event = ui.eventsList.lookFor(ui.nameField.getText());

                //Event Just Added
                if (eventAdded) {
                    eventBeingAdded = true;
                    addEvent(event);

                    ui.statusLabel.setText("Event Added.");
                    ui.statusLabel.setVisible(true);
                    Timer t = new Timer();

                    t.schedule(new TimerTask() {
                        public void run() {
                            ui.statusLabel.setVisible(false);
                        }
                    }, 2000);
                }
            }
        }

        //Change Event Command
        else if(e.getActionCommand().equals("change_event")){
            JComboBox comboBox = (JComboBox)e.getSource();

            switch_Event(ui.eventsList.lookFor((String)comboBox.getSelectedItem()));
        }

        //Select Colour Command
        else if(e.getActionCommand().equals("select_colour")){
            ui.colors[ui.colourBox.getSelectedIndex() ^ 1] = new Color(ui.r.getValue(), ui.g.getValue(), ui.b.getValue());

            ui.r.setValue(ui.colors[ui.colourBox.getSelectedIndex()].getRed());
            ui.g.setValue(ui.colors[ui.colourBox.getSelectedIndex()].getGreen());
            ui.b.setValue(ui.colors[ui.colourBox.getSelectedIndex()].getBlue());
        }

        //Update Event Command
        else if(e.getActionCommand().equals("update_event")){
            //Event event = ui.eventsList.lookFor(ui.nameField.getText());
        	//!!! changing the event name would cause lookFor to return null.
            Event event = ui.eventsList.lookFor((String)ui.eventsBox.getSelectedItem());
            System.out.println("update " + event);
            if(event != null) {
                update_Event(event);

                ui.statusLabel.setText("Event Updated.");
                ui.statusLabel.setVisible(true);
                Timer t = new Timer();

                t.schedule(new TimerTask() {
                    public void run() {
                        ui.statusLabel.setVisible(false);
                    }
                }, 2000);
            }
        }

        //Remove Event Command
        else if(e.getActionCommand().equals("remove_event")){
            int index = ui.eventsList.indexOfEvent(ui.nameField.getText());

            if(index != -1){
                ui.eventsList.events.remove(index);
                ui.nameField.setText("");

                ui.statusLabel.setText("Event Remove.");
                ui.statusLabel.setVisible(true);
                Timer t = new Timer();

                eventBeingRemoved = true;
                ui.eventsBox.removeItemAt(index);

                t.schedule(new TimerTask() {
                    public void run() {
                        ui.statusLabel.setVisible(false);
                    }
                }, 2000);

                //Reset Form
                if(ui.eventsList.events.size() == 0) {
                    ui.selectImage1.setText("Select Image Left");
                    ui.selectImage2.setText("Select Image Right");
                    ui.image1_path = "";
                    ui.image2_path = "";

                    ui.nameField.setText("");
                    ui.colors[0] = new Color(0, 0, 0);
                    ui.colors[1] = new Color(0, 0, 0);
                    ui.r.setValue(0);
                    ui.g.setValue(0);
                    ui.b.setValue(0);
                    ui.colourBlock.setIcon(new ImageIcon(ui.getBufferedImage(new Color(0, 0, 0))));
                    ui.calender.jp.getModel().setSelected(false);
                    ui.hourBox.setSelectedIndex(0);
                    ui.minuteBox.setSelectedIndex(0);
                }

                //load data from next event
                else {
                    Event event = ui.eventsList.events.get(0);

                    switch_Event(event);
                }
            }
        }
    }
    
    public void listEvents(ListSelectionEvent e){
    	if (!e.getValueIsAdjusting()) {
	          JList list = (JList) e.getSource();
	          int selections[] = list.getSelectedIndices();
	          int index = selections[0];
			 //if new award was selected, reset the textfields, and disable btnEdit, btnDelete
			 
			 /*if (index == 0) { //new event
				 txtAward.setText("");
				 txtAward.requestFocus();
				 txtDescription.setText("");
				 chkYBY.setSelected(false);
				 btnEdit.setEnabled(false);
				 btnDelete.setEnabled(false);
				 btnAdd.setEnabled(true);
			 }
			 //update the textFields with appropriate info, and disable btnAdd
			 else {
				 txtAward.setText(awardsInfo.get(listModel.get(index)).getAward());
				 //txtAward.setText(awardsInfo.get(awardsKeys[index]).getAward());
				 txtDescription.setText(awardsInfo.get(listModel.get(index)).getDescription()); 
				 //txtDescription.setText(awardsInfo.get(awardsKeys[index]).getDescription());            
				 chkYBY.setSelected(awardsInfo.get(listModel.get(index)).isYearByYear());
				 //chkYBY.setSelected(awardsInfo.get(awardsKeys[index]).isYearByYear());
				 btnAdd.setEnabled(false);
				 btnEdit.setEnabled(true);
				 btnDelete.setEnabled(true);
			 }
	        }
    }

    public void reload_Event_File(){
        //Reset Form
        ui.eventsBox.removeAllItems();
        ui.nameField.setText("");

        ui.selectImage1.setText("Select Image Left");
        ui.selectImage2.setText("Select Image Right");
        ui.image1_path = "";
        ui.image2_path = "";

        ui.colors[0] = new Color(0, 0, 0);
        ui.colors[1] = new Color(0, 0, 0);
        ui.r.setValue(0);
        ui.g.setValue(0);
        ui.b.setValue(0);
        ui.colourBlock.setIcon(new ImageIcon(ui.getBufferedImage(new Color(0, 0, 0))));
        ui.calender.jp.getModel().setSelected(false);
        ui.colourBox.setSelectedIndex(0);
        ui.hourBox.setSelectedIndex(0);
        ui.minuteBox.setSelectedIndex(0);

        //Add Events To Events Box
        for(int i = 0; i < ui.eventsList.events.size(); i++)
            ui.eventsBox.addItem(ui.eventsList.events.get(i).getName());

        //Select First Item
        reload_Event(ui.eventsList.events.get(0));

        eventBeingRemoved = false;
        eventBeingAdded = false;
    }

    //Update Existing Event
    public void update_Event(Event e){
        e.setName(ui.nameField.getText());
 
        e.setImageLeft(ui.image1_path);
        e.setImageRight(ui.image2_path);

        Date selectedDate = (Date) ui.calender.jp.getModel().getValue();
        String s = selectedDate.toString();
        int[] formatted_date = getDate(s);

        e.setDay(formatted_date[0]);
        e.setMonth(formatted_date[1]);
        e.setYear(formatted_date[2]);
        e.setHour(Integer.parseInt((String)ui.hourBox.getSelectedItem()));
        e.setMinute(Integer.parseInt((String)ui.minuteBox.getSelectedItem()));

        ui.colors[ui.colourBox.getSelectedIndex()] = new Color(ui.r.getValue(), ui.g.getValue(), ui.b.getValue());
        Color other = ui.colors[ui.colourBox.getSelectedIndex() ^ 1];

        if(ui.colourBox.getSelectedIndex() == 0) {
            e.setBackgroundColour(ui.colors[0].getRed(), ui.colors[0].getGreen(), ui.colors[0].getBlue());
            e.setBannerColour(other.getRed(), other.getGreen(), other.getBlue());
        }
        else{
            e.setBackgroundColour(other.getRed(), other.getGreen(), other.getBlue());
            e.setBannerColour(ui.colors[1].getRed(), ui.colors[1].getGreen(), ui.colors[1].getBlue());
        }
    }

    //Populate Form With Event Data
    public void switch_Event(Event e){
        if(!eventBeingAdded && !eventBeingRemoved && e != null) {
            ui.nameField.setText(e.getName());
            if(e.getImageLeft() != null) {
              ui.selectImage1.setText(e.getImageLeft());//changing the label, not ui.image_path, ui.image_path still contains the previous image
              ui.image1_path = e.getImageLeft();//Manoil added this line because an old event after just switch, 
              //without any image selection will save with the previous image. This happens because update_event is 
              // using e.setImage_path(ui.image_path); and ui.image_path has not been set, just the label text was changed
            }
            else {
              ui.selectImage1.setText("Select Image Left");
            }
            
            if(e.getImageRight() != null) {
              ui.selectImage2.setText(e.getImageRight());//changing the label, not ui.image_path, ui.image_path still contains the previous image
              ui.image2_path = e.getImageRight();//Manoil added this line because an old event after just switch, 
              //without any image selection will save with the previous image. This happens because update_event is 
              // using e.setImage_path(ui.image_path); and ui.image_path has not been set, just the label text was changed
            }
            else {
              ui.selectImage2.setText("Select Image Right");
            }
            ui.calender.jp.getModel().setSelected(true);
            ui.calender.jp.getModel().setDay(e.getDay());
            ui.calender.jp.getModel().setMonth(e.getMonth() - 1);
            ui.calender.jp.getModel().setYear(e.getYear());

            ui.colourBox.setSelectedIndex(0);

            ui.colors[0] = new Color(e.getBackgroundColour().getRGB());
            ui.colors[1] = new Color(e.getBannerColour().getRGB());

            ui.colourBlock.setIcon(new ImageIcon(ui.getBufferedImage(new Color(ui.r.getValue(), ui.g.getValue(), ui.b.getValue()))));

            ui.r.setValue(ui.colors[0].getRed());
            ui.g.setValue(ui.colors[0].getGreen());
            ui.b.setValue(ui.colors[0].getBlue());

            ui.hourBox.setSelectedItem("" + e.getHour());
            ui.minuteBox.setSelectedItem("" + e.getMinute());
        }

        eventBeingAdded = false;
        eventBeingRemoved = false;
    }

    public void reload_Event(Event e){
        ui.nameField.setText(e.getName());
       
        if(e.getImageLeft() != null) ui.selectImage1.setText(e.getImageLeft().substring(13));
        else ui.selectImage1.setText("Select Image Left");
        
        if(e.getImageRight() != null) ui.selectImage2.setText(e.getImageRight().substring(13));
        else ui.selectImage2.setText("Select Image Right");

        ui.calender.jp.getModel().setSelected(true);
        ui.calender.jp.getModel().setDay(e.getDay());
        ui.calender.jp.getModel().setMonth(e.getMonth() - 1);
        ui.calender.jp.getModel().setYear(e.getYear());

        ui.colourBox.setSelectedIndex(0);

        ui.colors[0] = new Color(e.getBackgroundColour().getRGB());
        ui.colors[1] = new Color(e.getBannerColour().getRGB());

        ui.colourBlock.setIcon(new ImageIcon(ui.getBufferedImage(new Color(ui.colors[0].getRGB()))));

        ui.r.setValue(ui.colors[0].getRed());
        ui.g.setValue(ui.colors[0].getGreen());
        ui.b.setValue(ui.colors[0].getBlue());

        eventBeingAdded = false;
        eventBeingRemoved = false;
    }

    //Populate Event With Data
    public void addEvent(Event e){
        e.setImageLeft(ui.image1_path);
        e.setImageRight(ui.image2_path);

        Date selectedDate = (Date) ui.calender.jp.getModel().getValue();
        String s = selectedDate.toString();
        int[] formatted_date = getDate(s);

        e.setDay(formatted_date[0]);
        e.setMonth(formatted_date[1]);
        e.setYear(formatted_date[2]);

        e.setHour(Integer.parseInt((String)ui.hourBox.getSelectedItem()));
        e.setMinute(Integer.parseInt((String)ui.minuteBox.getSelectedItem()));

        ui.colors[ui.colourBox.getSelectedIndex()] = new Color(ui.r.getValue(), ui.g.getValue(), ui.b.getValue());
        Color other = ui.colors[ui.colourBox.getSelectedIndex() ^ 1];

        if(ui.colourBox.getSelectedIndex() == 0) {
            e.setBackgroundColour(ui.colors[0].getRed(), ui.colors[0].getGreen(), ui.colors[0].getBlue());
            e.setBannerColour(other.getRed(), other.getGreen(), other.getBlue());
        }
        else{
            e.setBackgroundColour(other.getRed(), other.getGreen(), other.getBlue());
            e.setBannerColour(ui.colors[1].getRed(), ui.colors[1].getGreen(), ui.colors[1].getBlue());
        }

        ui.eventsBox.addItem(e.getName());

        ui.eventsBox.setSelectedIndex(ui.eventsList.indexOfEvent(e.getName()));
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

    //Check If Basic Requirements For Event Is Met
    public boolean checkCompletion(){
        if(!ui.nameField.getText().equals("") && ui.calender.jp.getModel().getValue() != null)
            return true;
        return false;
    }
    */
}