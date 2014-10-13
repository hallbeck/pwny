package base;

/**
 * Created by hallbeck on 7/13/2014.
 */
import java.util.Properties;

import javax.mail.Session;

public class SimpleEmail {

    public static void main(String[] args) {

        System.out.println("SimpleEmail Start");

        String smtpHostServer = "smtp.journaldev.com";
        String emailID = "hallbeck2011@gmail.com";

        Properties props = System.getProperties();

        props.put("mail.smtp.host", smtpHostServer);

        Session session = Session.getInstance(props, null);

        EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
    }
    public void notify(Session session, String toEmail, String subject, String body){

    }

}
