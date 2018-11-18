package chatbottest;

import chatbot.Bot;
import org.junit.Assert;
import org.junit.Test;

public class BotTest {
  @Test
  public void testIsMakingAction() throws Exception {
    Bot bot = new Bot();
    String help = "Этот бот умеет выбирать фильм по жанрам и годам, " + 
        "заданными пользователем, основываясь на данных сайта kinopoisk.ru\r" + 
    	    "Команды для общения:\r\n" + 
    		"/help - вызов справки\r\n" + 
    		"/restart - перезапуск бота\r\n" + 
    		"/genres - задать жанры\r\n" + 
    		"/years - задать года\r\n" + 
    		"/movie - найти фильм\n" + 
    		"/stop - закрыть программу";
    Assert.assertEquals(help, bot.makeAction("/start"));
    Assert.assertEquals("Выберите жанр: триллер, боевик, драма, фантастика, "
        + "аниме, приключения, криминал, фэнтези, военный, мультфильм, комедия, семейный, "
    	+ "детектив, мелодрама, биография, история", bot.makeAction("/genres"));
    Assert.assertEquals("", bot.makeAction("триллер"));
    Assert.assertEquals("Выберите года создания фильма в формате YYYY-YYYY", bot.makeAction("/years"));
    Assert.assertEquals("", bot.makeAction("1990-2000"));
    Assert.assertEquals(help, bot.makeAction("/help"));
    String movie = bot.makeAction("/movie");
    Assert.assertEquals(true, movie.contains("Рейтинг") && movie.contains("Жанр") 
    	&& movie.contains("Год"));
    Assert.assertEquals(help, bot.makeAction("/restart"));
    Assert.assertEquals("ОШИБКА. Неправильные данные.", bot.makeAction("abracadabra"));
    Assert.assertEquals("Не могу понять команду", bot.makeAction("/abracadabra"));
	Assert.assertEquals(null, bot.makeAction("/stop"));
  }
}