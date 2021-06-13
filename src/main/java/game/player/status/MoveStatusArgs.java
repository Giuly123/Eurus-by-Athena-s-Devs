package game.player.status;

import game.entity.tile.Tile;
import game.gameUtilities.Coordinates;

/**
 * Classe che rappresenta gli argomenti dello stato MoveStatus.
 */
public class MoveStatusArgs
{
    public Coordinates coordinates;
    public Tile startTile;
    public Tile nextTile;
};
