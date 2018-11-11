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

  public boolean isMakingAction(String cmd) { 
	if (cmd.startsWith("/")) { 
	  return isMakingCommand(cmd); 
	} else { 
	  setParametres(cmd); 
	} 
	return true; 
  } 

  private boolean isMakingCommand(String cmd) { 
    State next = fSM.changeState(currentState, cmd); 
	if (next == null) { 
	  if (cmd.equals("/stop")) { 
		return false;
	  } 
	  System.out.println("Не могу понять команду\n"); 
	} else { 
	  System.out.println(next.message); 
	  if (next.name.equals("movie")) { 
		chooseMovie(); 
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
	return true; 
  } 
	
  private void setParametres(String parametre) { 
	if (currentState.name.equals("genres") && isGenres(parametre)) { 
	  genres.add(parametre); 
	} else if (currentState.name.equals("years") && isYears(parametre)) { 
	  years = parametre.split("-"); 
	} else { 
	  System.out.println("ОШИБКА. Неправильные данные.\n"); 
	} 
  }

  private boolean isGenres(String line) { 
	String[] lines = line.split(", "); 
	int count = 0; 
	for (int i = 0; i < allGenres.length; i++) { 
	  for (int j = 0; j < lines.length; j++) { 
		if (allGenres[i].equals(lines[j])) { 
		  count++; 
		} 
	  } 
	} 
	return count == lines.length; 
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

  private void chooseMovie() { 
    try { 
	  String movie = generator.chooseMovie(genres, years); 
	  if (movie.equals("")) { 
		System.out.println("Я не нашел фильма с такими параметрами. Попробуй изменить их\n"); 
	  } else { 
		System.out.println(movie); 
	  } 
	} catch (Exception e) {
		e.printStackTrace();
	} 
  } 
}