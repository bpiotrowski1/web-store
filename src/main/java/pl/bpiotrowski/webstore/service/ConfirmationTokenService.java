package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.entity.ConfirmationToken;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.ConfirmationTokenRepository;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public void sendConfirmTokenToUser(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        confirmationTokenRepository.save(confirmationToken);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Registration complete!");
        mailMessage.setFrom("storew48@gmail.com");
        mailMessage.setText("To confirm your account please visit: http://wdf6:8080/confirm?token=" + confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);
    }

    public ConfirmationToken findConfirmationTokenByToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Confirmation token: " + token + " not found"));
    }

    public void deleteConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }

}
