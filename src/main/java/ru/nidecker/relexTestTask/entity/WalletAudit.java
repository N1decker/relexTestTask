package ru.nidecker.relexTestTask.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import ru.nidecker.relexTestTask.entity.enums.WalletAuditType;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(value = EnumType.STRING)
    private WalletAuditType walletAuditType;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String walletName;

    @NonNull
    private LocalDate transactionDate;

    public WalletAudit(WalletAuditType walletAuditType, String userEmail, String walletName, LocalDate transactionDate) {
        this.walletAuditType = walletAuditType;
        this.userEmail = userEmail;
        this.walletName = walletName;
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WalletAudit that = (WalletAudit) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
