package game.gameUtilities.observerPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe soggetto per implementazione del pattern Observer.
 * @param <Type> tipo generico.
 */
public class Subject<Type>
{
    private List<Observer<Type>> observers = new ArrayList<>();
    private List<Observer<Type>> unregistered = new ArrayList<>();

    /**
     * Registra un observer al soggetto.
     * @param observer observer da registrare
     */
    public void register(Observer<Type> observer)
    {
        observers.add(observer);
    }

    /**
     * Unregistra un observer al soggetto.
     * @param observer observer da unregistrare
     */
    public void unregister(Observer<Type> observer)
    {
        unregistered.add(observer);
    }

    /**
     * Notifica tutti gli observer che sono registrati al
     * soggetto di un avvenuto cambiamento.
     * @param arg argomento passato agli observer
     */
    public void notifyObservers(Type arg)
    {
        observers.removeAll(unregistered);
        unregistered.clear();

        for (int i = 0; i < observers.size(); ++i)
        {
            observers.get(i).onChanged(arg);
        }
    }

}