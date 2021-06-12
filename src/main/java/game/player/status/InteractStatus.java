package game.player.status;

import game.entity.interactable.Interactable;

public enum InteractStatus
{
    used,
    needItem,
    needAnswer,
    alreadyUsed,
    wrongInteractable;

    private Interactable interactable;

    public Interactable getInteractable()
    {
        return interactable;
    }

    public void setInteractable(Interactable interactable)
    {
        this.interactable = interactable;
    }
}
