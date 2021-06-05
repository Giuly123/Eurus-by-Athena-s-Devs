package game.entity;

import java.util.UUID;

public abstract class BaseEntity
{
    final protected UUID id;
    final protected String name;

    public BaseEntity(UUID id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public UUID getId() {return id;}

}
