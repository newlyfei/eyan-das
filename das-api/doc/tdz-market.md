# Elasticsearch索引

#### 创建索引
索引：tdz_market，索引类型：market
```
PUT /tdz_market
{
  "mappings": {
    "market": {
      "properties": {
        "name": {
          "type": "text"
        },
        "description": {
           "type": "text"
        },
        "imgId": {
            "type": "text"
        },
        "location": {
          "type":"geo_point"
        },
        "addtime":{
          "type":"date"
        }
      }
    }
  }
}
```

#### 查看索引
```
GET /tdz_market/_mapping
```