package br.com.pagsys.msemail.model;

import br.com.pagsys.msemail.enums.EmailType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EmailType type;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Email(EmailType type, String email, Date date) {
        this.type = type;
        this.email = email;
        this.date = date;
    }
}
