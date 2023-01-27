package com.naver.batch.ch7;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
public class Pay {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

    public Pay() {
    }

    public Pay(Long amount, String txName, LocalDateTime txDateTime) {
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }

    public Pay(Long id, Long amount, String txName, LocalDateTime txDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setTxName(String txName) {
        this.txName = txName;
    }

    public void setTxDateTime(LocalDateTime txDateTime) {
        this.txDateTime = txDateTime;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "id=" + id +
                ", amount=" + amount +
                ", txName='" + txName + '\'' +
                ", txDateTime=" + txDateTime +
                '}';
    }
}
