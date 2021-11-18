import org.junit.Test;
import org.tuean.database.DatabaseGot;
import org.tuean.util.Log;

import java.util.Map;

public class DBTest {


    @Test
    public void testDBMap() {
        String tableName = "menus";
        Map<String, String> map = DatabaseGot.getTableColumnInfo(tableName);
        Log.getLog().info(map.toString());
    }

}
