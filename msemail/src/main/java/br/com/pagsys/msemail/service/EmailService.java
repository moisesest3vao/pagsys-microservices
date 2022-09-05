package br.com.pagsys.msemail.service;

import br.com.pagsys.msemail.enums.EmailType;
import br.com.pagsys.msemail.model.Email;
import br.com.pagsys.msemail.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private final JavaMailSender javaMailSender;
    @Autowired
    private EmailRepository emailRepository;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendJoinerEmail(String email) {
        this.send(email, EmailType.JOINER,
                "Welcome to PagSys: hope you enjoy it",
                """
                        Hello new User\s

                        We are happy knowing that you choose us as your payment system!
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""
                        );
    }

    public void sendLeaverEmail(String email) {
        this.send(email,
                EmailType.LEAVER, "See you, PagSys: It's never a goodbye",
                """
                        Hello ex-user\s

                        We are sad knowing that you left us, but it's ok!
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""
        );
    }

    public void sendFailedPurchase(String email) {
        this.send(email,
                EmailType.FAILED_PURCHASE, "PagSys: We had an issue with your purchase",
                """
                        Hello user\s

                        Your purchase was cancelled, please try again! :(
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""
        );
    }

    public void sendSuccessPurchase(String email) {
        this.send(email,
                EmailType.SUCCESS_PURCHASE, "PagSys: Thank you for your purchase",
                """
                        Hello user\s

                        Thank you for choosing us, come back any time! :)
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""
        );
    }

    public void sendProcessingPurchase(String email) {
        this.send(email,
                EmailType.PROCESSING_PURCHASE, "PagSys: We're on it",
                """
                        Hello user\s

                        We got your purchase and we're processing it! :)
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""
        );
    }

    private void send(String to, EmailType type, String subject, String text){

        this.emailRepository.save(new Email(type, to, new Date()));

        var mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);

        javaMailSender.send(mail);
        log.info("email sent successfully");
    }

}
