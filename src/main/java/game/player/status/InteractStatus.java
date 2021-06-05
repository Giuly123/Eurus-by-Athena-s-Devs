package game.player.status;

import game.entity.interactable.Interactable;

public enum InteractStatus
{
    used,
    needItem,
    needAnswer,
    alreadyUsed,
    wrongInteractable;

    public Interactable interactable;
}
