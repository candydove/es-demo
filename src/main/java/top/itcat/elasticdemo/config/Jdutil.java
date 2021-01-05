package top.itcat.elasticdemo.config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.itcat.elasticdemo.bean.Product;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yan.zhang
 * @date 2021/1/5
 */
public class Jdutil {
    public static void main(String[] args) throws Exception {
        new Jdutil().getBykeys("java").forEach(System.out::println);
    }
    public List<Product> getBykeys(String keys) throws Exception {
        String url = "https://search.jd.com/Search?keyword="+keys;
        // 1、解析网页（就是Document对象）
        Document document = Jsoup.parse(new URL(url), 30000);
        // 2、获取div
        Element element = document.getElementById("J_goodsList");
        System.out.println(element.html());
        // 3、获取li
        Elements elements = element.getElementsByTag("li");
        // 4、遍历
        ArrayList<Product> list = new ArrayList<>();
        for (Element el : elements){
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            Product product = new Product();
            product.setImg(img);
            product.setPrice(price);
            product.setName(title);
            list.add(product);
        }
        return list;
    }

}
