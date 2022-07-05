package org.tuean.transfer.impls;

import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlContent;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaMethod;
import org.tuean.enums.InitMethod;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.enums.SqlType;
import org.tuean.transfer.ITransfer;
import org.tuean.util.Log;
import org.tuean.util.Util;

import java.util.*;

import static org.tuean.consts.Consts.MAPPER_BLANK;
import static org.tuean.consts.Consts.PRIMARY;

public class UpdateByPrimaryKeySelective implements ITransfer {


    @Override
    public XmlNode transfer(JavaMethod method, List<DBColumnInfo> dbColumnInfoList, String tableName, JavaClass entityClass) {
        XmlNode node = new XmlNode();
        node.setId(method.getMethodName());
        node.setTag(SqlType.update.name());

        int tier = 1;
        Map<String, Object> tagAttrs = new HashMap<>();
        tagAttrs.put("id", method.getMethodName());
        tagAttrs.put("parameterType", entityClass.getPackageInfo() + "." + method.getArgs().get(0).getArgClassStr());

        DBColumnInfo keyColumn = dbColumnInfoList.stream().filter(n -> PRIMARY.equals(n.getKey())).findFirst().orElse(null);
        if (keyColumn == null) {
            Log.getLog().warn("table " + tableName + "has no primary key");
            return null;
        }

        String pri = keyColumn.getName();
        tagAttrs.put("useGeneratedKeys", "true");
        tagAttrs.put("keyProperty", pri);

        node.setTagAttrs(tagAttrs);

        List<XmlContent> fixedContents = new LinkedList<>();
        XmlContent xmlContent = new XmlContent(tier * MAPPER_BLANK, "update " + tableName);
        fixedContents.add(xmlContent);
        xmlContent = new XmlContent(tier * MAPPER_BLANK, "<set>");
        fixedContents.add(xmlContent);
        tier++;
        for (int x = 0; x < dbColumnInfoList.size(); x++) {
            String name = dbColumnInfoList.get(x).getName();
            String fieldName = Util.dbColumn2JavaField(dbColumnInfoList.get(x).getName());
            String fieldJdbcType = JdbcTypeEnum.getMybatisByDBType(dbColumnInfoList.get(x).getType());
            if (pri.equals(name)) continue;

            String ifContent = "<if test =\"" + fieldName + " != null\">";
            xmlContent = new XmlContent(tier * MAPPER_BLANK, ifContent);
            fixedContents.add(xmlContent);
            tier++;

            String sql = name + " = #{" + fieldName + ", jdbcType=" + fieldJdbcType + "}";
            if (x != dbColumnInfoList.size() - 1) sql = sql + ",";
            xmlContent = new XmlContent(tier * MAPPER_BLANK, sql);
            fixedContents.add(xmlContent);
            tier--;

            xmlContent = new XmlContent(tier * MAPPER_BLANK, "</if>");
            fixedContents.add(xmlContent);
        }
        tier--;
        xmlContent = new XmlContent(tier * MAPPER_BLANK, "</set>");
        fixedContents.add(xmlContent);

        xmlContent = new XmlContent(tier * MAPPER_BLANK, "where " + pri + " = #{" + Util.dbColumn2JavaField(pri) + ", jdbcType=" + JdbcTypeEnum.getMybatisByDBType(keyColumn.getType()) + "}");
        fixedContents.add(xmlContent);

        node.setFixedContent(fixedContents);
        return node;
    }

    @Override
    public InitMethod initMethod() {
        return InitMethod.updateByPrimaryKeySelective;
    }
}
