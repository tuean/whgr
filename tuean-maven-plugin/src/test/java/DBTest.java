import org.junit.Test;
import org.tuean.database.DatabaseGot;
import org.tuean.entity.DBColumnInfo;
import org.tuean.util.Log;

import java.util.List;
import java.util.Map;

public class DBTest {


//    @Test
    public static List<DBColumnInfo> testDBMap() {
        Prepare.init();
        String tableName = "menus";
        List<DBColumnInfo> infos = DatabaseGot.getTableColumnInfo(tableName);
        Log.getLog().info(infos.toString());
        return infos;
    }

}
