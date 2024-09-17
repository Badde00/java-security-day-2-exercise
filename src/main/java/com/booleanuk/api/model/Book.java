package com.booleanuk.api.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends Borrowable {

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "genre")
    private String genre;

    public Book(String title, String author, String publisher, String genre) {
        super();
        this.setType("book");
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public Book(int id) {
        super(id);
        this.setType("book");
    }

    public boolean isItemValid() {
        return !(StringUtils.isBlank(title)
                || StringUtils.isBlank(author)
                || StringUtils.isBlank(publisher)
                || StringUtils.isBlank(genre));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(type, book.type)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(publisher, book.publisher)
                && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, title, author, publisher, genre);
    }
}
