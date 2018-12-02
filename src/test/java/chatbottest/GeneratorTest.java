package chatbottest;

import chatbot.Generator;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class GeneratorTest {
  @Test
  public void testWithoutGenresAndYear() throws Exception {
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
    String movie = g.chooseMovie(genres, new String[] {"", ""});
	Assert.assertEquals(true, movie.length() != 0);
  }
	
  @Test
  public void testOneGenre() throws Exception {
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
	genres.add("комедия");
	String movie = g.chooseMovie(genres, new String[] {"", ""});
	Assert.assertEquals(true, movie.contains("комедия"));
  }
	
  @Test
  public void testTwoGenres() throws Exception {
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
    genres.add("комедия");
    genres.add("триллер");
    String movie = g.chooseMovie(genres, new String[] {"", ""});
    Assert.assertEquals(true, movie.contains("комедия") && movie.contains("триллер"));
  }
	
  @Test
  public void testThreeGenres() throws Exception{
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
    genres.add("комедия");
    genres.add("триллер");
    genres.add("криминал");
    String movie = g.chooseMovie(genres, new String[] {"", ""});
    Assert.assertEquals(true, movie.contains("триллер") && movie.contains("криминал") 
        && movie.contains("комедия"));
  }
	
  @Test
  public void testYears() throws Exception{
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
    String movie = g.chooseMovie(genres, new String[] {"1999", "2000"});
	Assert.assertEquals(true, movie.contains("1999"));
  }
	
  @Test
  public void testGenresAndYears() throws Exception {
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
    genres.add("триллер");
    genres.add("драма");
    String movie = g.chooseMovie(genres, new String[] {"1999", "2000"});
    Assert.assertEquals(true, movie.contains("1999") && movie.contains("триллер") 
    	&& movie.contains("драма"));
  }
	
  @Test
  public void testMovieNotFound() throws Exception {
    Generator g = new Generator();
    ArrayList<String> genres = new ArrayList<String>();
    genres.add("жанр");
    String movie = g.chooseMovie(genres, new String[] {"", ""});
    Assert.assertEquals("", movie);
  }
}