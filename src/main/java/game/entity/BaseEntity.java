package game.entity;

import java.util.UUID;

/**
 * Definisce le proprietà base di ogni Entità.
 */
public abstract class BaseEntity
{
    final protected UUID id;
    final protected String name;

    public BaseEntity(UUID id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return il nome dell'entità.
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return l'UUID dell'entità.
     */
    public UUID getId() {return id;}

}
