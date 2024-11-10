package ipwa02;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class Testlaeufe extends Aufgaben
{
    //@Column
    private List<Testfaelle> testfaelle = new ArrayList<Testfaelle>();

    public List<Testfaelle> getTestfaelle()
    {
        return testfaelle;
    }

    public void setTestfaelle(List<Testfaelle> testfaelle)
    {
        this.testfaelle = testfaelle;
    }
}
