package eu.telecomnancy.directdealing.models;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.models.annonces.Service;
import eu.telecomnancy.directdealing.models.chat.Chat;
import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.models.evaluation.Evaluation;
import eu.telecomnancy.directdealing.utils.Config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import eu.telecomnancy.directdealing.utils.db.DAO;
import jakarta.persistence.*;


/**
 * Classe représentant un utilisateur
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username")
    private String username;
    private String password;
    private String num;
    private String mail;
    private String adresse;
    private URL imgUrl;
    /**
     * Entier représentant les droits de l'utilisateur :
     * 0 si user - 1 si admin
     */
    private int droits;
    private int solde;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Annonce> annonces;


    @OneToMany(mappedBy = "demandeur", fetch = FetchType.EAGER)
    private List<Deal> dealsDemandeur;
    @OneToMany(mappedBy = "offrant", fetch = FetchType.EAGER)
    private List<Deal> dealsOffrant;

    @OneToMany( fetch = FetchType.EAGER)
    private Map<User,Chat> chats;

    @OneToMany(mappedBy ="author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;


    private boolean newMessages=false;
    private String annonceMSG="";

    public User() {

    }

    /**
     * Créé un utilisateur à partir de ses informations
     *
     * @param username
     * @param password
     * @param num
     * @param mail
     * @param adresse
     */
    public User(String username, String password, String num, String mail, String adresse) {
        this.username = username;
        this.password=password;
        this.num = num;
        this.mail = mail;
        this.adresse = adresse;
        this.droits = 0;
        this.annonces = new ArrayList<>();
        this.imgUrl = Application.class.getResource("/default-user.png");
        this.solde = Config.getInstance().initial_florains;
        this.chats= new HashMap<>();
        this.dealsDemandeur = new ArrayList<>();
        this.dealsOffrant = new ArrayList<>();
        this.evaluations = new ArrayList<>();
    }

    /**
     * Créé un utilisateur à partir de ses informations et d'une image
     *
     * @param username
     * @param password
     * @param num
     * @param mail
     * @param adresse
     * @param imgUrl
     */
    public User(String username, String password, String num, String mail, String adresse, String imgUrl) {
        this(username, password, num, mail, adresse);
        this.annonces = new ArrayList<>();
        this.chats = new HashMap<>();
        this.dealsDemandeur = new ArrayList<>();
        this.dealsOffrant = new ArrayList<>();
        this.evaluations = new ArrayList<>();
        try {
            this.imgUrl = new URI(imgUrl).toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Test le mot de passe de l'utilisateur
     *
     * @param password
     * @return vrai si le password est le même que celui de l'utilisateur, faux sinon
     */
    public boolean isPasswordMatching(String password) {
        return this.password.equals(password);
    }





    // ---------------------------- Getters & Setters ---------------------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public URL getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(URL imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getDroits() {
        return droits;
    }

    public void setDroits(int droits) {
        this.droits = droits;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }


    public String toString() {
        return String.format("""
                User :
                    username : %s
                    password : %s
                    numero : %s
                    mail : %s
                    adresse : %s
                    imgUrl : %s
                    droits : %d
                    solde : %d
                    Annonce : %s
                """, username, password, num, mail, adresse, imgUrl, droits, solde,annonces);
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public void addAnnonce(Annonce p) {
        this.annonces.add(p);
        DAO.saveAnnonce(p);
    }

    public void addDeal(Deal d) {
        System.out.println("ADD DEAL : " + username);
        if(d.getDemandeur().equals(this)){
            this.dealsDemandeur.add(d);
            d.getOffrant().addDeal(d);
            DAO.saveDeal(d);
            DAO.updateUser(this);
        }else if(d.getOffrant().equals(this)){
            this.dealsOffrant.add(d);
            DAO.updateUser(this);
        }
        else{
            System.out.println("NON");
        }
    }

    public void removeAnnonce(Annonce a) {
        this.annonces.remove(a);
    }

    public void addChat(User user,Chat chat) {
        this.chats.put(user,chat);
    }

    public Chat getChat(User user){
        return this.chats.get(user);
    }

    public Map<User,Chat> getChats(){
        return this.chats;
    }

    public List<Deal> getDealsDemandeur() {
        return dealsDemandeur;
    }

    public void setDealsDemandeur(List<Deal> dealsDemandeur) {
        this.dealsDemandeur = dealsDemandeur;
    }

    public List<Deal> getDealsOffrant() {
        return dealsOffrant;
    }

    public void setDealsOffrant(List<Deal> dealsOffrant) {
        this.dealsOffrant = dealsOffrant;
    }

    public void setChats(Map<User, Chat> chats) {
        this.chats = chats;
    }

    public void removeChat(User user){
        this.chats.remove(user);
    }

    public void sendMessage(User target, String content){
        Chat chat = this.chats.get(target);
        chat.sendMessage(this,content);
        target.setNewMessages(true);
        target.setAnnonceMSG(chat.getAnnonce().getTitre());
    }

    public ArrayList<String> getChatsUserListedByUsername() {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : this.chats.keySet()) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }


    public String getEvaluationString() {
        System.out.println(this.evaluations);
        int note = 0;
        for (Evaluation e : this.evaluations) {
            note += e.getNote();
        }
        if (this.evaluations.size() == 0) {
            return "Pas encore d'évaluation";
        } else {
            return String.valueOf(note / this.evaluations.size());
        }
    }

    public void addEvaluation(Evaluation e) {
        this.evaluations.add(e);
        DAO.saveEvaluation(e);
    }

    public List<Pret> getPrets() {
        return annonces.stream().filter(annonce -> annonce instanceof Pret).map(annonce -> (Pret) annonce).toList();    }

    public List<Emprunt> getEmprunts() {
        return annonces.stream().filter(annonce -> annonce instanceof Emprunt).map(annonce -> (Emprunt) annonce).toList();
    }

    public List<Service> getServices() {
        return annonces.stream().filter(annonce -> annonce instanceof Service).map(annonce -> (Service) annonce).toList();
    }

    public void setNewMessages(boolean value){this.newMessages=value;}

    public boolean getNewMessages(){
        return this.newMessages;
    }

    public void setAnnonceMSG(String annonceName){
        this.annonceMSG=annonceName;
    }

    public String getAnnonceMSG(){
        return this.annonceMSG;
    }

    public boolean equals(Object object){
        if(object instanceof User){
            User user = (User) object;
            return user.getUsername().equals(this.username);
        }
        return false;
    }

}



