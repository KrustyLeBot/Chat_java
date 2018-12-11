package Messages;
import main.*;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

    private final String mess;
    private final Date horodatation;
    private final User emetteur;

    public Message(String message, User emetteur)
    {
        this.mess = message;
        this.horodatation = new Date();
        this.emetteur = emetteur;

    }

    public String getMessage()
    {
        return this.mess;

    }

    public Date getHorodatation()
    {
        return this.horodatation;

    }

    public User getEmetteur() {
        return emetteur;
    }
}
