package ipwa02;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("Service")
@SessionScoped
public class Service implements Serializable
{
    private List<String> einAusgabeListe = new ArrayList<>();
    public List<String> getEinAusgabeListe() {
        return einAusgabeListe;
    }
    public void setEinAusgabeListe(List<String> einAusgabeListe) {
        this.einAusgabeListe = einAusgabeListe;
    }

    private Personen angemeldetePerson;

    public Personen getAngemeldetePerson()
    {
        return angemeldetePerson;
    }
    public void setAngemeldetePerson(Personen angemeldetePerson)
    {
        this.angemeldetePerson = angemeldetePerson;
    }
    public boolean isLoggedIn()
    {
        return angemeldetePerson != null;
    }
    public String anmelden()
    {
        // anmeldeprozess
        return "index.xhtml?faces-redirect=true";
    }
    public String abmelden()
    {
        setAngemeldetePerson(null);
        return "login.xhtml?faces-redirect=true";
    }
    public String registrieren()
    {
        // registrierungsprozess
        return "index.xhtml?faces-redirect=true";
    }
    public String divAufgabenListe()
    {
        // html element, welches alle Aufgaben mit Metadaten und optionen in einer HTML Table anzeigt
        return "";
    }
    public String AufgabeSpeichern()
    {
        // html element, welches die zu bearbeitende Aufgabe in iframe lädt und die alten Werte in die eingabefelder vorlädt
        return "";
    }
    public void aufgabeSpeichern()
    {
        //wenn Aufgabe bereits existiert, dann überschreiben ansonsten neu anlegen. (einAusgabeListe nutzen)
    }
    public String getRolle()
    {
        if (angemeldetePerson == null) return "";
        return angemeldetePerson.getRole();
    }



    //************************Simulierte Daten werden hier im Konstruktor erstellt*****************************
    public Service()
    {

    }
}
