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
public class Provider1 {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "provider1");
        SpringApplication.run(Provider1.class, args);
    }
}
