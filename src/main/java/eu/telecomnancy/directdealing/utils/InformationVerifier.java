package eu.telecomnancy.directdealing.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformationVerifier {

    public static boolean isValidPhoneNumber(String numero){
        String regex = "^(0|\\+33|0033)[1-9]([-. ]?[0-9]{2}){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);

        return matcher.matches();
    }

    public static boolean isValidPseudo(String pseudo){
        return pseudo.length() >= 3;
    }

    public static boolean isValidEmail(String email){
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length()>=5;
    }


    public static void formatIntTextField(TextField text){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        text.setTextFormatter(textFormatter);
        text.setText("1");
    }
}
