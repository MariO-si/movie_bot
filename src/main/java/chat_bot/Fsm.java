package chat_bot;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Fsm {
	
	private Map<State, Map<String, State>> toState = new HashMap<State, Map<String, State>>();
	private State start = new State("start", "");
	private State genres = new State("genres", "�������� ����: �������, ������, �����, ����������, �����, �����������, ��������, ������� , ������� , ���������� , ������� , �������� , �������� , ��������� , ��������� , �������\n");
	private State years = new State("years", "������ ���� �������� ������ � ������� YYYY-YYYY\n")	;
	public State movie = new State("movie", "������� �����...\n");
	//private State end = new State("end", ""); 	// ��� �� ����� ��� ��������� �������, ��� �� ����� ������ �����
	private State help = new State("help", "���� ��� ����� �������� ����� �� ������ � �����, ��������� �������������, ����������� �� ������ ����� kinopoisk.ru\r\n" + 
				"������� ��� �������:\r\n" + 
				"/help � ����� �������\r\n" + 
				"/restart � ���������� ����\r\n" + 
				"/genres � ������ �����\r\n" + 
				"/years � ������ ����\r\n" + 
				"/movie � ����� �����\n");
	
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
