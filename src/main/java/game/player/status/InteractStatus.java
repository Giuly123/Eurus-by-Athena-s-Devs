package game.player.status;

import game.entity.interactable.Interactable;

/**
 * Possibili stati dell'azione 'interact'.
 */
public enum InteractStatus
{
    used,
    needItem,
    needAnswer,
    alreadyUsed,
    wrongInteractable;

    private Interactable interactable;

    /**
     *
     * @return l'iteractable
     */
    public Interactable getInteractable()
    {
        return interactable;
    }

    /**
     * Imposta l'interactable
     * @param interactable da impostare
     */
    public void setInteractable(Interactable interactable)
    {
        this.interactable = interactable;
    }
}
