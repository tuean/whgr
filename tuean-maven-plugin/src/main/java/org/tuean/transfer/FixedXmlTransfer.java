package org.tuean.transfer;

import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;
import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlContent;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaMethod;
import org.tuean.enums.InitMethod;
import org.tuean.util.Log;

import java.util.List;
import java.util.Set;

public class FixedXmlTransfer {


    public static XmlNode transferRouter(JavaMethod method, List<DBColumnInfo> dbColumnInfoList, String tableName, JavaClass entityClass) {
        if (method == null) return null;
        String methodName = method.getMethodName();
        Reflections reflections = new Reflections("org.tuean");
        Set<Class<? extends ITransfer>> classes = reflections.getSubTypesOf(ITransfer.class);
        if (CollectionUtils.isEmpty(classes)) return null;
        for (Class<? extends ITransfer> transferClass : classes) {
            try {
                ITransfer transfer = transferClass.newInstance();
                if (transfer.initMethod().name().equals(methodName)) {
                    return transfer.transfer(method, dbColumnInfoList, tableName, entityClass);
                }
            } catch (Exception var) {
                Log.getLog().error(var);
            }

        }
        return null;
    }

}
