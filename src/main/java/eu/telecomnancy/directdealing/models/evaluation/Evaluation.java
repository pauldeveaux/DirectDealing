package eu.telecomnancy.directdealing.models.evaluation;


import eu.telecomnancy.directdealing.models.User;
import jakarta.persistence.*;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id_annonce;
    protected int note;

    @ManyToOne(fetch = FetchType.EAGER)
    protected User author;

    public Evaluation(int note_user, User author){
        this.note = note_user;
        this.author = author;
    }

    public Evaluation() {

    }

    public int getNote() {
        return note;
    }

    public User getUser() {
        return author;
    }
}
