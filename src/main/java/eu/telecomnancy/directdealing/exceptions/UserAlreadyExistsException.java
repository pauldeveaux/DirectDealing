package eu.telecomnancy.directdealing.exceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(){
        super("A user already exists with this username");
    }

}
