package Messages;
import main.*;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

    private final String mess;
    private final Date horodatation;
    private final User emetteur;
    private final User destinataire;

    public Message(String message, User emetteur, User destinataire)
    {
        this.mess = message;
        this.horodatation = new Date();
        this.emetteur = emetteur;
        this.destinataire = destinataire;

    }

    public Date getHorodatation()
    {
        return this.horodatation;

    }

    public User getEmetteur() {
        return emetteur;
    }
    
    public User getDestinataire() {
        return destinataire;
    }
    
    public String toTxt() {
    	return this.mess;
    }
}
