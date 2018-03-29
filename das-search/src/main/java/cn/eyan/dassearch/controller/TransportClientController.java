package cn.eyan.dassearch.controller;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Controller
public class TransportClientController {

    @ResponseBody
    @RequestMapping("/get")
    public GetResponse getTransport() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "myClusterName")   //如果您使用与“elasticsearch”不同的名称，则必须设置群集名称
                .put("client.transport.sniff", true)    //启用嗅探功能，可以动态添加新主机并删除旧主机
                .build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();

        client.close();

        return response;
    }

    @ResponseBody
    @RequestMapping("/add")
    public IndexResponse addJsonTransport() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "myClusterName")   //如果您使用与“elasticsearch”不同的名称，则必须设置群集名称
                .put("client.transport.sniff", true)    //启用嗅探功能，可以动态添加新主机并删除旧主机
                .build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

//        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
//                .setSource(jsonBuilder()
//                        .startObject()
//                        .field("user", "kimchy")
//                        .field("postDate", new Date())
//                        .field("message", "trying out Elasticsearch")
//                        .endObject()
//                ).get();

        String json = "{" +
                "\"user\":\"kimchy2\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        IndexResponse response = client.prepareIndex("twitter", "tweet")
                .setSource(json, XContentType.JSON)
                .get();

        // Index name
        String _index = response.getIndex();
        System.out.println(_index);
// Type name
        String _type = response.getType();
        System.out.println(_type);
// Document ID (generated or not)
        String _id = response.getId();
        System.out.println(_id);
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.
        RestStatus status = response.status();

        client.close();

        return response;
    }
}
