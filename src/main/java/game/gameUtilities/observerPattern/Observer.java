package game.gameUtilities.observerPattern;

/**
 * Interfaccia generica per observer.
 * @param <Type> Observer di un tipo generico.
 */
public interface Observer<Type>
{
    public void onChanged(Type arg);
}
