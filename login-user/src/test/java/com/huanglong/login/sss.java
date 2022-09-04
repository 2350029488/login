package com.huanglong.login;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.huanglong.login.entity.User;
import com.huanglong.login.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@SpringBootTest
public class sss {
    @Autowired
    private IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;

        public static void main(String[] args) {
            FastAutoGenerator.create("jdbc:mysql://localhost:3306/test_user?serverTimezone=GMT%2B8", "root", "root")
                    .globalConfig(builder -> {
                        builder.author("黄隆") // 设置作者
                                //.enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                                .outputDir("D:\\project\\LoginSystem\\login\\login-user\\src\\main\\java"); // 指定输出目录
                    })
                    .packageConfig(builder -> {
                        builder.parent("com.huanglong.login") // 设置父包名
                                .moduleName("") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\project\\LoginSystem\\login\\login-user\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.addInclude("user");// 设置需要生成的表名
//                                .addTablePrefix("t_"); // 设置过滤表前缀
                    })
                    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                    .execute();
        }

        @Test
    public void test(){
            List<User> list = userService.list();
            list.forEach(System.out::println);
        }

        @Test
    public void tests(){
            redisTemplate.opsForValue().set("k1","ssss");
            //从redis中获取值
            Object k1 = redisTemplate.opsForValue().get("k1");
            System.out.println(k1);
        }
        @Test
    public void tessst(){
            String encode = passwordEncoder.encode("123");
            System.out.println(encode);
        }
}
