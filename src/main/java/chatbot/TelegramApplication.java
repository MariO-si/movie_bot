package chatbot; 

import java.util.HashMap; 
import java.util.Map;
import org.telegram.telegrambots.ApiContextInitializer; 
import org.telegram.telegrambots.TelegramBotsApi; 
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class TelegramApplication { 
  Map<String, User> currentUsers;
  DataBase base;

  public TelegramApplication() throws Exception { 
	currentUsers = new HashMap<String, User>();
    base = new DataBase();
  } 

  public User initUser(String token) throws Exception {
	User user = new User(token);
	currentUsers.put(token, user);
	return user;
  } 

  public static void main(String[] args) throws Exception { 
	ApiContextInitializer.init(); 
	TelegramBotsApi telegramBotsApi = new TelegramBotsApi(); 
	try {
	  telegramBotsApi.registerBot(new TelegramAPI()); 
	} catch (TelegramApiRequestException e) { 
	  e.printStackTrace();
	}
  }
}