package com.tuean.whgr.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;
import java.util.List;

//@EnableWebMvc
@Configuration
//@Deprecated
public class HttpMessageConverterConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter converter = iterator.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) iterator.remove();
            if (converter instanceof MappingJackson2XmlHttpMessageConverter) iterator.remove();
            if (converter instanceof MappingJackson2SmileHttpMessageConverter) iterator.remove();
            if (converter instanceof MappingJackson2CborHttpMessageConverter) iterator.remove();
            if (converter instanceof FastJsonHttpMessageConverter) iterator.remove();
        }
        converters.add(new GsonHttpMessageConverter(gson));
    }

}
