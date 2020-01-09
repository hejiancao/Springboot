package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author created by shaos on 2019/11/11
 */
@Component
public class EsService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private String index = "api-test";
    private String type = "v1";

    private TransportClient client;

    public EsService(TransportClient client) {
        this.client = client;
    }



    /**组合查询
     * @author shaos
     * @date 2020/1/9 10:09
     */
    public void boolQuery() {
        int pageSize = 1000;
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("tenantId", "111"))
                .must(QueryBuilders.matchQuery("vin", "111"))
                .must(QueryBuilders.matchQuery("batteryPackCode", "111"))
                .must(QueryBuilders.rangeQuery("collectTime").gt("2020-01-02").lt("2020-01-03"));
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder)
                .setSearchType(SearchType.DEFAULT).setSize(pageSize).setScroll(TimeValue.timeValueMinutes(1))
                .addSort("time", SortOrder.DESC)
                .execute()
                .actionGet();
    }





    /**统计
     * @author shaos
     * @date 2020/1/9 10:03
     */
    public long count() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("tenantId", "11111"))
                .must(QueryBuilders.rangeQuery("collectTime").gt("2020-01-02").lt("2020-01-03"));
        long totalRows = client.prepareSearch(index).setTypes(type)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .get().getHits().getTotalHits();
        return totalRows;
    }



    /** 添加索引json
     * @author shaos
     * @date 2019/11/11 16:56
     */
    public void index() {
        JSONObject json = new JSONObject();
        json.put("user", "kimchy");
        json.put("postDate", new Date());
        json.put("message", "trying out Elasticsearch");
        IndexResponse response = client.prepareIndex(index, type, "1")
                .setSource(json, XContentType.JSON).get();

        LOGGER.info("index:{}", response.getIndex());
        LOGGER.info("type:{}", response.getType());
        LOGGER.info("id:{}", response.getId());
        LOGGER.info("version:{}", response.getVersion());
        LOGGER.info("status:{}", response.status());

    }

    /** 添加索引 jsonBuilder
     * @author shaos
     * @date 2019/11/11 16:56
     */
    public void index2() throws IOException {
        XContentBuilder json = jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();
        IndexResponse response = client.prepareIndex(index, type, "1")
                .setSource(json).get();

        LOGGER.info("index:{}", response.getIndex());
        LOGGER.info("type:{}", response.getType());
        LOGGER.info("id:{}", response.getId());
        LOGGER.info("version:{}", response.getVersion());
        LOGGER.info("status:{}", response.status());

    }


    /**检索
     * @author shaos
     * @date 2019/11/11 16:55
     */
    public void get() {
        GetResponse response = client.prepareGet(index, type, "1").get();
        Map<String, Object> source = response.getSource();
        String sourceAsString = response.getSourceAsString();
        LOGGER.info(sourceAsString);
    }

    /** 批量检索
     * 多获取API允许根据文档的索引，类型和ID获取文档列表
     * @author shaos
     * @date 2019/11/11 17:31
     */
    public void multiGet() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
//                .add("twitter", "tweet", "1")
//                .add("twitter", "tweet", "2", "3", "4")
//                .add("another", "type", "foo")
                .add(index, type, "1")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                LOGGER.info("json:{}", json);
            }
        }
    }


    /** 多条件查询
     * @author shaos
     * @date 2019/11/14 16:43
     */
    public void multisearch() {
        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        // You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }
        LOGGER.info("multisearch:{}", nbHits);
    }


    /** DSL查询详解
     * @author shaos
     * @date 2019/11/14 16:49
     */
    public void dsl() {

        /** QueryBuilders详解 */

        // matchQuery 用于执行全文查询的标准查询，包括模糊匹配和短语或接近查询。
        SearchRequestBuilder sr1 = client.prepareSearch().setQuery(QueryBuilders.matchQuery("address", "上海"));
        // multiMatchQuery 多字段匹配
        SearchRequestBuilder sr2 = client.prepareSearch().setQuery(QueryBuilders.multiMatchQuery("上海", "title", "address"));
        // termQuery 完全匹配
        SearchRequestBuilder sr6 = client.prepareSearch().setQuery(QueryBuilders.termQuery("key", "obj"));
        // termsQuery 一次匹配多个值
        SearchRequestBuilder sr7 = client.prepareSearch().setQuery(QueryBuilders.termsQuery("key", "obj1", "obj2"));
        // boolQuery 组合查询
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("user", "kimchy")) // and
                .mustNot(QueryBuilders.termQuery("message", "nihao")) //not
                .should(QueryBuilders.termQuery("gender", "male")); //or


        MultiSearchResponse resp = client.prepareMultiSearch().add(sr7).get();
        for (MultiSearchResponse.Item item : resp.getResponses()) {
            for (SearchHit documentFields : item.getResponse().getHits().getHits()) {
                LOGGER.info("查询结果:" + documentFields.getSourceAsString());
            }
        }

    }


    /** 删除
     * @author shaos
     * @date 2019/11/11 16:58
     */
    public void delete() {
        DeleteResponse response = client.prepareDelete(index, type, "1").get();
        LOGGER.info("status:{}", response.status());
    }

    /** 条件删除
     * @author shaos
     * @date 2019/11/11 17:08
     */
    public void deleteByQuery() {
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("user", "kimchy"))
                .source(index)
                .get();
        long deleted = response.getDeleted();
    }

    /** 条件删除 异步
     * @author shaos
     * @date 2019/11/11 17:08
     */
    public void deleteByQueryAsyn() {
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("user", "kimchy"))
                .source(index)
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });
    }


    /** 更新
     * @author shaos
     * @date 2019/11/11 17:25
     */
    public void update() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest(index, type, "1")
                .doc(jsonBuilder()
                        .startObject()
                        .field("user", "shaos")
                        .endObject());
        client.update(updateRequest).get();
    }





    /** 批量API允许在单个请求中索引和删除多个文档。这是一个示例用法
     * @author shaos
     * @date 2019/11/11 17:35
     */
    public void bulk() throws Exception {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        // either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex(index, type, "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex(index, type, "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }


}
