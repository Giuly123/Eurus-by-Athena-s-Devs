package game.gameUtilities.pair;

/**
 * Interfaccia per generico Pair formato
 * da chiave e valore.
 * @param <K> tipo generico per la chiave.
 * @param <V> tipo generico per il valore.
 */
public interface IPair<K, V>
{
    public K getKey();
    public V getValue();
}