package com.eyan.dasapi.controller;

import com.eyan.dasapi.bean.Location;
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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/market")
public class MarketController {
    private static Logger logger= LoggerFactory.getLogger(MarketController.class);
    @Autowired
    private TransportClient client;

    private final static ObjectMapper mapper = new ObjectMapper();
    private AtomicInteger no = new AtomicInteger(1000);

    private final static String TEST_INDEX = "tdz_market";
    private final static String TEST_TYPE = "market";

    /**
     * 添加索引数据
     * @param name
     * @param description
     * @param lat
     * @param lon
     * @return
     * @throws JsonProcessingException
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public IndexResponse addPostion(@RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam Double lat,
                                    @RequestParam Double lon) throws JsonProcessingException {

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        TdzMarket tdzMarket=new TdzMarket();
        tdzMarket.setName(name);
        tdzMarket.setDescription(description);
        tdzMarket.setAddtime(new Date());

        Location location=new Location(lat,lon);
        tdzMarket.setLocation(location);

        String contentJson = mapper.writeValueAsString(tdzMarket);
        return client.prepareIndex(TEST_INDEX, TEST_TYPE).setSource(contentJson, XContentType.JSON).get();
    }

    /**
     * 搜索数据
     * @return
     */
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

    /**
     * 搜索周边距离内的数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/distance/search")
    public List findDistanceInfo(@RequestParam Double lat, @RequestParam Double lon){
        logger.info("request position info,lat={} lon={}",lat,lon);
        List result=new ArrayList();
        SearchRequestBuilder srb1 = client.prepareSearch(TEST_INDEX).setTypes(TEST_TYPE)
                .setPostFilter(
                        QueryBuilders.geoDistanceQuery("location")
                                .point(lat,lon)
                                .distance(2, DistanceUnit.KILOMETERS)

                );

//        SearchRequestBuilder srb2 = client.prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch().add(srb1).get();

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
//            logger.
            SearchHits hits=response.getHits();
            for(SearchHit hit:hits){
                Map<String,Object> itemHit=hit.getSourceAsMap();
                result.add(itemHit);
            }
            nbHits += response.getHits().getTotalHits();
            return result;
        }
        return result;
    }
}
