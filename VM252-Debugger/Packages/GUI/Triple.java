package GUI;


public class Triple< FirstElementType, SecondElementType, ThirdElementType >
{

    private FirstElementType myFirst;
    private SecondElementType mySecond;
    private ThirdElementType myThird;

    public Triple(FirstElementType first, SecondElementType second, ThirdElementType third)
    {
        setFirst(first);
        setSecond(second);
        setThird(third);
        }

    public void setFirst(FirstElementType other)
    {
        myFirst = other;
        }

    public FirstElementType first()
    {
        return myFirst;
        }

    public void setSecond(SecondElementType other)
    {
        mySecond = other;
        }

    public SecondElementType second()
    {
        return mySecond;
        }

    public void setThird(ThirdElementType other)
    {
        myThird = other;
        }

    public ThirdElementType third()
    {
        return myThird;
        }

    }


