package game.player.status;

import java.util.UUID;

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
