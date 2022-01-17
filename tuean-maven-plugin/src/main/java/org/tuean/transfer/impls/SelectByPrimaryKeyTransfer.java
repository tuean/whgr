package org.tuean.transfer.impls;

import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlContent;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaMethod;
import org.tuean.enums.InitMethod;
import org.tuean.enums.SqlType;
import org.tuean.transfer.ITransfer;
import org.tuean.util.Log;
import org.tuean.util.Util;

import java.util.*;

import static org.tuean.consts.Consts.MAPPER_BLANK;
import static org.tuean.consts.Consts.PRIMARY;

public class SelectByPrimaryKeyTransfer implements ITransfer {

    @Override
    public XmlNode transfer(JavaMethod method, List<DBColumnInfo> dbColumnInfoList, String tableName, JavaClass entityClass) {
        XmlNode node = new XmlNode();
        node.setId(method.getMethodName());
        node.setTag(SqlType.select.name());

        int tier = 1;
        Map<String, Object> tagAttrs = new HashMap<>();
        tagAttrs.put("id", method.getMethodName());
        tagAttrs.put("parameterType", Util.getBaseWholeClass(method.getArgs().get(0).getArgClassStr()));
        tagAttrs.put("resultMap", "BaseResultMap");
        node.setTagAttrs(tagAttrs);

        DBColumnInfo keyColumn = dbColumnInfoList.stream().filter(n -> PRIMARY.equals(n.getKey())).findFirst().orElse(null);
        if (keyColumn == null) {
            Log.getLog().warn("table " + tableName + "has no primary key");
            return null;
        }

        String pri = keyColumn.getName();

        List<XmlContent> fixedContents = new LinkedList<>();

        XmlContent xmlContent = new XmlContent(tier * MAPPER_BLANK, "select <include refid=\"Base_Column_List\" /> ");
        fixedContents.add(xmlContent);
        xmlContent = new XmlContent(tier * MAPPER_BLANK, "from " + tableName);
        fixedContents.add(xmlContent);
        xmlContent = new XmlContent(tier * MAPPER_BLANK, "where " + pri + " = #{" + Util.dbColumn2JavaField(pri) + ", jdbcType=" + keyColumn.getType().toUpperCase(Locale.ROOT) + "}");
        fixedContents.add(xmlContent);

        node.setFixedContent(fixedContents);
        return node;
    }

    @Override
    public InitMethod initMethod() {
        return InitMethod.selectByPrimaryKey;
    }
}
