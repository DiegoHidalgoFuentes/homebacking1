package com.minhub.homebacking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;


    public Transaction() {
    }

    public Transaction(TransactionType type, double amount, String description, LocalDateTime data, Account account) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.data = data;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.getAmount(), getAmount()) == 0 &&
                Objects.equals(getId(), that.getId()) && getType() ==
                that.getType() && Objects.equals(getDescription(),
                that.getDescription()) && Objects.equals(getData(),
                that.getData()) && Objects.equals(getAccount(),
                that.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getAmount(), getDescription(), getData(), getAccount());
    }*/
}
