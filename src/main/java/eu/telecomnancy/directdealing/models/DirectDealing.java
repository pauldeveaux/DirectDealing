package eu.telecomnancy.directdealing.models;

import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.exceptions.UserAlreadyExistsException;
import eu.telecomnancy.directdealing.exceptions.WrongPasswordException;
import eu.telecomnancy.directdealing.models.annonces.*;
import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.utils.JsonUtils;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe principale du modèle.
 * A récupérer avec DirectDealing.getInstance()
 */
public class DirectDealing {

    /**
     * Singleton
     */
    private static DirectDealing instance;
    /**
     * User connecté
     */
    private User activeUser;


    private final BooleanProperty isUserConnectedProperty = new SimpleBooleanProperty(false);

    /**
     * Liste des utilisateurs sous la forme <mail, User>
     */
    private Map<String, User> users;

    /**
     * Constructeur privé. Pour créer un DirectDealing, il faut appler DirectDealing.getInstance().
     */
    private DirectDealing(){
        this.users = new HashMap<>();
        this.prets = new ArrayList<>();
        this.emprunts = new ArrayList<>();
        this.services = new ArrayList<>();
    }

    /**
     * ArrayList des pret
     */
    private ArrayList<Pret> prets;

    private ArrayList<Emprunt> emprunts;

    private ArrayList<Service> services;


    /**
     * Méthode pour le singleton
     * @return l'instance du DirectDealing
     */
    public static DirectDealing getInstance() {
        if (instance == null){
            instance = new DirectDealing();
            try {
                instance.loadUsers();
            } catch (NoSuchFileException e) {
                System.out.println("No users.json file existing.");
            }

        }
        return instance;
    }


    /**
     * Inscrit un utilisateur et le connecte automatiquement
     * @param username
     * @param mail
     * @param adresse
     * @param num
     * @param mdp
     */
    public User inscrireUser(String username, String mail, String adresse, String num, String mdp) throws UserAlreadyExistsException {
        if(users.containsKey(username))
            throw new UserAlreadyExistsException();

        User user = new User(username, mdp, num, mail, adresse);
        this.users.put(username, user);
        DAO.saveUser(user);
        return user;
    }

    /**
    * Get the registered users
    * */
    public void loadUsers() throws NoSuchFileException{
        // TODO decomment
        /*List<User> users = JsonUtils.savedUsers();
        for (User usr:users) {
            this.users.put(usr.getUsername(),usr);
        }*/
    }

    /**
     * Remove a user
     */
    public void removeUser(User user){
        users.remove(user.getUsername());
        JsonUtils.removeSavedUser(user);
    }


    /**
     * Deconnect a user
     */
    public void deconnexion(){
        this.activeUser = null;
        setIsUserConnectedProperty(false);
    }

    /**
     * Connecte un utilisateur depuis son username et son mot de passe
     * @param username
     * @param mdp
     * @throws NotAUserException exception throw si l'utilisateur n'existe pas
     * @throws WrongPasswordException exception throw si le mot de passe est faux
     */
    public void connectUser(String username, String mdp) throws NotAUserException, WrongPasswordException {
        if(!users.containsKey(username))
            throw new NotAUserException();
        User user = users.get(username);

        if (user.isPasswordMatching(mdp)){
            activeUser = user;
            setIsUserConnectedProperty(true);
        }
        else{
            throw new WrongPasswordException();
        }
    }


    public void changeUsername(User user, String newUsername) throws UserAlreadyExistsException {
        if(users.containsKey(newUsername))
            throw new UserAlreadyExistsException();

        this.users.remove(user.getUsername());
        user.setUsername(newUsername);
        this.users.put(newUsername, user);
    }



    // -------------------------- Getters & Setters -------------------------------

    /**
     * @return l'utilisateur connecté
     */
    public User getActiveUser(){
        return this.activeUser;
    }


    public boolean isIsUserConnectedProperty() {
        return isUserConnectedProperty.get();
    }

    public BooleanProperty isUserConnectedPropertyProperty() {
        return isUserConnectedProperty;
    }

    private void setIsUserConnectedProperty(boolean isUserConnectedProperty) {
        this.isUserConnectedProperty.set(isUserConnectedProperty);
    }

    public User getUser(String username) throws NotAUserException {
        for (User user: users.values()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        throw new NotAUserException();
    }

    public void setActiveUser(User user){
        this.activeUser = user;
        this.isUserConnectedProperty.setValue(true);
    }



    /**
     * @return la liste des prets
     */

    public ArrayList<Pret> getPrets(){
        return this.prets;
    }

    public void addPret(Pret pret){
        this.prets.add(pret);
    }

    public void removePret(Pret pret){
        this.prets.remove(pret);
    }

    public void addEmprunt(Emprunt emprunt){
        this.emprunts.add(emprunt);
    }

    public void removeEmprunt(Emprunt emprunt){
        this.emprunts.remove(emprunt);
    }

    public void addService(Service service){
        this.services.add(service);
    }

    public void removeService(Service service){
        this.services.remove(service);
    }

    public ArrayList<Emprunt> getEmprunts(){
        return this.emprunts;
    }

    public ArrayList<Service> getServices(){
        return this.services;
    }



    public Map<String, User> getUsers(){
        return this.users;
    }

    public void setUsers(Map<String,User> users){
        this.users = users;
    }
}
