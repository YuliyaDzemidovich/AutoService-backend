package org.example.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "sequence_client_id"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private int id;
    @Column
    @Size(max = 100, message = "book title should not be greater than 100 characters")
    private String title;
    @Column(nullable = false)
    private int authorId;
    @Column(nullable = false)
    private int publisherId;
    @Column
    private Date year;
    @Column(unique = true, nullable = false)
    private String ISBN;
    @Column
    private int typeId;
    @Column
    private String coverUrl;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(this.id);
        sb.append("\nBook title: ");
        sb.append(this.title);
        sb.append("\nAuthorId: ");
        sb.append(this.authorId);
        sb.append("\nPublisherId: ");
        sb.append(this.publisherId);
        sb.append("\nYear: ");
        sb.append(this.year.toLocalDate().getYear());
        sb.append("\nISBN: ");
        sb.append(this.ISBN);
        sb.append("\nTypeId: ");
        sb.append(this.typeId);
        sb.append("\nHas cover image stored? ");
        sb.append(this.coverUrl != null);
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
