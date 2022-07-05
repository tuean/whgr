package org.tuean.parser.xml;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tuean.entity.XmlNode;
import org.tuean.parser.IParser;
import org.tuean.util.Log;
import org.tuean.util.ParserXmlUtil;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class XmlFileParser implements IParser<XmlNode> {


    @Override
    public XmlNode parser(InputStream in) {
        try {
            List<String> lines = IOUtils.readLines(in, Charset.defaultCharset());
            return parser(lines);
        } catch (Exception var) {
            Log.getLog().error(var);
            throw new RuntimeException("parse java file error");
        }
    }

    @Override
    public XmlNode parser(List<String> textLines) {
        String text = String.join(" ", textLines);
        Document doc = Jsoup.parse(text);
        Elements mapperEls = doc.select("mapper");

        if (mapperEls.size() < 1) {
            return null;
        }

        // mapper element
        if (mapperEls.size() > 1) {
            throw new RuntimeException("multi mapper tag");
        }
        Element mapperEl = mapperEls.first();

        // we want only mapper info
        XmlNode mapperNode = ParserXmlUtil.parse(mapperEl);

        return mapperNode;
    }
}
