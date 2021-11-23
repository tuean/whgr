import org.junit.Test;
import org.tuean.database.DatabaseGot;
import org.tuean.util.Log;

import java.util.Map;

public class DBTest {


    @Test
    public static Map<String, String> testDBMap() {
        Prepare.init();
        String tableName = "menus";
        Map<String, String> map = DatabaseGot.getTableColumnInfo(tableName);
        Log.getLog().info(map.toString());
        return map;
    }

}
