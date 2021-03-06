package game.managers.database;

import java.sql.*;
import java.util.Properties;

/**
 * Classe che gestisce il database locale.
 * Nel database locale vi sono le informazioni relative a:
 * - volume; - tempo di gioco.
 */
public class GameDatabaseManager
{
    private Connection connection;

    private static final String USERNAME_DB = "atena";
    private static final String PASSWORD_DB = "devs";

    private static final String PATH_DB = "./resources/db/gameDatabase";

    private static final String CREATE_TABLE = "create table if not exists CURRENTPLAYER (currentplayer varchar (20) " +
            "primary key not null," +
            "volume float" +
            "(20) not null, time" +
            " long(20) not null);";


    private static GameDatabaseManager instance;

    /**
     *
     * @return l'istanza della classe.
     * @throws Exception eccezione che si potrebbe generare
     */
    public static GameDatabaseManager getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new GameDatabaseManager();
        }
        return instance;
    }

    /**
     * Costruttore della classe che inizializza il database.
     * @throws Exception eccezione che si potrebbe generare
     */
    private GameDatabaseManager() throws Exception
    {
        try
        {
            Properties dbprops = new Properties();
            dbprops.setProperty("user", USERNAME_DB);
            dbprops.setProperty("password", PASSWORD_DB);
            connection = DriverManager.getConnection("jdbc:h2:" + PATH_DB, dbprops);
            initDB();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("Problema con il database");
        }
    }

    /**
     *
     * @param nameTable nome della tabella
     * @return la lunghezza dalla tabella
     * @throws SQLException eccezione che si potrebbe generare
     */
    private int getLenghTable(String nameTable) throws SQLException
    {
        int size = 0;
        Statement stm = null;
        ResultSet rs = null;

        try
        {
            stm = connection.createStatement();
            rs = stm.executeQuery("SELECT * FROM " + nameTable);

            if (rs != null)
            {
                rs.last();
                size = rs.getRow();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }

            if (stm != null)
            {
                stm.close();
            }
        }

        return size;
    }


    /**
     * Inizializza il database.
     * @throws Exception eccezione che si potrebbe generare
     */
    private void initDB() throws Exception
    {
        Statement stm = null;

        try
        {
            stm = connection.createStatement();
            stm.executeUpdate(CREATE_TABLE);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (stm != null)
            {
                stm.close();
            }
        }

        try
        {
            if (getLenghTable("CURRENTPLAYER") <= 0)
            {
                stm = connection.createStatement();

                stm.executeUpdate("INSERT INTO CURRENTPLAYER VALUES('currentplayer', -28.0, 0);");
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (stm != null)
            {
                stm.close();
            }
        }
    }

    /**
     * Effettua una query sul database e preleva il primo attributo della tabella.
     * @param nameAttribute nome dell'attributo da prendere
     * @param nameTable nome della tabella
     * @param <T> tipo generico
     * @return il valore dell'attributo preso dalla tabella.
     */
    public <T> T getValueFromTable(String nameAttribute, String nameTable)
    {
        return (T) getObjectFromTable(nameAttribute, nameTable);
    }

    /**
     * Effettua una query che aggiorna il valore di un attributo.
     * @param nameAttribute nome dell'attributo da aggiornare
     * @param nameTable nome della tabella
     * @param value valore da aggiornare
     * @param key chiave
     */
    public void updateValue(String nameAttribute, String nameTable, String key, String value)
    {
        StringBuilder queryString = new StringBuilder();
        queryString.append("UPDATE ");
        queryString.append(nameTable);
        queryString.append(" SET ");
        queryString.append(nameAttribute);
        queryString.append(" = ");
        queryString.append(value);
        queryString.append(" where currentplayer = '");
        queryString.append(key);
        queryString.append("'");

        Statement stm = null;

        try
        {
            stm = connection.createStatement();
            stm.executeUpdate(queryString.toString());

        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * Effettua una query che restituisce un attributo dalla tabella specificata.
     * @param nameAttribute nome dell'attributo da prendere
     * @param nameTable nome della tabella
     * @return l'attributo richiesto
     */
    private Object getObjectFromTable(String nameAttribute, String nameTable)
    {
        Object result = null;
        Statement stm = null;
        ResultSet rs = null;

        try
        {
            stm = connection.createStatement();
            rs = stm.executeQuery("SELECT " + nameAttribute + " FROM " + nameTable);

            while (rs.next())
            {
                result = rs.getObject(nameAttribute);
            }

        }
        catch (SQLException throwables)
        {
            return result;
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if (stm != null)
                {
                    stm.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

}
