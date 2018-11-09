package chat_bot_test;

import org.junit.Assert;
import org.junit.Test;

import chat_bot.Generator;

public class TestChooseMovie {
	
	@Test
	public void testOneGenre() throws Exception {
		Generator g = new Generator();
		String movie = g.chooseMovie("�������", new String[] {"", ""});
		Assert.assertEquals(true, movie.contains("�������"));
	}
	
	@Test
	public void testThreeGenres() throws Exception{
		Generator g = new Generator();
		String movie = g.chooseMovie("�������, ��������, �������", new String[] {"", ""});
		Assert.assertEquals(true, movie.contains("�������") && movie.contains("��������") && movie.contains("�������"));
	}
	
	@Test
	public void testYears() throws Exception{
		Generator g = new Generator();
		String movie = g.chooseMovie("", new String[] {"1999", "2000"});
		System.out.println(movie);
		Assert.assertEquals(true, movie.contains("1999"));
	}
	
}
