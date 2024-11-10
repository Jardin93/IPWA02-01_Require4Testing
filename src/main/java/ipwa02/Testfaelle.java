package ipwa02;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Testfaelle extends Aufgaben
{
    //@Column
    private List<Testschritte> testschritte = new ArrayList<Testschritte>();;

    public List<Testschritte> getTestschritte()
    {
        return testschritte;
    }

    public void setTestschritte(List<Testschritte> testschritte)
    {
        this.testschritte = testschritte;
    }

    //@Column
    private Testlaeufe testlauf;

    public Testlaeufe getTestlauf()
    {
        return testlauf;
    }

    public void setTestlauf(Testlaeufe testlauf)
    {
        this.testlauf = testlauf;
    }


    /*
    @ManyToMany
    @JoinTable(
            name = "aufgaben_user",
            joinColumns = @JoinColumn(name = "Aufgaben_id"),
            inverseJoinColumns = @JoinColumn(name = "Personen_id")
    )*/
    private List<Personen> zugeordneteUser = new ArrayList<Personen>();

    public List<Personen> getZugeordneteUser()
    {
        return zugeordneteUser;
    }


    public void setZugeordneteUser(List<Personen> zugeordneteUser)
    {
        this.zugeordneteUser = zugeordneteUser;
    }




    //@Column(nullable = false)
    private String statusErgebnis = "Auftrag erstellt";

    public String getStatusErgebnis()
    {
        return statusErgebnis;
    }

    public void setStatusErgebnis(String statusErgebnis)
    {
        this.setLastUpdate();
        this.statusErgebnis = statusErgebnis;
    }



    //@Column(nullable = false)
    private Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public void setLastUpdate()
    {
        this.lastUpdate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public void setBeschreibung(String beschreibung)
    {
        this.setLastUpdate();
        this.beschreibung = beschreibung;
    }
}
