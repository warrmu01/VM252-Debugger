package tuples;


public class Quadruple<
    FirstElementType,
    SecondElementType,
    ThirdElementType,
    FourthElementType
    >
{

    private FirstElementType myFirst;
    private SecondElementType mySecond;
    private ThirdElementType myThird;
    private FourthElementType myFourth;

    public Quadruple(
        FirstElementType first,
        SecondElementType second,
        ThirdElementType third,
        FourthElementType fourth
        )
    {
        setFirst(first);
        setSecond(second);
        setThird(third);
        setFourth(fourth);
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

    public void setFourth(FourthElementType other)
    {
        myFourth = other;
        }

    public FourthElementType fourth()
    {
        return myFourth;
        }

    }


