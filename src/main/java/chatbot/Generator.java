package chatbot; 

import java.io.*;
import java.net.*;
import java.util.ArrayList; 
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator {
  public int movieNum = 1;
  private String[] dataMovies;
  private Pattern namePattern = Pattern.compile("data-film-title=\"[[^\"].{1}]+\"");
  private Pattern genrePattern = Pattern.compile("(?<=\\()(\\D+)[^(\\.+)](?=\\.?\\.?\\.?\\))");
  private Pattern yearPattern = Pattern.compile("\\(\\d{4}\\)"); 
  private Pattern ratingPattern = Pattern.compile("data-film-rating=\"[[^\"].{1}]+\"");

  public Generator() throws Exception {
    downloadHtmlCode();
    FileInputStream stream = new FileInputStream("html.txt");
    InputStreamReader streamReader = new InputStreamReader(stream, "windows-1251");
    try (BufferedReader reader = new BufferedReader(streamReader)) { 
      try (Scanner sc = new Scanner(reader)) { 
    	String htmlCode = sc.nextLine(); 
		dataMovies = htmlCode.split("<div id=\"film\\d*\"></div>"); 
	  } catch (Exception e) {
	    e.printStackTrace();
      } 
	} catch (Exception e) {
	  e.printStackTrace();	
	} 
  } 

  private void downloadHtmlCode() throws Exception {
  	File f = new File("html.txt");
    if (f.exists() && f.isFile()){
      return;
	}
    URL url = new URL("https://www.kinopoisk.ru/top/lists/58/filtr/all/sort/order/perpage/200/");
    InputStreamReader reader = new InputStreamReader(url.openStream(), "windows-1251");
	try (BufferedReader in = new BufferedReader(reader)) { 
	  FileOutputStream stream = new FileOutputStream("html.txt");
	  OutputStreamWriter writer = new OutputStreamWriter(stream, "windows-1251");
	  String inputLine;
	  try (BufferedWriter file = new BufferedWriter(writer)) { 
		while ((inputLine = in.readLine()) != null) { 
		  file.write(inputLine); 
		} 
	  } catch (Exception e) {
		e.printStackTrace();		
	  } 
	} catch (Exception e) {
	  e.printStackTrace();	
	} 
  } 

  public String chooseMovie(ArrayList<String> genres, String[] years) throws Exception { 
	for (int i = movieNum; i < dataMovies.length; i++) { 
	  String currentGenre = findGenres(dataMovies[i], genres);
	  int currentYear = findYear(dataMovies[i], years); 
	  if (currentGenre != null && currentYear != -1) { 
		String currentName = findInfo(namePattern.matcher(dataMovies[i])).substring(16).replaceAll("&nbsp;", " ");
		String currentRating = findInfo(ratingPattern.matcher(dataMovies[i])).substring(18, 23); 
		movieNum = i + 1; 
		return String.format("%s\nРейтинг: %s\nЖанр: %s\nГод: %s\n", currentName, 
		  currentRating, currentGenre, currentYear); 
	  } 
	} 
	return ""; 
  } 
	
  private String findGenres(String movie, ArrayList<String> genres) { 
	String currentGenre = findInfo(genrePattern.matcher(movie)); 
	String[] currentGenres = currentGenre.split(", "); 
	if (currentGenres.length < genres.size()) { 
	  return null; 
	} 
	if (genres.isEmpty()) { 
	  return currentGenre; 
	} 
	int count = 0; 
	for (int k = 0; k < currentGenres.length; k++ ) { 
	  if (genres.contains(currentGenres[k])) { 
		count++; 
	  } 
	} 
	return count == genres.size() ? currentGenre : null; 
  } 

  private int findYear(String movie, String[] years) { 
	String currentYear = findInfo(yearPattern.matcher(movie)).substring(1, 5); 
	int year = Integer.parseInt(currentYear); 
	if (years[0].equals("")) { 
	  return year; 
	} 
	if (year >= Integer.parseInt(years[0]) && year <= Integer.parseInt(years[1])) { 
		return year; 
	} 
	return -1; 
  }
	
  private String findInfo(Matcher matcher) { 
	matcher.find(); 
	return matcher.group(); 
  }
}