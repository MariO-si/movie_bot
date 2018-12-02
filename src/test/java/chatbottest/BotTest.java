package chatbottest;

import chatbot.User;
import org.junit.Assert;
import org.junit.Test;

public class BotTest {
  @Test
  public void testIsMakingAction() throws Exception {
	User usr = new User();
    String help = "Этот бот умеет выбирать фильм по жанрам и годам, " + 
        "заданными пользователем, основываясь на данных сайта kinopoisk.ru\r\n" + 
    	    "Команды для общения:\r\n" + 
    		"/help - вызов справки\r\n" + 
    		"/restart - перезапуск бота\r\n" + 
    		"/genres - задать жанры\r\n" + 
    		"/years - задать года\r\n" + 
    		"/movie - найти фильм\n";
    Assert.assertEquals(help, usr.bot.makeAction("/start", usr));
    Assert.assertEquals("Выберите жанр: триллер, боевик, драма, фантастика, "
        + "аниме, приключения, криминал, фэнтези, военный, мультфильм, комедия, семейный, "
    	+ "детектив, мелодрама, биография, история, мюзикл", usr.bot.makeAction("/genres", usr));
    Assert.assertEquals("", usr.bot.makeAction("триллер", usr));
    Assert.assertEquals("Выберите года создания фильма в формате YYYY-YYYY", 
        usr.bot.makeAction("/years", usr));
    Assert.assertEquals("", usr.bot.makeAction("1990-2000", usr));
    Assert.assertEquals(help, usr.bot.makeAction("/help", usr));
    String movie = usr.bot.makeAction("/movie", usr);
    Assert.assertEquals(true, movie.contains("Рейтинг") && movie.contains("Жанр") 
    	&& movie.contains("Год"));
    Assert.assertEquals(help, usr.bot.makeAction("/restart", usr));
    Assert.assertEquals("ОШИБКА. Неправильные данные.", usr.bot.makeAction("abracadabra", usr));
    Assert.assertEquals("Не могу понять команду", usr.bot.makeAction("/abracadabra", usr));
  }
}