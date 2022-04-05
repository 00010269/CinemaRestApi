package uz.pdp.cinemaroomb6.service.interfaces;

import javax.mail.MessagingException;

public interface EmailSendService {

    void sendMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
