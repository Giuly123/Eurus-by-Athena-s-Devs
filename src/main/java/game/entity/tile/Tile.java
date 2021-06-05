package game.entity.tile;

import game.entity.dialog.DialogEvent;
import game.entity.guessingGame.GuessingGame;
import game.entity.interactable.Interactable;
import game.gameUtilities.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tile
{
    //final private boolean isLastTile;
    final private List<Coordinates> allowedDirections;
    final private String shortDescription;
    final private String fullDescription;
    final private String descriptionOffLimitsArea;
    final private String descriptionOfDarkRoom;
    final private DialogEvent dialogEvent;

    final private List<UUID> interactableHere;
    final private UUID interactableNeededToEnter;
    final private List<UUID> itemsToTake;
    final private UUID interactableToSwitchOnLight;

    final private boolean hasGuessingGame;

    final private boolean neededToSwitchOnLight;

    final private GuessingGame guessingGameToEnter;

    public Tile(List<Coordinates> allowedDirections,
                String shortDescription,
                String fullDescription,
                String descriptionOffLimitsArea,
                String descriptionOfDarkRoom,
                List<UUID> interactableHere,
                UUID interactableNeededToEnter,
                List<UUID> itemToTake,
                boolean hasGuessingGame,
                GuessingGame guessingGameToEnter,
                boolean neededToSwitchOnLight,
                UUID interactableToSwitchOnLight,
                DialogEvent dialogEvent
    )
    {
        this.allowedDirections = allowedDirections;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.descriptionOffLimitsArea = descriptionOffLimitsArea;

        this.interactableHere = interactableHere;
        this.interactableNeededToEnter = interactableNeededToEnter;
        this.itemsToTake = itemToTake;
        this.guessingGameToEnter = guessingGameToEnter;
        this.hasGuessingGame = hasGuessingGame;
        this.interactableToSwitchOnLight = interactableToSwitchOnLight;
        this.neededToSwitchOnLight = neededToSwitchOnLight;
        this.descriptionOfDarkRoom = descriptionOfDarkRoom;
        this.dialogEvent = dialogEvent;
    }

    public String getShortDescription()
    {
        return shortDescription != null ? shortDescription : "";
    }

    public String getFullDescription()
    {
        return fullDescription != null ? fullDescription : "";
    }

    public String getDescriptionOffLimitsArea()
    {
        return descriptionOffLimitsArea != null ? descriptionOffLimitsArea : "";
    }

    public List<Coordinates> getAllowedDirections()
    {
        return allowedDirections != null ? allowedDirections : new ArrayList<>();
    }

    public List<UUID> getInteractableHere()
    {
        return interactableHere != null ? interactableHere : new ArrayList<>();
    }

    public UUID getInteractableNeededToEnter()
    {
        return interactableNeededToEnter;
    }

    public List<UUID> getItemsToTake()
    {
        return itemsToTake != null ? itemsToTake : new ArrayList<>();
    }

    public GuessingGame getGuessingGameToEnter()
    {
        return guessingGameToEnter;
    }

    public boolean hasGuessingGame()
    {
        return hasGuessingGame;
    }

    public UUID getInteractableToSwitchOnLight() {
        return interactableToSwitchOnLight;
    }

    public boolean isNeededToSwitchOnLight() {
        return neededToSwitchOnLight;
    }

    public String getDescriptionOfDarkRoom() {
        return descriptionOfDarkRoom != null ? descriptionOfDarkRoom : "";
    }

    public DialogEvent getDialog()
    {
        return dialogEvent;
    }
}
