package mg.portfolio.contact;

import mg.portfolio.api.dto.ContactRequest;
import mg.portfolio.domain.ContactMessage;
import mg.portfolio.domain.ContactMessageRepository;
import mg.portfolio.config.AdminProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ContactService {
 private final ContactMessageRepository messages; private final JavaMailSender mailSender;
 private final org.springframework.core.env.Environment env;
 private final String to; private final String from;
 public ContactService(ContactMessageRepository messages,JavaMailSender mailSender,org.springframework.core.env.Environment env,
     @org.springframework.beans.factory.annotation.Value("${app.contact.to}") String to,
     @org.springframework.beans.factory.annotation.Value("${app.contact.from}") String from){
  this.messages=messages;this.mailSender=mailSender;this.env=env;this.to=to;this.from=from;
 }
 public void receive(ContactRequest request){
  // Champ honeypot : un robot qui le remplit est traité sans révéler la détection.
  if(StringUtils.hasText(request.website())) return;
  ContactMessage saved=messages.save(new ContactMessage(request.name().trim(),request.email().trim(),request.subject().trim(),request.message().trim()));
  if(!StringUtils.hasText(env.getProperty("MAIL_HOST"))) return;
  try {
   SimpleMailMessage mail=new SimpleMailMessage();mail.setFrom(from);mail.setTo(to);
   mail.setReplyTo(saved.getEmail());mail.setSubject("[Portfolio] "+saved.getSubject());
   mail.setText("Nom : "+saved.getName()+"\nEmail : "+saved.getEmail()+"\n\n"+saved.getMessage());
   mailSender.send(mail);
  } catch (Exception ex) {
   // Le message reste en base même si le serveur SMTP est indisponible.
  }
 }
}
