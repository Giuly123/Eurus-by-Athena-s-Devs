package game.player.status;

import game.entity.item.Item;

/**
 * Possibili stati dell'azione 'take'
 */
public enum TakeItemStatus
{
    taken,
    alreadyTaken,
    wrongItem;

    private Item item;

    /**
     *
     * @return l'item preso
     */
    public Item getItem()
    {
        return item;
    }

    /**
     * Imposta l'item da prendere.
     * @param item da impostare
     */
    public void setItem(Item item)
    {
        this.item = item;
    }
}