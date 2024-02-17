package eu.telecomnancy.directdealing.exceptions;

public class NotAUserException extends Exception{

    public NotAUserException(){
        super("The user does not exists");
    }
}
