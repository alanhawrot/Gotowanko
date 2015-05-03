package pl.edu.uj.gotowanko.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * Created by michal on 03.05.15.
 */
@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class.getSimpleName());

    @Autowired
    private MailSender mailSender;

    public MailBuilder from(String email) {
        return new MailBuilder(email);
    }

    public boolean send(String from, String to, String title, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(body);
        try {
            mailSender.send(mailMessage);
            return true;
        } catch (MailException e) {
            logger.warn("MailException: " + e.toString());
            return false;
        }
    }

    public class MailBuilder {

        private String title;
        private String sourceEmail;
        private StringBuilder bodyBuilder = new StringBuilder();

        protected MailBuilder(String email) {

            this.sourceEmail = email;
        }

        public MailBuilder withTitle(String title) {
            this.title = format(title);
            return this;
        }

        public MailBuilder withTitle(String titleFormat, Object... objects) {
            this.title = format(titleFormat, objects);
            return this;
        }

        public MailBuilder nextLine(String line) {
            bodyBuilder.append(format(line));
            bodyBuilder.append("\n");
            return this;
        }

        public MailBuilder nextLine(String lineFormat, Object... objects) {
            bodyBuilder.append(format(lineFormat, objects));
            bodyBuilder.append("\n");
            return this;
        }

        public boolean send(String destinationEmail) {
            return MailService.this.send(sourceEmail, destinationEmail, title, bodyBuilder.toString());
        }
    }
}
