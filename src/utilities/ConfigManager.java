package utilities;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Manoil (September 2017)
 * Manages configurable settings form a properties file located on Desktop/CountdownClock/ini.properties
 * eventNameSize, clockSize, hourMinSecSize. Adjust as necessary 
 *
 */
public class ConfigManager {

    public static int eventNameSize = 0;
    public static int clockSize = 0;
    public static int hourMinSecSize = 0;
    public static int transitionTime = 0;
    public static Color bannerColor;
    public static Color backgroundColor;
   
    // Prevent instatiation
    private ConfigManager() {
        throw new AssertionError();
    }

    public static void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            File file = new File(System.getProperty("user.home"), "/Desktop/CountdownClock/ini.properties");
            file.mkdirs();
            FileReader fileReader = new FileReader(file);

            //load a properties file from class path, inside static method
            prop.load(fileReader);
            try {
            	eventNameSize = Integer.valueOf(prop.getProperty("eventNameSize"));
            	clockSize = Integer.valueOf(prop.getProperty("clockSize"));
            	hourMinSecSize = Integer.valueOf(prop.getProperty("hourMinSecSize"));
                transitionTime = Integer.valueOf(prop.getProperty("transitionTime"));
                String[] values = prop.getProperty("bannerColor").replaceAll(",", "").split(" ");
                bannerColor = new Color(Integer.valueOf(values[0]), Integer.valueOf(values[1]), Integer.valueOf(values[2]));
                values = prop.getProperty("backgroundColor").replaceAll(",", "").split(" ");
                backgroundColor = new Color(Integer.valueOf(values[0]), Integer.valueOf(values[1]), Integer.valueOf(values[2]));
            } catch(NumberFormatException e) {
                System.out.println("Error in number format in resolution definition in ini.properties");
                e.printStackTrace();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void initiateProperties() {
        File file = new File(System.getProperty("user.home"), "/Desktop/CountdownClock/ini.properties");
        if(file.exists()) return;
        File folder = new File(System.getProperty("user.home"), "/Desktop/CountdownClock");
        folder.mkdir();// the awards folder needs to be created before the file ini.properties is created
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            // set the properties value
            prop.setProperty("eventNameSize", "60");
            prop.setProperty("clockSize", "100");
            prop.setProperty("hourMinSecSize", "40");
            prop.setProperty("transitionTime", "10");
            prop.setProperty("bannerColor", "255, 0, 0");
            prop.setProperty("backgroundColor", "255, 0, 0");
            prop.store(output, null);
        } catch(IOException io) {
            io.printStackTrace();
        } finally {
            if(output != null) {
                try {
                    output.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
