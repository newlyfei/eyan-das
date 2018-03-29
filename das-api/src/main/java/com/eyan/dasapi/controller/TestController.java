package com.eyan.dasapi.controller;

import com.eyan.dasapi.bean.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.RandomStringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {
    @Autowired
    private TransportClient client;

    private final static ObjectMapper mapper = new ObjectMapper();
    private AtomicInteger no = new AtomicInteger(1000);

    private final static String TEST_INDEX = "es-docs";
    private final static String TEST_TYPE = "cargo";

    @RequestMapping("/add")
    @ResponseBody
    public IndexResponse add() throws JsonProcessingException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        User user=new User(String.valueOf(no), RandomStringUtils.randomAlphabetic(6), new Date());

        String userStr = mapper.writeValueAsString(user);
        return client.prepareIndex(TEST_INDEX, TEST_TYPE).setSource(userStr, XContentType.JSON).get();
    }
}
