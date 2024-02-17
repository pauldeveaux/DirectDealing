package eu.telecomnancy.directdealing.utils.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                System.out.println(databaseExists());
                if (!databaseExists()) {
                    // Si la base de données n'existe pas, configurez la création automatique
                    configuration.setProperty("hibernate.hbm2ddl.auto", "create");
                }

                sessionFactory = configuration.buildSessionFactory();
                sessionFactory.openSession();
                return sessionFactory;
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static boolean databaseExists() {
        String jdbcUrl = "jdbc:hsqldb:file:database";
        String username = "sa";
        String password = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            // Exécutez une requête pour vérifier l'existence de la table INFORMATION_SCHEMA.SYSTEM_TABLES
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES WHERE TABLE_NAME = 'SYSTEM_TABLES'");

            // Si la requête réussit, cela signifie que la base de données existe
            return resultSet.next();
        } catch (SQLException e) {
            // Une exception SQL indique généralement que la base de données n'existe pas
            return false;
        }
    }


    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}