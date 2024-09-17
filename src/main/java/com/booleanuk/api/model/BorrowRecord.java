package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "borrow_records")
@Getter
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "borrowable_id", referencedColumnName = "id")
    private Borrowable borrowable;

    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Setter
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    public boolean isReturned() {
        return this.returnedAt != null;
    }

    public BorrowRecord(User borrower, Borrowable borrowable) {
        this.borrower = borrower;
        this.borrowable = borrowable;
        this.borrowedAt = LocalDateTime.now();
        returnedAt = null;
    }
}
