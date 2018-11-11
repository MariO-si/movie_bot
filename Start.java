package chatbot; 

import java.util.Scanner; 

public class Start { 
  public static void main(String[] args) throws Exception { 
    System.out.println("Press /start"); 
    Bot bot = new Bot(); 
    try (Scanner scanner = new Scanner(System.in)) { 
      while (true) { 
        if (!bot.isMakingAction(scanner.nextLine())) { 
          break; 
        }
      } 
    } 
    catch (Exception e) { 
      e.printStackTrace(); 
    } 
  } 
}