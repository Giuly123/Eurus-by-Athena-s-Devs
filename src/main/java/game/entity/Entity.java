package game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
