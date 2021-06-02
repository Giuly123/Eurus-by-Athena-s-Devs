package game.gameUtilities.observerPattern;

public interface Observer<Type>
{
    public void onChanged(Type arg);
}
