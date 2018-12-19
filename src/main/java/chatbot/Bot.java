package chatbot; 

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List; 

public class Bot {
  private FSM fSM = new FSM();
  private final String[] allGenres = new String[] {"триллер", "боевик", "драма", 
	  "фантастика", "аниме", "приключения", "криминал", "фэнтези", "военный", "мультфильм", 
	      "комедия", "семейный", "детектив", "мелодрама", "биография", "история", "мюзикл"}; 
  private Generator generator = new Generator();
  public String currentMovie;

  public Bot() throws Exception {}

  public String makeAction(String cmd, User user) throws SQLException {
	if (cmd.startsWith("/")) { 
	  return makeCommand(cmd, user); 
	} else { 
	  return setParametres(cmd, user); 
	} 
  } 

  private String makeCommand(String cmd, User user) { 
	State next = fSM.changeState(user.currentState, cmd); 
	if (next == null) { 
	  return "Не могу понять команду"; 
	} else {
	  user.currentState = next;
	  if (next.name.equals("movie")) {
		return getMovieName(user); 
	  } else if (next.name.equals("genres")) { 
		user.genres.clear(); 
	  } else if (next.name.equals("years")) { 
		user.years = new String[] {"", ""}; 
	  } else if (cmd.equals("/restart")) { 
		user.years = new String[] {"", ""}; 
		user.genres.clear();
		generator.movieNum = 1;
	  }
	} 
	return next.message; 
  } 

  public String setParametres(String parametre, User user) throws SQLException { 
	if (user.currentState.name.equals("genres") && isGenres(parametre)) { 
	  user.genres.add(parametre); 
	} else if (user.currentState.name.equals("years") && isYears(parametre)) { 
	  user.years = parametre.split("-"); 
	} else if (user.currentState.name.equals("movie")) {
	  if (parametre.equals("Да")) {
		generator.addMovieToDataBase(currentMovie, user.id);
		return getMovieName(user);
	  } else if (parametre.equals("Нет")) {
		return "";
	  }
	} else { 
	  return "ОШИБКА. Неправильные данные."; 
	} 
	return ""; 
  } 

  private boolean isGenres(String line) { 
	for (int i = 0; i < allGenres.length; i++) { 
	  if (allGenres[i].equals(line)) { 
		return true; 
	  } 
	} 
	return false; 
  } 

  private boolean isYears(String years) { 
	String[] year = years.split("-"); 
	try { 
	  int first = Integer.parseInt(year[0]); 
	  int last = Integer.parseInt(year[1]); 
	  int currentYear = Calendar.getInstance().get(Calendar.YEAR); 
	  return first < last && first > 1800 && last <= currentYear; 
	} catch (Exception e) { 
	  return false; 
	} 
  } 

  private String getMovieName(User user) {
	try {
	  String movie = generator.chooseMovie(user.genres, user.years, user);
	  if (movie.equals("")) {
        generator.movieNum = 1;
		return "Я не нашел фильма с такими параметрами. Попробуй изменить их"; 
	  } else {
		return movie; 
	  } 
	} catch (Exception e) { 
	  return e.getMessage(); 
	} 
  } 

  public List<String> getNextCommands(User user){ 
    return fSM.getNextCommands(user.currentState); 
  } 
  
  public State getStart() { 
	return fSM.getStart(); 
  } 
}