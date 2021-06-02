package game.gameUtilities.observerPattern;


import game.gameUtilities.observerPattern.Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject<Type>
{
    private List<Observer<Type>> observers = new ArrayList<>();
    private List<Observer<Type>> unregistered = new ArrayList<>();

    public void register(Observer<Type> observer)
    {
        observers.add(observer);
    }

    public void unregister(Observer<Type> observer)
    {
        unregistered.add(observer);
    }

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