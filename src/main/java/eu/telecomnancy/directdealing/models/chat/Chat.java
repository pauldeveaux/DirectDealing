package eu.telecomnancy.directdealing.models.chat;

import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User demandeur;
    @ManyToOne
    private User proposeur;
    @OneToMany
    private final ArrayList<Message> messages;
    @ManyToOne
    private final Annonce annonce;

    public Chat(Annonce annonce) throws NotAUserException {
        this.demandeur= DirectDealing.getInstance().getActiveUser();
        this.proposeur=DirectDealing.getInstance().getUser(annonce.getAuthor().getUsername());
        this.annonce =annonce;
        this.messages= new ArrayList<>();
    }

    public Chat() {
        this.messages=new ArrayList<>();
        this.annonce=null;
    }

    public void sendMessage(User owner,String message) {
        //System.out.println("Le message est parvenu à la classe Chat.");
        //System.out.println("Longeur de l'ArrayList des msg avant ajout : " +this.messages.size());
        messages.add(new Message(owner,message));
        //System.out.println("Dans celle ci, les attributs demandeur, proposeur sont :"+this.demandeur.getUsername()+", "+this.proposeur.getUsername());
        //System.out.println("Longeur de l'ArrayList des msg après : " +this.messages.size());
    }

    public User getDemandeur(){
        return demandeur;
    }

    public User getProposeur(){
        return proposeur;
    }

    public ArrayList<String> getMessages(){
        ArrayList<String> messages = new ArrayList<>();
        for (Message message:this.messages){
            LocalDateTime infos = message.getDate();
            messages.add(infos.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).substring(0, 1).toUpperCase() + infos.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).substring(1).toLowerCase() +" "+infos.getDayOfMonth()+"/"+infos.getMonth().getValue()+"/"+infos.getYear()+" à "+infos.getHour()+":"+infos.getMinute()+". De la part de : "+message.getOwner().getUsername()+"\n"+message.getContent()+"\n");
        }
        return  messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDemandeur(User demandeur) {
        this.demandeur = demandeur;
    }

    public void setProposeur(User proposeur) {
        this.proposeur = proposeur;
    }

    public Annonce getAnnonce(){
        return this.annonce;
    }

    public User getOtherUser() {
        if(DirectDealing.getInstance().getActiveUser().equals(proposeur)){
            return demandeur;
        }
        else{
            return proposeur;
        }
    }
}
