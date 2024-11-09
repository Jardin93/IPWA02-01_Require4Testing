package ipwa02;

import jakarta.inject.Named;

@Named("Milch_test")
public class test
{
    private String name = "testname";

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
