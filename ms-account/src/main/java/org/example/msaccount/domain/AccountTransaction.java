package org.example.msaccount.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountTransaction {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateTime;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id")
    private Account account;
}


