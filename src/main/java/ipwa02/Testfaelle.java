package ipwa02;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Testfaelle extends Aufgaben
{
    @ManyToOne
    @JoinColumn(name = "anforderung_id")
    private Anforderungen anforderung;
    public Anforderungen getAnforderung()
    {
        return anforderung;
    }
    public void setAnforderung(Anforderungen anforderung)
    {
        this.anforderung = anforderung;
    }


    @ManyToOne
    @JoinColumn(name = "testlauf_id")
    private Testlaeufe testlauf;

    public Testlaeufe getTestlauf()
    {
        return testlauf;
    }

    public void setTestlauf(Testlaeufe testlauf)
    {
        this.testlauf = testlauf;
    }


    @Column(nullable = false)
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

    @ElementCollection
    @CollectionTable(name = "testschritte", joinColumns = @JoinColumn(name = "testfall_id"))
    @Column(name = "schritt")
    private List<String> Testschritte;

    public List<String> getTestschritte()
    {
        return Testschritte;
    }

    public void setTestschritte(List<String> testschritte)
    {
        this.Testschritte = testschritte;
    }

    @Column(nullable = false)
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
