package game.entity.dialog;
import game.entity.BaseEntity;
import java.util.UUID;

/**
 * Rappresenta le entità dialogo presenti nel file dialogues.json.
 */
public class DialogEvent extends BaseEntity
{
    private final String dialogText;

    public DialogEvent(UUID id, String name, String dialogText)
    {
        super(id, name);
        this.dialogText = dialogText;
    }

    /**
     *
     * @return il dialogo
     */
    public String getDialogText()
    {
        return dialogText;
    }
}
