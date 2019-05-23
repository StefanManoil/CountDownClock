package data;
import java.awt.*;

public class Event {
    private String name;
    private String imageLeft;
    private String imageRigth;

    private int year, month, day;
    private String backgroundColour;
    private String bannerColour;

    private int hour, minute;
   /**Constructor
    * default background_colour is white, default banner_colour is red,
    * and the default images are MARCL.png
    * @param name - the name of the event
    */
    public Event(String name){
        this.name = name;
        backgroundColour = "#ffffff";
        bannerColour = "#ff0000";
        imageLeft = "MARCL.png";
        imageRigth = "MARCL.png";
    }
    
   /**name getter
    * @param none
    * @return - the name of the event
    */
    public String getName() {
        return name;
    }
    
   /**name setter
    * @param name - the new event name
    * @return void
    */
    public void setName(String name) {
        this.name = name;
    }
    
   /**imageLeft getter
    * @param none
    * @return - the name of the image on the left
    */
    public String getImageLeft() {
        return imageLeft;
    }
    
   /**imageRight getter
    * @param none
    * @return - the name of the image on the right
    */
    public String getImageRight() {
        return imageRigth;
    }
    
   /**imageLeft setter
    * @param imageName - the name of the image on the left
    * @return none
    */    
    public void setImageLeft(String imageName) {
        this.imageLeft = imageName;
    }
    
   /**imageRight setter
    * @param imageName - the name of the image on the right
    * @return none
    */
    public void setImageRight(String imageName) {
        this.imageRigth = imageName;
    }
    
   /**year getter
    * @param none
    * @return - the year as an integer miliseconds
    */   
    public int getYear() {
        return year;
    }
    
   /**year setter
    * @param year - the no of miliseconds
    * @return none
    */
    public void setYear(int year) {
        this.year = year;
    }
    
   /**month getter
    * @param none
    * @return - the month as an integer miliseconds
    */ 
    public int getMonth() {
        return month;
    }
    
   /**month setter
    * @param month - the no of miliseconds
    * @return none
    */
    public void setMonth(int month) {
        this.month = month;
    }
    
    /**day getter
     * @param none
     * @return - the day as an integer miliseconds
     */ 
    public int getDay() {
        return day;
    }
    
   /**day setter
    * @param day - the no of miliseconds
    * @return none
    */
    public void setDay(int day) {
        this.day = day;
    }
    
   /**hour getter
   * @param none
   * @return - the hour as an integer miliseconds
   */   
    public int getHour(){
        return hour;
    }

    /**hour setter
     * @param hour - the no of miliseconds
     * @return none
     */
    public void setHour(int hour){
        this.hour = hour;
    }
    
    /**minute getter
     * @param none
     * @return - the minute as an integer miliseconds
     */  
    public int getMinute(){
        return minute;
    }
    
    /**minute setter
     * @param minute - the no of miliseconds
     * @return none
     */   
    public void setMinute(int minute){
        this.minute = minute;
    }

    /**backgroundColour getter
     * @param none
     * @return - the backgroundColour as a Color object
     */ 
    public Color getBackgroundColour() {
        return new Color(Integer.parseInt(backgroundColour, 16));
    }
    
   /**backgroundColour getter
    * @param none
    * @return - the background color as a hexadecimal String
    */ 
    public String getBackground() {
        return backgroundColour;
    }

   /**backgroundColour setter
    * @param bc - a hexadecimal String color
    * @return none
    */ 
    public void setBackground(String bc) {
        this.backgroundColour = bc;
    }
    
   /**backgroundColour setter
    * @param r, g, b  - three integer values - the RGB for backgroundColour
    * @return none
    */ 
    public void setBackgroundColour(int r, int g, int b) {
        this.backgroundColour = Integer.toHexString((r << 16) | (g << 8) | b);
        if(this.backgroundColour.length() != 6){
            int a = 6 - backgroundColour.length();
            String out = "";

            for(int i = 0; i < a; i++){
                out += "0";
            }

            out += this.backgroundColour;
            this.backgroundColour = out;
        }
    }
    
   /**bannerColour getter
    * @param none
    * @return - the background color as a hexadecimal String
    */ 
    public String getBanner(){
        return bannerColour;
    }
    
    /**bannerColour getter
     * @param none
     * @return - the background color as a Color object
     */ 
    public Color getBannerColour() {
        return new Color(Integer.parseInt(bannerColour, 16));
    }

    /**bannerColour setter
     * @param r, g, b  - three integer values - the RGB for bannerColour
     * @return none
     */     public void setBannerColour(int r, int g, int b) {
        this.bannerColour = Integer.toHexString((r << 16) | (g << 8) | b);
        if(this.bannerColour.length() != 6){
            int a = 6 - bannerColour.length();
            String out = "";

            for(int i = 0; i < a; i++){
                out += "0";
            }

            out += this.bannerColour;
            this.bannerColour = out;
        }
    }
 
    /** toString returns a String representation of the Event object - for debugging purpose
     * @see java.lang.Object#toString()
     */
    public String toString (){
      return "Event "+name+" image1 "+ imageLeft + " image2 "+ imageRigth+ "\n year " + year + " month "+ month + " day "+ day + " background_colour " + backgroundColour+ " banner_colour "+ bannerColour;
    }
}
