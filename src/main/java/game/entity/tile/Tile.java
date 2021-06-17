package game.entity.tile;

import game.gameUtilities.Coordinates;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Rappresenta le entità Tile presenti nel file map.json.
 */
public class Tile
{
    final private List<Coordinates> allowedDirections;
    final private String shortDescription;
    final private String fullDescription;
    final private String descriptionOffLimitsArea;
    final private String descriptionOfDarkRoom;
    final private UUID dialogEventId;

    final private List<UUID> interactableHere;
    final private UUID interactableNeededToEnter;
    final private List<UUID> itemsToTake;
    final private UUID interactableToSwitchOnLight;
    
    final private boolean neededToSwitchOnLight;

    public Tile(List<Coordinates> allowedDirections,
                String shortDescription,
                String fullDescription,
                String descriptionOffLimitsArea,
                String descriptionOfDarkRoom,
                List<UUID> interactableHere,
                UUID interactableNeededToEnter,
                List<UUID> itemToTake,
                boolean neededToSwitchOnLight,
                UUID interactableToSwitchOnLight,
                UUID dialogEventId
    )
    {
        this.allowedDirections = allowedDirections;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.descriptionOffLimitsArea = descriptionOffLimitsArea;

        this.interactableHere = interactableHere;
        this.interactableNeededToEnter = interactableNeededToEnter;
        this.itemsToTake = itemToTake;
        this.interactableToSwitchOnLight = interactableToSwitchOnLight;
        this.neededToSwitchOnLight = neededToSwitchOnLight;
        this.descriptionOfDarkRoom = descriptionOfDarkRoom;
        this.dialogEventId = dialogEventId;
    }

    /**
     *
     * @return stringa con una descrizione parziale della stanza.
     */
    public String getShortDescription()
    {
        return shortDescription != null ? shortDescription : "";
    }

    /**
     *
     * @return stringa con una descrizione completa della stanza.
     */
    public String getFullDescription()
    {
        return fullDescription != null ? fullDescription : "";
    }

    /**
     *
     * @return stringa con una descrizione di una zona non accessibile.
     */
    public String getDescriptionOffLimitsArea()
    {
        return descriptionOffLimitsArea != null ? descriptionOffLimitsArea : "";
    }

    /**
     *
     * @return lista di cordinate ammesse.
     */
    public List<Coordinates> getAllowedDirections()
    {
        return allowedDirections != null ? allowedDirections : new ArrayList<>();
    }

    /**
     *
     * @return lista di UUID di interactables presenti nella stanza.
     */
    public List<UUID> getInteractableHere()
    {
        return interactableHere != null ? interactableHere : new ArrayList<>();
    }

    /**
     *
     * @return l'UUID dell' interactable da usare per accedere alla stanza.
     */
    public UUID getInteractableNeededToEnter()
    {
        return interactableNeededToEnter;
    }

    /**
     *
     * @return lista di UUID di item presenti nella stanza.
     */
    public List<UUID> getItemsToTake()
    {
        return itemsToTake != null ? itemsToTake : new ArrayList<>();
    }

    /**
     *
     * @return l'UUID dell'interactable che rende la stanza da illuminare.
     */
    public UUID getInteractableToSwitchOnLight() {
        return interactableToSwitchOnLight;
    }

    /**
     *
     * @return true se la stanza è da illuminare.
     */
    public boolean isNeededToSwitchOnLight() {
        return neededToSwitchOnLight;
    }

    /**
     *
     * @return una stringa con la descrizione della stanza da illuminare.
     */
    public String getDescriptionOfDarkRoom() {
        return descriptionOfDarkRoom != null ? descriptionOfDarkRoom : "";
    }

    /**
     *
     * @return l'UUID del dialogo presente nella stanza.
     */
    public UUID getDialogId()
    {
        return dialogEventId;
    }
}
