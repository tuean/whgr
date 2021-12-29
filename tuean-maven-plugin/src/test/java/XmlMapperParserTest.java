import org.junit.Test;
import org.tuean.entity.XmlNode;
import org.tuean.parser.xml.XmlFileParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XmlMapperParserTest {

    @Test
    public void test() {
        String file = "/Users/tuean/IdeaProjects/whgr/whgr-backend/src/main/resources/mapper/TodoInfoMapper.xml";
        XmlFileParser parser = new XmlFileParser();
        try {
            XmlNode node = parser.parser(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
