package chatbot; 

import java.util.ArrayList; 

public class User {
  public String id;
  public Bot bot;
  public State currentState;
  public String[] years; 
  public ArrayList<String> genres;

  public User(String userId) throws Exception{
    id = userId;
	bot = new Bot();
	currentState = bot.getStart();
	years = new String[] {"", ""}; 
	genres = new ArrayList<String>();
  } 
}
