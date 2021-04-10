package top.itcat.elasticdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.itcat.elasticdemo.service.ITestService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * es
 * @author yan.zhang
 * @date 2021/1/4
 */
@Controller
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

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("hello", "你好妞妞！");
        model.addAttribute("users", Arrays.asList("张三","李四","王五"));
        return "success";
    }
}
