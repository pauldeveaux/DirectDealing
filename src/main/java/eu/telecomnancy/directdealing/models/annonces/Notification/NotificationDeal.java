package eu.telecomnancy.directdealing.models.annonces.Notification;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.deals.Deal;

public class NotificationDeal {
    private String message;
    private String date;
    private String heure;
    private Deal deal;

    public NotificationDeal(String message, String date, String heure, Deal deal){
        this.message = message;
        this.date = date;
        this.heure = heure;
        this.deal = deal;
    }

    public String getMessage(){
        return this.message;
    }

    public String getDate(){
        return this.date;
    }

    public String getHeure(){
        return this.heure;
    }

    public void setMessage(String message){
        this.message = message;
    }


    public void setDate(String date){
        this.date = date;
    }

    public void setHeure(String heure){
        this.heure = heure;
    }
}