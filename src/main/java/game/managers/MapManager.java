package game.managers;

import game.entity.tile.Tile;
import game.gameUtilities.Coordinates;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class MapManager
{
    private static MapManager instance;

    private boolean loaded = false;

    private Tile[][] matrixMap;
    private int dim;

    private MapManager() throws Exception
    {
        RootMapJson rootMapJson = loadMap();
        setupMap(rootMapJson.tiles);

        loaded = true;
    }

    public static MapManager getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new MapManager();
        }

        return instance;
    }


    public List<Tile> getContiguousTiles(int posRiga, int posColonna)
    {
        Tile currentTile = getTile(posRiga, posColonna);

        List<Tile> tiles = new ArrayList<>();

        Tile nord = getTile(posRiga + 1, posColonna);
        Tile sud = getTile(posRiga - 1, posColonna);
        Tile est = getTile(posRiga, posColonna + 1);
        Tile ovest = getTile(posRiga, posColonna - 1);

        if (nord != null && currentTile.getAllowedDirections().contains(Coordinates.North)) tiles.add(nord);
        if (sud != null && currentTile.getAllowedDirections().contains(Coordinates.South)) tiles.add(sud);
        if (est != null && currentTile.getAllowedDirections().contains(Coordinates.East)) tiles.add(est);
        if (ovest != null && currentTile.getAllowedDirections().contains(Coordinates.West)) tiles.add(ovest);

        return tiles;
    }



    public Tile getNextTile(int currentPosRiga, int currentPosColonna, Coordinates coordinates)
    {
        Tile tile = null;

        if (coordinates == Coordinates.North)
        {
            tile = getTile(currentPosRiga + 1, currentPosColonna);
        }
        else if (coordinates == Coordinates.South)
        {
            tile = getTile(currentPosRiga - 1, currentPosColonna);
        }
        else if (coordinates == Coordinates.East)
        {
            tile = getTile(currentPosRiga, currentPosColonna + 1);
        }
        else if (coordinates == Coordinates.West)
        {
            tile = getTile(currentPosRiga, currentPosColonna - 1);
        }

        return tile;
    }

    public Tile getTile(int posRiga, int posColonna)
    {
        Tile tile = null;

        if (posRiga >= 0 && posRiga < dim && posColonna >=0 && posColonna < dim)
        {
            tile = matrixMap[posRiga][posColonna];
        }

        return tile;
    }


    public boolean isPermittedMovement(int currentPositionRiga, int currentPositionColonna, Coordinates coordinatesShift)
    {
        boolean isPermitted = false;

        Tile currentTile = getTile(currentPositionRiga, currentPositionColonna);

        if (coordinatesShift == Coordinates.North)
        {
            if (!isOffTheMap(currentPositionRiga + 1, currentPositionColonna))
            {
                if (currentTile.getAllowedDirections().contains(Coordinates.North))
                {
                    isPermitted = true;
                }
            }
        }
        else if (coordinatesShift == Coordinates.South)
        {
            if (!isOffTheMap(currentPositionRiga - 1, currentPositionColonna))
            {
                if (currentTile.getAllowedDirections().contains(Coordinates.South))
                {
                    isPermitted = true;
                }
            }
        }
        else if (coordinatesShift == Coordinates.East)
        {
            if (!isOffTheMap(currentPositionRiga, currentPositionColonna + 1))
            {
                if (currentTile.getAllowedDirections().contains(Coordinates.East))
                {
                    isPermitted = true;
                }
            }
        }
        else if (coordinatesShift == Coordinates.West)
        {
            if (!isOffTheMap(currentPositionRiga, currentPositionColonna - 1))
            {
                if (currentTile.getAllowedDirections() != null && currentTile.getAllowedDirections().contains(Coordinates.West))
                {
                    isPermitted = true;
                }
            }
        }

        return isPermitted;
    }


    public boolean isOffTheMap(int currentPositionRiga, int currentPositionColonna)
    {
        return currentPositionColonna >= dim || currentPositionRiga >= dim || currentPositionColonna < 0 || currentPositionRiga < 0;
    }

    private void setupMap(List<Tile> map)
    {
        dim = (int)java.lang.Math.sqrt(map.size());

        matrixMap = new Tile[dim][dim];

        int  mapIndex = 0;

        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                matrixMap[i][j] = map.get(mapIndex);
                mapIndex++;
            }
        }
    }

    public int getDimension()
    {
        return dim;
    }

    private RootMapJson loadMap() throws Exception
    {
        RootMapJson rootMapJson = null;

        if (Utilities.fileExist(Utilities.MAP_JSON_PATH))
        {
            try
            {
                rootMapJson = JsonParser.GetClassFromJson(Utilities.MAP_JSON_PATH, RootMapJson.class);

            } catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file map.json");
            }
        }
        else
        {
            throw new Exception("File map.json non presente sul disco");
        }

        return rootMapJson;
    }


    private class RootMapJson
    {
        public List<Tile> tiles;
    }

}



