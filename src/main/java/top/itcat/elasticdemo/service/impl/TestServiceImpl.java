package top.itcat.elasticdemo.service.impl;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;
import top.itcat.elasticdemo.bean.Product;
import top.itcat.elasticdemo.config.Jdutil;
import top.itcat.elasticdemo.service.ITestService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yan.zhang
 * @date 2021/1/5
 */
@Service
public class TestServiceImpl implements ITestService {

    @Resource
    private RestHighLevelClient client;

    @Override
    public Boolean parseContent(String keywords) throws Exception {
        // 1、解析数据
        List<Product> products = new Jdutil().getBykeys(keywords);
        // 2、添加数据
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i = 0; i < products.size(); i++) {
            bulkRequest.add(new IndexRequest("jd")
                    .source(JSON.toJSONString(products.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    @Override
    public List<Map<String, Object>> searchPage(String keywords, Integer pageNum, Integer pageSize) throws IOException {
        if (pageNum < 1){
            pageNum = 1;
        }
        // 条件搜索
        SearchRequest searchRequest = new SearchRequest("jd");
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();

        // 分页
        searchBuilder.from(pageNum);
        searchBuilder.size(pageSize);

        // 精准匹配
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", keywords);
        searchBuilder.query(queryBuilder);
        searchBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchBuilder.highlighter(highlightBuilder);

        // 执行查询
        searchRequest.source(searchBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 解析结果
        ArrayList<Map<String, Object>> resList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()){
            // 解析高亮
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            // 替换
            if (name!=null){
                Text[] fragments = name.fragments();
                String newName = "";
                for (Text text : fragments){
                    newName += text;
                }
                sourceAsMap.put("name", newName);
            }
            resList.add(sourceAsMap);
        }
        return resList;
    }
}
