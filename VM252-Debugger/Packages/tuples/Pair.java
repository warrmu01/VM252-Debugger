package tuples;


public class Pair< FirstElementType, SecondElementType >
{

    private FirstElementType myFirst;
    private SecondElementType mySecond;

    public Pair(FirstElementType first, SecondElementType second)
    {
        setFirst(first);
        setSecond(second);
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

    }


