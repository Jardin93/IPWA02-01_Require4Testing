package ipwa02;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IPWA02_DB");
    private EntityManager em = emf.createEntityManager();

    private Personen angemeldetePerson = null;

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
        try
        {
            angemeldetePerson = em.createQuery("SELECT p FROM Personen p WHERE p.username = :username", Personen.class)
                    .setParameter("username", einAusgabeListe.get(0))
                    .getSingleResult();
            if (!Objects.equals(angemeldetePerson.getPassword(), einAusgabeListe.get(1)))
            {
                angemeldetePerson = null;
            }
        }
        catch (Exception e) {
            return "index.xhtml?faces-redirect=true";
        }
        return "index.xhtml?faces-redirect=true";
    }
    public String abmelden()
    {
        angemeldetePerson = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "LoginRegister.xhtml?faces-redirect=true";
    }
    public String registrieren()
    {
        if (Objects.equals(einAusgabeListe.get(1), einAusgabeListe.get(2)))  //Passwort übereinstimmung
        {
            try  //Suche ob Benutzername vergeben ist
            {
                angemeldetePerson = em.find(Personen.class, einAusgabeListe.get(0));
            }
            catch (Exception e) {}
            if (angemeldetePerson == null) //Benutzer anlegen
            {
                angemeldetePerson = new Personen();
                angemeldetePerson.setUsername(einAusgabeListe.get(0));
                angemeldetePerson.setPassword(einAusgabeListe.get(1));
                angemeldetePerson.setTeam(einAusgabeListe.get(3));
                angemeldetePerson.setRole(einAusgabeListe.get(4));
                try { //Benutzer speichern
                    em.getTransaction().begin();
                    em.persist(angemeldetePerson);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback(); // Rollback bei Fehler
                    }
                    angemeldetePerson = null;
                }
            }
        }
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
        einAusgabeListe = new ArrayList<>();
        // Initialisiere die Liste mit der Anzahl der benötigten Elementen
        for (int i = 0; i < 10; i++)
        {
            einAusgabeListe.add("");
        }
    }
}
