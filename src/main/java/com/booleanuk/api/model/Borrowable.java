package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @JsonIgnoreProperties({"borrowedItems", "roles"})
    private User borrower;

    @OneToMany(mappedBy = "borrowable")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BorrowRecord> borrowHistory;

    public Borrowable(int id) {
        this.id = id;
    }

    @JsonIgnore
    protected abstract boolean isItemValid();

    @JsonIgnore
    public boolean isValid() {
        return isItemValid();
    }

    public boolean isAvailable() {
        return borrower == null;
    }
}
