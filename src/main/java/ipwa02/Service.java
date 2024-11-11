package ipwa02;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

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

    private List<Aufgaben> aufgaben;
    private Aufgaben ausgewaehlteAufgabe;

    public List<Aufgaben> getAufgaben() {
        if (aufgaben == null) {
            aufgaben = em.createQuery("SELECT a FROM Aufgaben a", Aufgaben.class).getResultList();
        }
        return aufgaben;
    }

    private List<Personen> testerListe;
    public List<Personen> getTesterListe()
    {
        testerListe = em.createQuery("SELECT p FROM Personen p WHERE p.role = :rolle AND p.team = :team", Personen.class)
                .setParameter("rolle", "Tester")
                .setParameter("team", angemeldetePerson.getTeam())
                .getResultList();
        return testerListe;
    }
    private Map<String,String> StringtesterNamensListe = new HashMap<>();
    public List<String> getStringtesterNamensListe()
    {
        List<String> ls = new ArrayList<>(StringtesterNamensListe.keySet());
        return ls;
    }

    public void setStringtesterNamensListe()
    {
        for (Personen p : testerListe)
        {
            StringtesterNamensListe.put(p.getUsername(),p.getId().toString());
        }
    }

    public String aufgabeBearbeiten(Aufgaben aufgabe) {
        ausgewaehlteAufgabe = aufgabe;
        return null;
    }

    public void aufgabeSpeichern() {
        if (!isLoggedIn() && einAusgabeListe.get(0) != "") {return;}
        try //Testen ob die Aufgabe schon existiert
        {
            ausgewaehlteAufgabe = em.createQuery("SELECT p FROM Anforderungen p WHERE p.titel = :titel AND p.team = :team", Anforderungen.class)
                    .setParameter("titel", einAusgabeListe.get(0))
                    .setParameter("team", einAusgabeListe.get(1))
                    .getSingleResult();
        }catch (Exception e) {}
        switch (angemeldetePerson.getRole())
        {
            case "Requirements Engineer":
                Anforderungen anforderung = new Anforderungen();
                if (ausgewaehlteAufgabe != null) {anforderung = (Anforderungen) ausgewaehlteAufgabe;} //wenn schon existiert, dann überschreiben
                anforderung.setTitel(einAusgabeListe.get(0));
                anforderung.setBeschreibung(einAusgabeListe.get(1));
                anforderung.setTeam(angemeldetePerson.getTeam());
                anforderung.setErsteller(angemeldetePerson);
                try { //Aufgabe speichern
                    em.getTransaction().begin();
                    em.persist(anforderung);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback(); // Rollback bei Fehler
                    }
                }
                break;
            case "Testmanager":
                Testlaeufe testlauf = new Testlaeufe();
                if (ausgewaehlteAufgabe != null) {testlauf = (Testlaeufe) ausgewaehlteAufgabe;} //wenn schon existiert, dann überschreiben
                testlauf.setTitel(einAusgabeListe.get(0));
                testlauf.setBeschreibung(einAusgabeListe.get(1));
                testlauf.setTeam(angemeldetePerson.getTeam());
                testlauf.setErsteller(angemeldetePerson);
                testlauf.setTester(em.find(Personen.class, einAusgabeListe.get(2)));
                testlauf.setTestfaelle(IdListeZuAufgabenListe(einAusgabeListe.get(3),Testfaelle.class));
                try { //Aufgabe speichern
                    em.getTransaction().begin();
                    em.persist(testlauf);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback(); // Rollback bei Fehler
                    }
                }
                break;
            case "Testfallersteller":
            case "Tester":
                Testfaelle testfall = new Testfaelle();
                if (ausgewaehlteAufgabe != null) {testfall = (Testfaelle) ausgewaehlteAufgabe;}
                testfall.setTitel(einAusgabeListe.get(0));
                testfall.setBeschreibung(einAusgabeListe.get(1));
                testfall.setTeam(angemeldetePerson.getTeam());
                testfall.setErsteller(angemeldetePerson);
                testfall.setAnforderung(IdZuAufgabe(Long.parseLong(einAusgabeListe.get(2)), Anforderungen.class));
                testfall.setTestschritte(einAusgabeListe.get(3)); //sind mit ";" getrennt
                testfall.setStatusErgebnis(einAusgabeListe.get(4));
                testfall.setTestlauf(IdZuAufgabe(Long.parseLong(einAusgabeListe.get(5)), Testlaeufe.class));
                try { //Aufgabe speichern
                    em.getTransaction().begin();
                    em.persist(testfall);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback(); // Rollback bei Fehler
                    }
                }
                break;
        }
        ausgewaehlteAufgabe = null;
        clearEinAusgabeListe();
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
            clearEinAusgabeListe();
            return "index.xhtml?faces-redirect=true";
        }
        clearEinAusgabeListe();
        return "index.xhtml?faces-redirect=true";
    }
    public String abmelden()
    {
        angemeldetePerson = null;
        clearEinAusgabeListe();
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
        clearEinAusgabeListe();
        return "index.xhtml?faces-redirect=true";
    }

    public String getRolle()
    {
        if (angemeldetePerson == null) return "";
        return angemeldetePerson.getRole();
    }

    public void clearEinAusgabeListe()
    {
        for (int i = 0; i < 10; i++)
        {
            einAusgabeListe.set(i, (""));
        }
    }

    public <T extends Aufgaben> T IdZuAufgabe(long id, Class <T> T)
    {
        try
        {
            return em.find(T, id);
        } catch (Exception e) {
            return null;
        }
    }

    public <T extends Aufgaben> List<T> IdListeZuAufgabenListe(String ids, Class <T> T)
    {
        List<T> aufgabenListe = new ArrayList<T>();
        for (String idString : ids.split(";"))
        {
            try {
                long id = Long.parseLong(idString);
                T testfall = IdZuAufgabe(id, T);
                if (testfall != null) {
                    aufgabenListe.add((T) testfall);
                }
            } catch (NumberFormatException e) {}
        }
        return aufgabenListe;
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
