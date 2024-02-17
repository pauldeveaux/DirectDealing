package eu.telecomnancy.directdealing.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String folder = "DirectDealing";

    private static final String filename = "users.json";

    private static final String docuPath = System.getenv("APPDATA");

    private JsonUtils() {
    }

    public static void toJson(User user) {
        /*String filepath = docuPath + File.separator + folder + File.separator + filename;

        List<User> users;

        File jsonFile = new File(filepath);

        try {
            jsonFile.getParentFile().mkdirs();

            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                users = new ArrayList<>();
            } else {
                users = mapper.readValue(jsonFile, new TypeReference<List<User>>() {});
            }

            users.add(user);
            mapper.writeValue(jsonFile, users);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



    public static List<User> savedUsers() {
        List<User> userList = new ArrayList<>();

        String filepath = docuPath + File.separator + folder + File.separator + filename;


        if (!Files.exists(Paths.get(filepath)))
            return userList;

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filepath)));
            JSONArray usersArray = new JSONArray(jsonContent);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                User user = mapJsonToUser(userObject);
                userList.add(user);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static void removeSavedUser(User userToRemove) {
        String filepath = docuPath + File.separator + folder + File.separator + filename;

        List<User> users;

        File jsonFile = new File(filepath);

        try {
            if (!jsonFile.exists()) {
                return;
            }

            users = mapper.readValue(jsonFile, new TypeReference<List<User>>() {});

            users.removeIf(user -> user.getUsername().equals(userToRemove.getUsername()));

            mapper.writeValue(jsonFile, users); //on récupère tous les utilisateurs sous forme de liste, on supprime celui qu'il faut delete et on remet tout

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static User mapJsonToUser(JSONObject userObject) throws JSONException {
        String pseudo = userObject.getString("username");
        String password = userObject.getString("password");
        String phoneNumber = userObject.getString("num");
        String mail = userObject.getString("mail");
        String address = userObject.getString("adresse");
        String ppUrl = userObject.getString("imgUrl");
        int droits = userObject.getInt("droits");
        int solde = userObject.getInt("solde");
        JSONArray announces = userObject.getJSONArray("annonces");

        User user = new User(pseudo, password, phoneNumber, mail, address, ppUrl);
        user.setDroits(droits);
        user.setSolde(solde);
        user.setAnnonces(savedAnnonces(announces));
        return user;
    }

    private static List<Annonce> savedAnnonces(JSONArray annoncesArray) {
        List<Annonce> annonceList = new ArrayList<>();

        for (int i = 0; i < annoncesArray.length(); i++) {
            JSONObject annonceObject = annoncesArray.getJSONObject(i);
            Annonce annonce = mapJsonToAnnonce(annonceObject);
            annonceList.add(annonce);
        }
        return annonceList;
    }

    private static Annonce mapJsonToAnnonce(JSONObject annonceObject) throws JSONException {
        String titre = annonceObject.getString("titre");
        String description = annonceObject.getString("description");
        int prix = annonceObject.getInt("prix");
        String adresse = annonceObject.getString("adresse");
        String imgUrl = annonceObject.getString("imgUrl");
        boolean fini = annonceObject.getBoolean("fini");
        String authorPseudo = annonceObject.getJSONObject("author").getString("pseudo");
        String dateStr1 = annonceObject.getString("date_debut");
        LocalDate date_debut = LocalDate.parse(dateStr1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String dateStr2 = annonceObject.getString("date_fin");
        LocalDate date_fin = LocalDate.parse(dateStr2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String currencyImageUrl = annonceObject.getString("currencyImageUrl");
        return null;
        //return new Annonce(titre, description, prix, adresse, authorPseudo, date_debut, date_fin, currencyImageUrl);
    }
}
