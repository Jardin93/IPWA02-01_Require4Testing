package ipwa02;

import jakarta.persistence.Column;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Testfaelle extends Aufgaben
{
    //@Column
    private Anforderungen anforderung;
    public Anforderungen getAnforderung()
    {
        return anforderung;
    }
    public void setAnforderung(Anforderungen anforderung)
    {
        this.anforderung = anforderung;
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

    //@Column
    private Personen tester;

    public Personen getZugeordneteUser()
    {
        return tester;
    }

    public void setZugeordneteUser(Personen tester)
    {
        this.tester = tester;
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

    //@Column
    private List<String> Testschritte;

    public List<String> getTestschritte()
    {
        return Testschritte;
    }

    public void setTestschritte(List<String> testschritte)
    {
        this.Testschritte = testschritte;
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
