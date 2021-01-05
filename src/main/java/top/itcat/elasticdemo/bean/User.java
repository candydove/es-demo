package top.itcat.elasticdemo.bean;

import lombok.*;

/**
 * @author yan.zhang
 * @date 2021/1/4
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String desc;
}
