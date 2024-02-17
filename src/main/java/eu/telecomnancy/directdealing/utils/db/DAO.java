package eu.telecomnancy.directdealing.utils.db;

import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.models.deals.Deal;

import eu.telecomnancy.directdealing.models.annonces.Service;
import eu.telecomnancy.directdealing.models.evaluation.Evaluation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DAO {
    public static void saveUser(User u){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(u);
        transaction.commit();
    }

    public static Map<String, User> getAllUsersByUsername(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Map<String, User> users = session.createQuery("FROM User", User.class).list().stream().collect(java.util.stream.Collectors.toMap(User::getUsername, user -> user));
        transaction.commit();
        return users;
    }

    public static List<User> getAllUsers(){
        return HibernateUtil.getSessionFactory().getCurrentSession().createQuery("FROM User", User.class).list();
    }

    public static User getUser(String username){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, username);
        transaction.commit();
        return user;
    }

    public static List<User> getUserByEval(double threshold) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<Evaluation> evaluations = session.createQuery("FROM Evaluation", Evaluation.class)
                .list();

        // Group evaluations by user
        Map<User, List<Evaluation>> evaluationsByUser = evaluations.stream()
                .collect(Collectors.groupingBy(Evaluation::getUser));

        // Filter users with average rating above the threshold
        List<User> selectedUsers = evaluationsByUser.entrySet().stream()
                .filter(entry -> calculateAverageRating(entry.getValue()) > threshold)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        transaction.commit();
        return selectedUsers;
    }

    private static double calculateAverageRating(List<Evaluation> evaluations) {
        if (evaluations.isEmpty()) {
            return 0.0;
        }

        double sum = evaluations.stream()
                .mapToDouble(Evaluation::getNote)
                .sum();

        return sum / evaluations.size();
    }


    public static void updateUser(User u){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(u);
        transaction.commit();
    }

    public static void deleteUser(User u){
        HibernateUtil.getSessionFactory().getCurrentSession().delete(u);
    }

    public static void deleteAnnonce(Annonce a){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        System.out.println(a.getId());
        session.createQuery("DELETE FROM Annonce WHERE id = :id").setParameter("id", a.getId()).executeUpdate();
        transaction.commit();
    }

    public static List<Annonce> getAllAnnonces(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Annonce> annonces = session.createQuery("FROM Annonce", Annonce.class).list();
        transaction.commit();
        return annonces;
    }

    public static void saveAnnonce(Annonce a){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(a);
        transaction.commit();
    }

    // DEAL

    public static void saveDeal(Deal deal){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(deal);
        transaction.commit();
    }

    public static void saveEvaluation(Evaluation evaluation){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(evaluation);
        transaction.commit();
    }

    public static void updateDeal(Deal deal){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(deal);
        transaction.commit();
    }

    public static void deleteDeal(Deal deal){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Deal WHERE id = :id").setParameter("id", deal.getId()).executeUpdate();
        transaction.commit();
    }

    public static List<Deal> getAllDeal(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Deal> emprunts = session.createQuery("FROM Deal", Deal.class).list();
        transaction.commit();
        return emprunts;
    }


    public static void updateAnnonce(Annonce a){
        HibernateUtil.getSessionFactory().getCurrentSession().update(a);
    }

    public static Annonce getAnnonce(int id){
        return HibernateUtil.getSessionFactory().getCurrentSession().get(Annonce.class, id);
    }

    public static List<Pret> getAllPrets(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Pret> prets = session.createQuery("FROM Pret", Pret.class).list();
        transaction.commit();
        return prets;
    }

    public static List<Emprunt> getAllEmprunts(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Emprunt> emprunts = session.createQuery("FROM Emprunt", Emprunt.class).list();
        transaction.commit();
        return emprunts;
    }

    public static List<Service> getAllServices(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Service> services = session.createQuery("FROM Service", Service.class).list();
        transaction.commit();
        return services;
    }



}
