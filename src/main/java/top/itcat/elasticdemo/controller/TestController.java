package top.itcat.elasticdemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.index.IndexReader;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.elasticdemo.service.ITestService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yan.zhang
 * @date 2021/1/4
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    /**
     * 1、爬取数据
     * @param keywords
     * @return
     * @throws Exception
     */
    @RequestMapping("/save/{keywords}")
    public Boolean save(@PathVariable("keywords") String keywords) throws Exception {
        Boolean aBoolean = testService.parseContent(keywords);
        return aBoolean;
    }

    /**
     * 2、搜索数据
     * @param keywords
     * @param pageSize
     * @param pageNum
     * @return
     * @throws Exception
     */
    @RequestMapping("/search/{keywords}/{pageSize}/{pageNum}")
    public List<Map<String, Object>> test(@PathVariable("keywords") String keywords,
                          @PathVariable("pageSize") Integer pageSize,
                          @PathVariable("pageNum") Integer pageNum) throws Exception {
        return testService.searchPage(keywords, pageNum, pageSize);
    }

}
