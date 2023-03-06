package cn.haitaoss.consumer;

import cn.haitaoss.MyAnnotation;
import org.springframework.boot.SpringApplication;

/**
 * @author haitao.chen
 * email haitaoss@aliyun.com
 * date 2023-03-06 10:55
 *
 */
@MyAnnotation
public class Consumer1 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "consumer1");
        SpringApplication.run(Consumer1.class, args);
    }
}
