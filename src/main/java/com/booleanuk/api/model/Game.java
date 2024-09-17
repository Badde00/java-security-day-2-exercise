package com.booleanuk.api.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
@Getter
@Setter
public class Game extends Borrowable{
    @Column(name = "title")
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "genre")
    private String genre;

    @Column(name = "releaseYear")
    private Integer releaseYear;


    public Game(String title, String publisher, String genre, int releaseYear) {
        super();
        this.setType("game");
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    public Game(int id) {
        super(id);
        this.setType("game");
    }

    public boolean isItemValid() {
        return !(StringUtils.isBlank(title)
                || StringUtils.isBlank(publisher)
                || StringUtils.isBlank(genre)
                || releaseYear != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return Objects.equals(id, game.id)
                && Objects.equals(type, game.type)
                && Objects.equals(title, game.title)
                && Objects.equals(publisher, game.publisher)
                && Objects.equals(genre, game.genre)
                && Objects.equals(releaseYear, game.releaseYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, title, releaseYear, publisher, genre);
    }
}
