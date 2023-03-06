package cn.haitaoss.consumer;

import cn.haitaoss.MyAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author haitao.chen
 * email haitaoss@aliyun.com
 * date 2023-03-06 10:55
 *
 */
@MyAnnotation
@RefreshScope
public class Consumer2 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "consumer2");
        SpringApplication.run(Consumer2.class, args);
    }
}
