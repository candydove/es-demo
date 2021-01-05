package top.itcat.elasticdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yan.zhang
 * @date 2021/1/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String img;
    private String price;
    private String name;

}
