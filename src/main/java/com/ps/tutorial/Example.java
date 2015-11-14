package com.ps.tutorial;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Example {

    private static Logger log = Logger.getLogger(Example.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        @SuppressWarnings("unchecked")
        RedisTemplate<String, String> stringTemplate = (RedisTemplate<String, String>) ctx.getBean("stringRedisTemplate");
        // Plain data
        log.debug("Setting: 'hello'...");
        stringTemplate.opsForValue().set("test:field", "hello");
        String value = stringTemplate.opsForValue().get("test:field");
        log.debug("Read: '" + value + "'");
        assert value.equals("hello");
        // Complex data
        @SuppressWarnings("unchecked")
        RedisTemplate<String, User> userTemplate = (RedisTemplate<String, User>) ctx.getBean("userRedisTemplate");
        User user = new User("Pavlo", "Pohrebnyi");
        log.debug("Setting: '" + user + "'");
        userTemplate.opsForValue().set("test:user", user);
        User readUser = userTemplate.opsForValue().get("test:user");
        log.debug("Read: '" + user + "'");
        assert user.equals(readUser);
        ctx.close();
    }
}