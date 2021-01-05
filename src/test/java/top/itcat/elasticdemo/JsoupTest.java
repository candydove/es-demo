package top.itcat.elasticdemo;

import org.junit.jupiter.api.Test;
import top.itcat.elasticdemo.bean.User;

import java.util.ArrayList;

/**
 * @author yan.zhang
 * @date 2021/1/4
 */
public class JsoupTest {
    @Test
    void sort(){
        User user1 = new User(1,"zs1", 18,"aa");
        User user2 = new User(2,"zs2", 19, "aa");
        User user3 = new User(3,"zs3", 10, "aa");
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.sort((r1, r2) -> (r2.getAge()).compareTo(r1.getAge()));
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).toString());
        }
    }
}
