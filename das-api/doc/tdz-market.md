# Elasticsearch索引

#### 创建索引
索引：tdz-market，索引类型：market
```
PUT /tdz-market
{
  "mappings": {
    "market": {
      "properties": {
        "name": {
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
GET /tdz-market/_mapping
```