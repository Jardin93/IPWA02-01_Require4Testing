package ipwa02;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aufgaben
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ersteller;

    @Column(nullable = false)
    private String team;

    @Column(nullable = false)
    private Timestamp date;

    @Column(nullable = false)
    private Timestamp lastUpdate;

    @Column(nullable = false)
    private String titel;

    @Column
    private String beschreibung;

    @Column(nullable = false)
    private String status = "Auftrag erstellt";

    @ManyToMany
    @JoinTable(
            name = "aufgaben_user",
            joinColumns = @JoinColumn(name = "Aufgaben_id"),
            inverseJoinColumns = @JoinColumn(name = "Personen_id")
    )
    private List<Personen> zugeordneteUser = new ArrayList<Personen>();

    public Aufgaben(){}

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getErsteller()
    {
        return ersteller;
    }

    public String getTeam()
    {
        return team;
    }

    public Timestamp getDate()
    {
        return date;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public String getTitel()
    {
        return titel;
    }

    public String getBeschreibung()
    {
        return beschreibung;
    }

    public String getStatus()
    {
        return status;
    }

    public List<Personen> getZugeordneteUser()
    {
        return zugeordneteUser;
    }


    public void setZugeordneteUser(List<Personen> zugeordneteUser)
    {
        this.zugeordneteUser = zugeordneteUser;
    }

    public void setErsteller(String ersteller)
    {
        this.ersteller = ersteller;
    }

    public void setTeam(String team)
    {
        this.team = team;
    }

    public void setDate()
    {
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public void setLastUpdate()
    {
        this.lastUpdate = new Timestamp(System.currentTimeMillis());
    }

    public void setTitel(String titel)
    {
        this.titel = titel;
    }

    public void setBeschreibung(String beschreibung)
    {
        this.setLastUpdate();
        this.beschreibung = beschreibung;
    }

    public void setStatus(String Status)
    {
        this.setLastUpdate();
        this.status = status;
    }

}
