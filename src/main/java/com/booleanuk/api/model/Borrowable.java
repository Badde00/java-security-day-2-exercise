package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowables")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Borrowable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "type")
    protected String type;

    @ManyToOne
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    private User borrower;

    public Borrowable(int id) {
        this.id = id;
    }

    @JsonIgnore
    protected abstract boolean isItemValid();

    @JsonIgnore
    public boolean isValid() {
        return isItemValid();
    }
}
