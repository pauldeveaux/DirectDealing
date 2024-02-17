package eu.telecomnancy.directdealing.models.chat;

import eu.telecomnancy.directdealing.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User owner;

    private LocalDateTime date;

    private String content;

    public Message(User owner, String content){
        this.date=LocalDateTime.now();
        this.content=content;
        this.owner=owner;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date.truncatedTo(ChronoUnit.MINUTES);
    }

    public User getOwner() {
        return owner;
    }
}
