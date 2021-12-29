package org.tuean.parser.xml;

import com.sun.tools.javac.util.Assert;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tuean.entity.XmlNode;
import org.tuean.parser.IParser;
import org.tuean.util.Log;
import org.tuean.util.ParserXmlUtil;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        // mapper
        Assert.check(mapperEls.size() == 0);
        Element mapperEl = mapperEls.first();
        String mapperTag = mapperEl.tagName();
        Map<String, Object> tagAttrs = ParserXmlUtil.attributes2Map(mapperEl.attributes());

        // children
        List<XmlNode> list = new ArrayList<>();


        XmlNode entity = new XmlNode(mapperTag, tagAttrs, list, null);
        return entity;
    }
}
