package chat_bot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot {
	
	private Generator generator;
	private Fsm fsm;
	private String m_genres;
	private String[] m_years;
	private State currentState;
	private final String[] allGenres;
	
	public Bot() throws Exception{
		generator = new Generator();
		fsm = new Fsm();
		m_genres = "";
		m_years = new String[] {"", ""};
		currentState = fsm.GetStart();
		allGenres = new String[] {"триллер", "боевик", "драма", "фантастика", "аниме", "приключения", "криминал", "фэнтези", 
				"военный", "мультфильм", "комедия", "семейный", "детектив", "мелодрама", "биография", "история"};
	}
	
	public String makeAction(String cmd) {
		if (isCommand(cmd)) {
			State next = fsm.ChangeState(currentState, cmd);
			if (next == null) {
				System.out.println("Не могу понять команду\n");
				return "";
			}
			System.out.println(next.message);
			if (next.name.equals("movie")) { 
				chooseMovie(); 
			} else if (next.name.equals("genres")) { 
				m_genres = ""; 
			} else if (next.name.equals("years")) { 
				m_years = new String[] {"", ""}; 
			} else if (cmd.equals("/restart")) {
				m_years = new String[] {"", ""}; 
				m_genres = ""; 
			}
			currentState = next; 
		}else {
			if (currentState.name.equals("genres") && isGenres(cmd)) { 
				m_genres += cmd + " "; 
			} else if (currentState.name.equals("years") && isYears(cmd)) { 
				m_years = cmd.split("-"); 
			} else { 
				System.out.print("ОШИБКА. Неправильные данные.\n"); 
			}
		}
		return "";
	}
	
	private boolean isCommand(String line) {
		Pattern patternCommand = Pattern.compile("^/.+");
		Matcher m = patternCommand.matcher(line);
		return m.matches();
	}
	
	public boolean isGenres(String line) {
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
			return first < last && first > 1800 && last < 2019;	// поставить сеголняшний год через метод?
		} catch (Exception e) {
			return false;
		}
	}
	
	public void chooseMovie() {
		try {
			String movie = generator.chooseMovie(m_genres, m_years);
			if (movie.equals("")) {
				System.out.println("Я не нашел фильма с такими параметрами. Попробуй изменить их\n");
			}else {
				System.out.println(movie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
