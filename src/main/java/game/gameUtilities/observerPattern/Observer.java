package game.gameUtilities.observerPattern;

/**
 * Interfaccia generica per observer.
 * @param <Type> Observer di un tipo generico.
 */
public interface Observer<Type>
{
    /**
     * Azione che deve essere eseguita quando l'observer
     * viene notificato da un soggetto.
     * @param arg argomento
     */
    public void onChanged(Type arg);
}
