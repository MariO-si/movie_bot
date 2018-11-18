package chatbot; 

import java.util.HashMap; 
import java.util.Map;

public class FSM { 
  private State start = new State("start", ""); 
  private State genres = new State("genres", "Выберите жанр: триллер, боевик, драма, фантастика, "
      + "аниме, приключения, криминал, фэнтези, военный, мультфильм, комедия, семейный, "
  	  + "детектив, мелодрама, биография, история"); 
  private State years = new State("years", "Выберите года создания фильма в формате YYYY-YYYY") ; 
  private State movie = new State("movie", "Выбираю фильм...\n"); 
  private State help = new State("help", "Этот бот умеет выбирать фильм по жанрам и годам, " + 
  	  "заданными пользователем, основываясь на данных сайта kinopoisk.ru\r" + 
      "Команды для общения:\r\n" + 
	  "/help - вызов справки\r\n" + 
	  "/restart - перезапуск бота\r\n" + 
	  "/genres - задать жанры\r\n" + 
	  "/years - задать года\r\n" + 
	  "/movie - найти фильм\n" + 
	  "/stop - закрыть программу"); 
  private Map<State, Map<String, State>> movingToState = new HashMap<State, Map<String, State>>();

  public FSM() { 
	movingToState.put(start, new HashMap<String, State>(){{ 
	  put("/start", help);}}); 
	movingToState.put(genres, new HashMap<String, State>(){{ 
	  put("/restart", help); 
	  put("/years", years); 
	  put("/movie", movie); 
	  put("/help", help);}}); 
	movingToState.put(years, new HashMap<String, State>(){{ 
	  put("/restart", help); 
	  put("/genres", genres); 
	  put("/movie", movie); 
	  put("/help", help);}}); 
	movingToState.put(movie, new HashMap<String, State>(){{ 
	  put("/restart", help); 
	  put("/genres", genres); 
	  put("/years", years); 
	  put("/movie", movie); 
	  put("/help", help);}}); 
	movingToState.put(help, new HashMap<String, State>(){{ 
	  put("/restart", help); 
	  put("/genres", genres); 
	  put("/years", years); 
	  put("/movie", movie); 
	  put("/help", help);}}); 
  } 

  public State getStart() { 
	return start; 
  } 
	
  public State changeState(State state, String cmd) { 
	return movingToState.get(state).get(cmd); 
  }
}