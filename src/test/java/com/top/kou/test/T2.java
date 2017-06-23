package com.top.kou.test;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by ZXL on 2017/5/25.
 */
public class T2 {
    public static void main(String[] args) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        resolver.getResources("classpath*:**/*.xml");
    }
}
