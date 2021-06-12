package game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Definisce le proprietà delle entità più complesse.
 */
public abstract class Entity extends BaseEntity
{
    final protected String description;
    final protected String afterUsed;
    final private List<String> alias;


    public Entity(UUID id, String name, String description, String afterUsed, List<String> aliasName)
    {
        super(id, name);
        this.description = description;
        this.afterUsed = afterUsed;
        this.alias = aliasName;
    }

    /**
     *
     * @return una stringa con la descrizione dell'entità.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     *
     * @return una lista di stringhe con i vari alias dell'entità.
     */
    public List<String> getAlias()
    {
        return alias != null ? alias : new ArrayList<>();
    }

    /**
     *
     * @return frase da stampare dopo aver utilizzato l'entità.
     */
    public String getAfterUsed()
    {
        return afterUsed;
    }
}
