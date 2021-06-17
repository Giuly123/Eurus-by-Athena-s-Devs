package game.player.status;

import java.util.UUID;

/**
 * Classe che rappresenta gli argomenti dell'azione 'observe'.
 */
public class ObserveArgs
{
    public String text;
    public UUID dialogId;

    public ObserveArgs(String text, UUID dialogId)
    {
        this.text = text;
        this.dialogId = dialogId;
    }

}
