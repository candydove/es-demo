package top.itcat.elasticdemo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yan.zhang
 * @date 2021/1/5
 */
public interface ITestService {
    Boolean parseContent(String keywords) throws Exception;

    List<Map<String, Object>> searchPage(String keywords, Integer pageNum, Integer pageSize) throws IOException;
}
