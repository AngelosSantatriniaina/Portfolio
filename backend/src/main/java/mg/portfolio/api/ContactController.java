package mg.portfolio.api;
import mg.portfolio.api.dto.ContactRequest;
import mg.portfolio.contact.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {
 private final ContactService service;
 public ContactController(ContactService service){this.service=service;}
 @PostMapping public ResponseEntity<?> send(@Valid @RequestBody ContactRequest request){
  service.receive(request);
  return ResponseEntity.accepted().body(Map.of("message","Message reçu. Merci pour votre contact."));
 }
}
