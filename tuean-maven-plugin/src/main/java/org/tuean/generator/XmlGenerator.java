package org.tuean.generator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.tuean.consts.Consts;
import org.tuean.entity.XmlContent;
import org.tuean.entity.XmlNode;
import org.tuean.util.Util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;


public class XmlGenerator {


    public static void generateXml(XmlNode node, String filePath) throws IOException {
        FileOutputStream out = new FileOutputStream(filePath, false);

        out.write(Util.string2bytes("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"));
        out.write(Util.string2bytes("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n"));

//        Queue queue = new ArrayDeque();
        int blankSize = 0;
        out.write(Util.string2bytes(recursive(node, blankSize, true)));
    }


    private static String recursive(XmlNode node, int blankSize, boolean anotherLine) {
        StringBuffer sb = new StringBuffer();
        String tagName = node.getTag();
        String id = node.getId();
        // dialogue start
        sb.append(Util.blank(blankSize));
        // content start
        sb.append("<").append(tagName).append(Util.blank(1));

        if (node.getTagAttrs() != null) {
            if (node.getTagAttrs().containsKey("id")) {
                sb.append("id=\"").append(id).append("\"").append(Util.blank(1));
            }
            for (String key : node.getTagAttrs().keySet()) {
                if ("id".equals(key)) continue;
                sb.append(key).append(Consts.EQUAL).append("\"")
                        .append(node.getTagAttrs().get(key)).append("\"")
                        .append(Util.blank(1));
            }
        }

        if (CollectionUtils.isEmpty(node.getNodes()) && StringUtils.isBlank(node.getContent()) && CollectionUtils.isEmpty(node.getFixedContent())) {
            sb.append("/>");
            Util.nextLine(sb);
            return sb.toString();
        }


        sb.append(">");
        Util.nextLine(sb);

        // children
        if (node.getNodes() != null) {
            for (int x = 0; x < node.getNodes().size(); x++) {
                String cr = recursive(node.getNodes().get(x), blankSize + 2, false);
                sb.append(cr);
                if (!cr.endsWith("\n")) Util.nextLine(sb);
                if (anotherLine) Util.nextLine(sb);
                // remove last enter \n
//                if (x == node.getNodes().size() - 1) {
//                    sb.setLength(sb.length() - 3);
//                }
            }
        }

        // words
        if (!StringUtils.isBlank(node.getContent())) {
            sb.append(Util.blank(blankSize + 2));
            sb.append(node.getContent());
            Util.nextLine(sb);
        }

        // fixed content
        if (!CollectionUtils.isEmpty(node.getFixedContent())) {
            for (XmlContent content : node.getFixedContent()) {
                if (content == null) continue;
                sb.append(Util.blank(blankSize)).append(Util.blank(content.getBlankSize()));
                sb.append(content.getLine());
                Util.nextLine(sb);
            }
        }

        // content end
        sb.append(Util.blank(blankSize));
        sb.append("</").append(tagName).append(">");

        return sb.toString();
    }


}
