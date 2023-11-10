package Bean;

import java.sql.SQLException;

public class FctProtocole
{
    private static FctProtocole instance = null;
    private FctProtocole()
    {
        DatabaseConnection dbConnect;
        try
        {
            dbConnect = new DatabaseConnection(DatabaseConnection.MYSQL,
                    "192.168.137.129",
                    "PourStudent",
                    "Student",
                    "PassStudent1_");
            System.out.println("db Connect réussie !");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static FctProtocole getInstance()
    {
        if(instance == null)
        {
                instance = new FctProtocole();
            System.out.println("je suis dans le constructeur du singleton");
        }
        return instance;
    }




}
