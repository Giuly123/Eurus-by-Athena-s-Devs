package game.gameUtilities;

public interface Observer<Type>
{
    public void onChanged(Type arg);
}
