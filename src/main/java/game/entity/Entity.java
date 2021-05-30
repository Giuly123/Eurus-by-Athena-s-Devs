package game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Entity
{
    final protected UUID id;
    final protected String name;
    final protected String description;
    final protected String afterUsed;
    final private List<String> alias;


    public Entity(UUID id, String name, String description, String afterUsed, List<String> aliasName)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.afterUsed = afterUsed;
        this.alias = aliasName;
    }

    public String getName()
    {
        return name;
    }

    public UUID getId() {return id;}

    public String getDescription()
    {
        return description;
    }

    public List<String> getAlias()
    {
        return alias != null ? alias : new ArrayList<>();
    }

    public String getAfterUsed()
    {
        return afterUsed;
    }
}
