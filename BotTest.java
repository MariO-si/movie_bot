package chatbottests;

import chatbot.Bot;
import org.junit.Assert;
import org.junit.Test;

public class BotTest {
  @Test
  public void testIsMakingAction() throws Exception {
    Bot bot = new Bot();
    Assert.assertEquals(true, bot.isMakingAction("/start"));
    Assert.assertEquals(true, bot.isMakingAction("/genres"));
    Assert.assertEquals(true, bot.isMakingAction("/years"));
    Assert.assertEquals(true, bot.isMakingAction("/help"));
    Assert.assertEquals(true, bot.isMakingAction("/movie"));
    Assert.assertEquals(true, bot.isMakingAction("/restart"));
	Assert.assertEquals(false, bot.isMakingAction("/stop"));
  }
}