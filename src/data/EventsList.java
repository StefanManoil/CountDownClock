package data;
import java.util.ArrayList;

public class EventsList {
    public ArrayList<Event> events = new ArrayList<Event>();

    /**returns a specific event or null if the event cannot be found in the list
     * @param: event_name - the String event_name used in the search
     * @return - the event index in the list of events, or -1
     */
     public Event lookFor(String event_name){
         for(int i = 0; i < events.size(); i++){
             if(events.get(i).getName().equals(event_name))
                 return events.get(i);
         }

         return null;
     }
     
    /**returns the index on a specific event or -1 if the event cannot be found in the list
     * @param  event_name - the String event_name used in the search
     * @return - the event index in the list of events, or -1
     */
     public int indexOfEvent(String event_name){
         for(int i = 0; i < events.size(); i++)
             if(events.get(i).getName().equals(event_name))
                 return i;

         return -1;
     }
     
   /**returns a specific event or null if the event cannot be found in the list
    * @param: event_name - the String event_name used in the search
    * @return - true if the event has not been found in the list and it was added, false otherwise
    */
    public boolean addEvent(String name) {
        if(lookFor(name) == null) {
            events.add(new Event(name));
            return true;
        }
        return false;
    }
    
   /**returns the names of all the events in the list as an array of Strings
    * @param none
    * @return a String[] containing all the events' names
    */
    public String[] getNames(){
        String[] names = new String[events.size()];

        for(int i = 0; i < events.size(); i++){
            names[i] = events.get(i).getName();
        }

        return names;
    }
    
    /**delete all the events, empty the list
     * @param none
     * @return none
     */
     public void clearEvents() {
    	 events.clear();
     }
 
   /**prints out all the events in the list - for debugging purposes
    * @param none
    * @return none
    */
    public void printEvents() {
    	for (int i=0; i<events.size();i++) {
    		System.out.println((Event)events.get(i));
    	}
    }
}
