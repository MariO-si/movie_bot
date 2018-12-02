package chatbot; 

import java.util.Scanner; 

public class ConsoleApplication {
  public static void main(String[] args) throws Exception { 
	System.out.println("Press /start"); 
	User user = new User(); 
	try (Scanner scanner = new Scanner(System.in)) { 
	  while (true) { 
		String message = user.bot.makeAction(scanner.nextLine(), user); 
		if (message != null) { 
		  System.out.println(message); 
		} else { 
		  break; 
		} 
	  } 
	} catch (Exception e) { 
	  e.printStackTrace(); 
	} 
  } 
}