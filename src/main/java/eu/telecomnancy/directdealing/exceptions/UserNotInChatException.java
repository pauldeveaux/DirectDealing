package eu.telecomnancy.directdealing.exceptions;

public class UserNotInChatException extends Exception{

    public UserNotInChatException(){
        super("This user is not in the chat.");
    }

}
