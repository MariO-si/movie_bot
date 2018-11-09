package chat_bot;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Fsm {
	
	private Map<State, Map<String, State>> toState = new HashMap<State, Map<String, State>>();
	private State start = new State("start", "");
	private State genres = new State("genres", "¬ыберите жанр: триллер, боевик, драма, фантастика, аниме, приключени€, криминал, фэнтези , военный , мультфильм , комеди€ , семейный , детектив , мелодрама , биографи€ , истори€\n");
	private State years = new State("years", "¬ыбери года создани€ фильма в формате YYYY-YYYY\n")	;
	public State movie = new State("movie", "¬ыбираю фильм...\n");
	//private State end = new State("end", ""); 	// нам не нужно это состо€ние наверно, это мы нашли нужный фильм
	private State help = new State("help", "Ётот бот умеет выбирать фильм по жанрам и годам, заданными пользователем, основыва€сь на данных сайта kinopoisk.ru\r\n" + 
				" оманды дл€ общени€:\r\n" + 
				"/help Ц вызов справки\r\n" + 
				"/restart Ц перезапуск бота\r\n" + 
				"/genres Ц задать жанры\r\n" + 
				"/years Ц задать года\r\n" + 
				"/movie Ц найти фильм\n");
	
	public Fsm() {
		
		toState.put(start, new HashMap<String, State>(){{
			put("/start", help);}});
		toState.put(genres, new HashMap<String, State>(){{
			put("/restart", help);
			put("/years", years);
			put("/movie", movie);
			put("/help", help);}});
		toState.put(years, new HashMap<String, State>(){{
			put("/restart", help);
			put("/genres", genres);
			put("/movie", movie);
			put("/help", help);}});
		toState.put(movie, new HashMap<String, State>(){{
			put("/restart", help);
			put("/genres", genres);
			put("/years", years);
			put("/movie", movie);
			put("/help", help);}});
		toState.put(help, new HashMap<String, State>(){{
			put("/restart", help);
			put("/genres", genres);
			put("/years", years);
			put("/movie", movie);
			put("/help", help);}});
		
	}
	
	public State ChangeState(State state, String cmd) {
		return toState.get(state).get(cmd);
	}
	
	public State GetStart() {
		return start;
	}
}
