/**
 * Created by TuanDung on 10/19/2017.
 */
import java.io.FileOutputStream;
import java.util.Properties;

public class StoreDatabaseProperties {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("jdbc.username", "ericle_root");
        props.setProperty("jdbc.password", "123456789789");
        props.setProperty("jdbc.database", "heartbeat");
        props.setProperty("jdbc.db_url","jdbc:mysql://localhost/?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        props.setProperty("jdbc.db_params"," ");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("database.properties");
            props.store(out, "Database info");
            out.close();
        }
        catch (Exception e) {

        }
    }
}