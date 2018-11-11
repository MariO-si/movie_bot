package chatbottests;

import chatbot.FSM;
import chatbot.State;
import org.junit.Assert;
import org.junit.Test;

public class FSMTest {
  @Test
  public void testGetStart() throws Exception {
	FSM fSM = new FSM();
	Assert.assertEquals("start", fSM.getStart().name);
	Assert.assertEquals("", fSM.getStart().message);
  }
	
  @Test
  public void testChangeStateStart() throws Exception {
	FSM fSM = new FSM();
	State start = fSM.getStart();
	Assert.assertNull(fSM.changeState(start, "/genres"));
	Assert.assertNull(fSM.changeState(start, "/years"));
	Assert.assertNull(fSM.changeState(start, "/movie"));
	Assert.assertNull(fSM.changeState(start, "/restart"));
	Assert.assertNull(fSM.changeState(start, "/help"));
	Assert.assertNotNull(fSM.changeState(start, "/start"));
  }
	
  @Test
  public void testChangeStateHelp() throws Exception {
    FSM fSM = new FSM();
    State start = fSM.getStart();
    State help = fSM.changeState(start, "/start");
    Assert.assertNull(fSM.changeState(help, "/start"));
    Assert.assertNotNull(fSM.changeState(help, "/genres"));
    Assert.assertNotNull(fSM.changeState(help, "/years"));
    Assert.assertNotNull(fSM.changeState(help, "/movie"));
    Assert.assertNotNull(fSM.changeState(help, "/restart"));
    Assert.assertNotNull(fSM.changeState(help, "/help"));
  }
	
  @Test
  public void testChangeStateGenres() throws Exception {
    FSM fSM = new FSM();
	State start = fSM.getStart();
	State help = fSM.changeState(start, "/start");
	State genres = fSM.changeState(help, "/genres");
	Assert.assertNull(fSM.changeState(genres, "/genres"));
	Assert.assertNull(fSM.changeState(genres, "/start"));
	Assert.assertNotNull(fSM.changeState(genres, "/years"));
	Assert.assertNotNull(fSM.changeState(genres, "/movie"));
	Assert.assertNotNull(fSM.changeState(genres, "/restart"));
	Assert.assertNotNull(fSM.changeState(genres, "/help"));
  }
	
  @Test
  public void testChangeStateYears() throws Exception {
	FSM fSM = new FSM();
	State start = fSM.getStart();
	State help = fSM.changeState(start, "/start");
	State years = fSM.changeState(help, "/years");
	Assert.assertNull(fSM.changeState(years, "/years"));
	Assert.assertNull(fSM.changeState(years, "/start"));
	Assert.assertNotNull(fSM.changeState(years, "/genres"));
	Assert.assertNotNull(fSM.changeState(years, "/movie"));
	Assert.assertNotNull(fSM.changeState(years, "/restart"));
	Assert.assertNotNull(fSM.changeState(years, "/help"));
  }
	
  @Test
  public void testChangeStateMovie() throws Exception {
	FSM fSM = new FSM();
	State start = fSM.getStart();
	State help = fSM.changeState(start, "/start");
	State movie = fSM.changeState(help, "/movie");
	Assert.assertNull(fSM.changeState(movie, "/start"));
	Assert.assertNotNull(fSM.changeState(movie, "/years"));
	Assert.assertNotNull(fSM.changeState(movie, "/genres"));
	Assert.assertNotNull(fSM.changeState(movie, "/movie"));
	Assert.assertNotNull(fSM.changeState(movie, "/restart"));
	Assert.assertNotNull(fSM.changeState(movie, "/help"));
  }
}