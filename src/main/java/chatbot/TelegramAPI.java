package chatbot; 

import java.util.ArrayList; 
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage; 
import org.telegram.telegrambots.api.objects.Message; 
import org.telegram.telegrambots.api.objects.Update; 
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup; 
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton; 
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow; 
import org.telegram.telegrambots.bots.TelegramLongPollingBot; 
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramAPI extends TelegramLongPollingBot { 
  TelegramApplication app = new TelegramApplication(); 

  public TelegramAPI() throws Exception {} 

  @Override 
  public String getBotUsername() { 
	return "MovieTelegramBot"; 
  } 

  @Override
  public String getBotToken() { 
	return "796584068:AAEXo7J45zrNMyVg67II1FDX6_dBXrhv74I";
  } 

  @Override 
  public void onUpdateReceived(Update update) { 
	Message message = update.getMessage(); 
	String text = message.getText(); 
	String chatId = update.getMessage().getChatId().toString();
	if (text.equals("/start") && !app.currentUsers.containsKey(chatId)) {
	  try {
	    app.initUser(chatId);
	  } catch (Exception e) {
	    e.printStackTrace();
	  } 
	}
	if (!app.currentUsers.containsKey(chatId)) {
	  SendMessage sendMessage = createMessage(chatId, "Нажмите /start");
	  printMessage(sendMessage);
	} else { 
	  try {
		makeCommand(chatId, text, message);
	  } catch (Exception e) {
		e.printStackTrace();
	  }
	}
  } 

  public void makeCommand(String chatId, String text, Message message) throws Exception { 
	String answer = app.currentUsers.get(chatId).bot.makeAction(text, app.currentUsers.get(chatId));
	List<String> buttonNames = app.currentUsers.get(chatId).bot.getNextCommands(app.currentUsers.get(chatId));
	SendMessage sendMessage = createMessage(chatId, answer);
    setButtons(sendMessage, buttonNames);
    printMessage(sendMessage);
  }

  public void printMessage(SendMessage sendMessage) {
    try {
	  execute(sendMessage);
    } catch (TelegramApiException e) {
	  e.printStackTrace();
    }
  }
  
  public SendMessage createMessage(String chatId, String answer) {
	SendMessage sendMessage = new SendMessage(); 
	sendMessage.enableMarkdown(true); 
	sendMessage.setChatId(chatId); 
	sendMessage.setText(answer);
	return sendMessage;
  } 

  private void setButtons(SendMessage message, List<String> buttonNames) { 
	ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); 
	replyKeyboardMarkup.setSelective(true); 
	replyKeyboardMarkup.setResizeKeyboard(true); 
	replyKeyboardMarkup.setOneTimeKeyboard(false); 
	message.setReplyMarkup(replyKeyboardMarkup); 
	List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
	KeyboardRow keyboardFirstRow = new KeyboardRow(); 
	for (int i = 0; i < buttonNames.size(); i++) { 
	  keyboardFirstRow.add(new KeyboardButton(buttonNames.get(i))); 
	} 
	keyboardRowList.add(keyboardFirstRow);
	replyKeyboardMarkup.setKeyboard(keyboardRowList);
  } 
}