import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
//import activation.*;

public class SendEmail {

   public static void sendMail(String recepient, String newPW) {    
      Properties properties = new Properties();
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");

      String myAccount = "hpql.chatting.system@gmail.com";
      String myPassword = "covkzavxmzuugbjl";
      
      Session session = Session.getInstance(properties, new Authenticator() {
    	  @Override
    	  protected PasswordAuthentication getPasswordAuthentication() {
    		  return new PasswordAuthentication(myAccount, myPassword);
    	  }
      });
      Message message = prepareMessage(session, myAccount, recepient, newPW);
      try {
		Transport.send(message);
		System.out.println("Success!");
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

	private static Message prepareMessage(Session session, String myAccount, String recepient, String newPW) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setContent("<h1>" + newPW + "</h1>","text/html");
			return message;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
