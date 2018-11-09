package chat_bot;

import java.net.*;
import java.util.Arrays;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Generator {
	public int movieNum = 1;
	//private String[] m_genre;
	//private String[] m_years;
	private String[] dataMovies;
	private Pattern namePattern = Pattern.compile("data-film-title=\"[[^\"].{1}]+\"");
	private Pattern yearPattern = Pattern.compile("\\(\\d{4}\\)");
	private Pattern ratingPattern = Pattern.compile("data-film-rating=\"[[^\"].{1}]+\"");
	private Pattern genrePattern = Pattern.compile("(?<=\\()(\\D+)[^(\\.+)](?=\\.?\\.?\\.?\\))");
	
	
	public Generator() throws Exception{
		//m_genre = genre.split(", ");
		//m_years = years;
		downloadHtmlCode();
		try(FileReader reader = new FileReader("html.txt")){
	        try(Scanner sc = new Scanner(reader)){
		        String htmlCode = sc.nextLine();
		        dataMovies = htmlCode.split("<div id=\"film\\d*\"></div>");	// отделить это, чтобы не вызывалось, если мы продолжаем искать фильм
	        }catch (Exception e) {}
		}catch (Exception e) {}
	}
	
	
	private void downloadHtmlCode() throws Exception {
		// сделать проверку на существование
        URL url = new URL("https://www.kinopoisk.ru/top/lists/58/filtr/all/sort/order/perpage/200/");
        try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))){

            String inputLine;
            try(FileWriter file = new FileWriter("html.txt", true)){
            while ((inputLine = in.readLine()) != null) {
            	file.write(inputLine);
            }
            }catch (Exception e) {}
        }catch (Exception e) {}
	}
	
	
	private String findInfo(Matcher matcher) {
		matcher.find();
		return matcher.group();
	}
	
	
	private String findGenres(String movie, String[] m_genre) {
		String currentGenre = findInfo(genrePattern.matcher(movie));
		String[] currentGenres = currentGenre.split(", ");
		if (currentGenres.length < m_genre.length) {
			return null;
		}
		if (m_genre[0].equals("")) {
			return currentGenre;
		}
		int count = 0;
		for (int k = 0; k < currentGenres.length; k++ ) {
			if (Arrays.asList(m_genre).contains(currentGenres[k])) {
				count++;
			}
		}
		return count == m_genre.length ? currentGenre : null;
	}
	
	
	private int findYear(String movie, String[] m_years) {
		String currentYear = findInfo(yearPattern.matcher(movie)).substring(1, 5);
		int year = Integer.parseInt(currentYear);
		if (m_years[0].equals("")) {
			return year;
		}
		if (year >= Integer.parseInt(m_years[0]) && year <= Integer.parseInt(m_years[1])) {
			return year;
		}
		return -1;
	}
	
	public String chooseMovie(String genres, String[] m_years) throws Exception { 
		
		String[] m_genre = genres.split(" ");
		for (int i = movieNum; i < dataMovies.length; i++) {
    		String currentGenre = findGenres(dataMovies[i], m_genre);
    		int currentYear = findYear(dataMovies[i], m_years);
    		if (currentGenre != null && currentYear != -1) {
	    		String currentName = findInfo(namePattern.matcher(dataMovies[i])).substring(16);
	            String currentRating = findInfo(ratingPattern.matcher(dataMovies[i])).substring(18, 23);
	            movieNum = i + 1;
	            return String.format("%s\nРейтинг: %s\nЖанр: %s\nГод: %s\n", currentName, currentRating, currentGenre, currentYear);
	        }
    	}
        return "";
	}
}
