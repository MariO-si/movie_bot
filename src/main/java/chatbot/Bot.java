package chatbot; 

import java.util.ArrayList; 
import java.util.Calendar; 

public class Bot { 
  private FSM fSM = new FSM();
  private State currentState = fSM.getStart();
  private String[] years = new String[] {"", ""}; 
  private ArrayList<String> genres = new ArrayList<String>();
  private final String[] allGenres = new String[] {"триллер", "боевик", "драма", 
	  "фантастика", "аниме", "приключения", "криминал", "фэнтези", "военный", "мультфильм", 
	      "комедия", "семейный", "детектив", "мелодрама", "биография", "история"};
  private Generator generator = new Generator();

  public Bot() throws Exception {} 

  public String makeAction(String cmd) { 
	if (cmd.startsWith("/")) { 
	  return makeCommand(cmd); 
	} else { 
	  return setParametres(cmd); 
	}
  } 

  private String makeCommand(String cmd) { 
    State next = fSM.changeState(currentState, cmd); 
	if (next == null) { 
	  if (cmd.equals("/stop")) { 
		return null;
	  } 
	  return "Не могу понять команду";
	} else { 
	  if (next.name.equals("movie")) { 
		return getMovieName(); 
	  } else if (next.name.equals("genres")) { 
		genres.clear(); 
	  } else if (next.name.equals("years")) { 
		years = new String[] {"", ""}; 
	  } else if (cmd.equals("/restart")) { 
	    years = new String[] {"", ""}; 
		genres.clear(); 
	  } 
	  currentState = next; 
	} 
	return next.message; 
  } 
	
  private String setParametres(String parametre) { 
	if (currentState.name.equals("genres") && isGenres(parametre)) { 
	  genres.add(parametre); 
	} else if (currentState.name.equals("years") && isYears(parametre)) { 
	  years = parametre.split("-"); 
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

  private String getMovieName() { 
    try { 
	  String movie = generator.chooseMovie(genres, years); 
	  if (movie.equals("")) { 
		return "Я не нашел фильма с такими параметрами. Попробуй изменить их";
	  } else { 
		return movie;
	  } 
	} catch (Exception e) {
		return e.getMessage();
	} 
  } 
}