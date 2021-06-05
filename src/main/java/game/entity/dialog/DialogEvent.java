package game.entity.dialog;
import game.entity.BaseEntity;
import java.util.UUID;

public class DialogEvent extends BaseEntity
{
    private final String dialogText;

    public DialogEvent(UUID id, String name, String dialogText)
    {
        super(id, name);
        this.dialogText = dialogText;
    }

    public String getDialogText()
    {
        return dialogText;
    }
}
