package cn.haitaoss.provider;

import cn.haitaoss.MyAnnotation;
import org.springframework.boot.SpringApplication;

/**
 * @author haitao.chen
 * email haitaoss@aliyun.com
 * date 2023-03-06 10:55
 *
 */
@MyAnnotation
public class Provider2 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "provider2");
        SpringApplication.run(Provider2.class, args);
    }
}
