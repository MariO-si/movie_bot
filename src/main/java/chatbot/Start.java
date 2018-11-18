package chatbot; 

import java.util.Scanner; 

public class Start { 
  public static void main(String[] args) throws Exception { 
    System.out.println("Press /start"); 
    Bot bot = new Bot(); 
    try (Scanner scanner = new Scanner(System.in)) { 
      while (true) {
    	String message = bot.makeAction(scanner.nextLine());
        if (message != null) { 
          System.out.println(message);
        } else {
          break;
        }
      } 
    } 
    catch (Exception e) { 
      e.printStackTrace(); 
    } 
  } 
}