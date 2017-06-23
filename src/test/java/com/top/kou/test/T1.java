package com.top.kou.test;

import junit.framework.TestCase;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import top.kou.will.news.NewsListener;

/**
 * Created by ZXL on 2017/5/22.
 */
public class T1 extends TestCase {

    public void testBeanDefinition() {
        BeanDefinitionRegistry registry = new DefaultListableBeanFactory();

        Resource resource = new ClassPathResource("news/beans.xml");
        new XmlBeanDefinitionReader(registry).loadBeanDefinitions(resource);

        Object object = ((BeanFactory) registry).getBean("newsListener");
        NewsListener listener = ((BeanFactory) registry).getBean("newsListener", NewsListener.class);
        Assert.isTrue(object.equals(listener));
    }
}
