package gui;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.Event;
import data.EventsList;

public class InputOutput{

	public static void output(ArrayList<Event> events, String path){
		PrintStream out;
		try {
			out = new PrintStream(new File(path));
			out.println("{");
			for(int i = 0; i < events.size(); i++){
				Event e = events.get(i);
				out.println("\"event" + i + "\": {");
				out.println("\"event_name\": \"" + e.getName() + "\",");
				out.println("\"event_image1\": \"" + e.getImageLeft() + "\",");
				out.println("\"event_image2\": \"" + e.getImageRight() + "\",");
				out.println("\"event_year\": \"" + e.getYear() + "\",");
				out.println("\"event_month\": \"" + e.getMonth() + "\",");
				out.println("\"event_day\": \"" + e.getDay() + "\",");
				out.println("\"event_hour\": \"" + e.getHour() + "\",");
				out.println("\"event_minute\": \"" + e.getMinute() + "\",");
				out.println("\"background_colour\": \"#" + e.getBackground() + "\",");
				out.println("\"banner_colour\": \"#" + e.getBanner() + "\"");
				out.print("}");
				if(i != events.size() - 1)
					out.println(",");
				else out.println("");
			}

			out.println("}");
			out.close();
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR WRITING TO FILE!!!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
}

public static ArrayList<Event> input(String path) throws IOException{
	//9 Lines Event
	ArrayList<Event> events = new ArrayList<Event>();
	BufferedReader in = new BufferedReader(new FileReader(path));
	in.readLine();
	while(!(in.readLine().equals("}"))){
		String lineIn = in.readLine();
		String e_name = getValue(lineIn.substring(lineIn.indexOf(":")+3));
		Event e = new Event(e_name);
		lineIn = in.readLine();
		e.setImageLeft(getValue(lineIn.substring(lineIn.indexOf(":")+3)));
		lineIn = in.readLine();
		e.setImageRight(getValue(lineIn.substring(lineIn.indexOf(":")+3)));
		lineIn = in.readLine();
		e.setYear(Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+3))));
		lineIn = in.readLine();
		e.setMonth(Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+3))));
		lineIn = in.readLine();
		e.setDay(Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+3))));
		lineIn = in.readLine();
		e.setHour(Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+3))));
		lineIn = in.readLine();
		e.setMinute(Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+3))));
		lineIn = in.readLine();
		int r, g ,b;
		int col = Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+4)), 16);
		r = (col & 0xFF0000) >> 16;
			g = (col & 0x00FF00) >> 8;
			b = col & 0x0000FF;

			e.setBackgroundColour(r, g, b);
			lineIn = in.readLine();
			col = Integer.parseInt(getValue(lineIn.substring(lineIn.indexOf(":")+4)), 16);
			r = (col & 0xFF0000) >> 16;
			g = (col & 0x00FF00) >> 8;
			//g = (col & 0x00FF00) >> 8;
			b = col & 0x0000FF;

			e.setBannerColour(r, g, b);

			lineIn = in.readLine();
			events.add(e);
	}       
	return events;
}

private static String getValue(String s){
	int end = 0;

	for(int i = 0; i < s.length(); i++){
		if(s.charAt(i) == '\"') {
			end = i;
			break;
		}
	}
	return s.substring(0, end);
}
}
