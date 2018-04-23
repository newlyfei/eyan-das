package com.eyan.dasapi.controller;

import com.eyan.dasapi.bean.TdzMarket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private TransportClient client;

    private final static ObjectMapper mapper = new ObjectMapper();
    private AtomicInteger no = new AtomicInteger(1000);

    private final static String TEST_INDEX = "tdz-market";
    private final static String TEST_TYPE = "market";

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public IndexResponse addPostion(@RequestParam String name,@RequestParam Double[] location) throws JsonProcessingException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        TdzMarket tdzMarket=new TdzMarket();
        tdzMarket.setName(name);
        tdzMarket.setAddtime(new Date());

//        Double[] location={-73.983,40.719};
        tdzMarket.setLocation(location);

        String contentJson = mapper.writeValueAsString(tdzMarket);
        return client.prepareIndex(TEST_INDEX, TEST_TYPE).setSource(contentJson, XContentType.JSON).get();
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public String findInfo(){
        SearchRequestBuilder srb1 = client.prepareSearch(TEST_INDEX).setTypes(TEST_TYPE);
//        SearchRequestBuilder srb2 = client.prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch().add(srb1).get();

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
            return response.toString();
        }
        return String.valueOf(nbHits);
    }

    @ResponseBody
    @RequestMapping(value = "/search2")
    public String findDistanceInfo(){

        SearchRequestBuilder srb1 = client.prepareSearch(TEST_INDEX).setTypes(TEST_TYPE)
                .setPostFilter(
                        QueryBuilders.geoDistanceQuery("location")
                                .point(40.715,-73.988)
                                .distance(1, DistanceUnit.KILOMETERS)

                );
//        SearchRequestBuilder srb2 = client.prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch().add(srb1).get();

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
            return response.toString();
        }
        return String.valueOf(nbHits);
    }
}
