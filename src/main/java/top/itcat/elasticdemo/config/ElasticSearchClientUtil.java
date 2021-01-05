package top.itcat.elasticdemo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yan.zhang
 * @date 2021/1/4
 */
@Configuration
public class ElasticSearchClientUtil {
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("www.itcat.top", 9200, "http")));
        return client;
    }
}
