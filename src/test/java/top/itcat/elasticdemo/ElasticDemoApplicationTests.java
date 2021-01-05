package top.itcat.elasticdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.itcat.elasticdemo.bean.User;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class ElasticDemoApplicationTests {

    @Resource
    RestHighLevelClient restHighLevelClient;

    // 1.1、创建索引
    @Test
    void creatIndex() throws IOException {
        // 1、创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("okcoin");
        // 2、客户端执行请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    // 1.2、索引是否存在
    @Test
    void existIndex() throws IOException {
        // 1、创建索引请求
        GetIndexRequest request = new GetIndexRequest("okcoin");
        // 2、客户端执行请求
        Boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 1.3、删除索引
    @Test
    void deleteIndex() throws IOException {
        // 1、创建索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("okcoin");
        // 2、客户端执行请求
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    // 2.1、创建文档
    @Test
    void createDoc() throws IOException {
        IndexRequest request = new IndexRequest("okcoin");
        request.id("1");
        User user = User.builder()
                .id(1)
                .age(18)
                .name("张三")
                .desc("张三喜欢篮球、乒乓球、羽毛球")
                .build();
        request.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
    }

    // 2.2、查询文档
    @Test
    void getDoc() throws IOException {
        GetRequest request = new GetRequest("okcoin", "1");
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(getResponse);
    }

    // 2.3、更新文档
    @Test
    void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest("okcoin", "1");
        User user = User.builder()
                .id(1)
                .age(20)
                .build();
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update);
    }
    // 2.4、删除文档
    @Test
    void deleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest(
                "okcoin",
                "1");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    // 2.5、批量（操作）文档
    @Test
    void createDocBatch() throws IOException {
        BulkRequest request = new BulkRequest();
        User user1 = User.builder()
                .id(1)
                .age(18)
                .name("张岩")
                .build();
        User user2 = User.builder()
                .id(1)
                .age(18)
                .name("李四")
                .build();
        request.add(new IndexRequest("okcoin").id("10")
                .source(JSON.toJSONString(user1), XContentType.JSON));
        request.add(new IndexRequest("okcoin").id("11")
                .source(JSON.toJSONString(user2), XContentType.JSON));
        BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk);
    }



}
