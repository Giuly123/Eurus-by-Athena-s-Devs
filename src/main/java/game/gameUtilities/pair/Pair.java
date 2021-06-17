package game.gameUtilities.pair;

/**
 * Classe che prende due argomenti chiave, valore.
 * @param <K> tipo generico per la chiave
 * @param <V> tipo generico per il valore
 */
public class Pair<K, V> implements IPair<K, V>
{
    private K key;
    private V value;

    public Pair()
    {
        this.key = null;
        this.value = null;
    }

    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey()
    {
        return key;
    }

    @Override
    public V getValue()
    {
        return value;
    }


    public void setKey(K key)
    {
        this.key = key;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

}
