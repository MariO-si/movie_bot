package chatbot; 

import java.util.HashMap; 
import java.util.Map;
import org.telegram.telegrambots.ApiContextInitializer; 
import org.telegram.telegrambots.TelegramBotsApi; 
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class TelegramApplication { 
  Map<String, User> map; 

  public TelegramApplication() throws Exception { 
	map = new HashMap<String, User>(); 
  } 

  public User initUser(String token) throws Exception {
	User user = new User(); 
	map.put(token, user); 
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