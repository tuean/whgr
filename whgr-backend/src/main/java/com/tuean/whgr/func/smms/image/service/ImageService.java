package com.tuean.whgr.func.smms.image.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tuean.whgr.func.smms.dto.BaseDTO;
import com.tuean.whgr.func.smms.dto.BaseDataDTO;
import com.tuean.whgr.func.smms.dto.BaseListDataDTO;
import com.tuean.whgr.func.smms.image.dto.ImageDataDTO;
import com.tuean.whgr.func.smms.properties.SmmsConstants;
import com.tuean.whgr.func.smms.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * Image 模块的接口
 * @author pupilcc
 */
@Service
public class ImageService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final HttpHeaders headers;
    private final RestTemplate restTemplate;

    public ImageService(HttpHeaders headers, RestTemplate restTemplate) {
        this.headers = headers;
        this.restTemplate = restTemplate;
    }

    /**
     * 图片上传
     * https://doc.sm.ms/#api-Image-Upload
     * @param file 图片文件
     * @param format Return Type: json or xml, the default value is json
     * @return Upload Image DTO
     */
    public BaseDataDTO<ImageDataDTO> uploadImage(MultipartFile file, String format) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>(2);
        paramMap.add("smfile", file.getResource());
        paramMap.add("format", format);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(paramMap, headers);

        String response = restTemplate.postForObject(
                SmmsConstants.URL_API + SmmsConstants.URL_UPLOAD_IMAGE, request, String.class);

        return JsonUtils.jsonToObj(new TypeReference<BaseDataDTO<ImageDataDTO>>(){}, response);
    }

    /**
     * 查询上传的历史记录
     * https://doc.sm.ms/#api-Image-Upload_History
     * @return Upload History DTO
     */
    public BaseListDataDTO<ImageDataDTO> uploadHistory() {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                SmmsConstants.URL_API + SmmsConstants.URL_UPLOAD_HISTORY,
                HttpMethod.GET, request, String.class);

        return JsonUtils.jsonToObj(
                new TypeReference<BaseListDataDTO<ImageDataDTO>>(){}, response.getBody());
    }

    /**
     * 删除图片
     * https://doc.sm.ms/#api-Image-Deletion
     * @param hash 图片的 hash
     * @param format Return Type: json or xml, the default value is json
     * @return BaseDTO 对象
     */
    public BaseDTO deleteImage(String hash, String format) {
        String url = SmmsConstants.URL_API + SmmsConstants.URL_DELETE +
                "/" + hash + "?format=" + format;
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return JsonUtils.jsonToObj(new BaseDTO(), response.getBody());
    }
}
