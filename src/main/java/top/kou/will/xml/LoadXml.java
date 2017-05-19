package top.kou.will.xml;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.util.ResourceUtils;
import top.kou.will.news.NewsProvider;


/**
 * Created by ZXL on 2017/5/19.
 */
public class LoadXml {


    public static void main(String[] args) throws Exception {
        BeanDefinitionRegistry registry = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions(new org.springframework.core.io.ClassPathResource("/news/beans.xml"));

        BeanFactory factory = (BeanFactory) registry;

        NewsProvider newsProvider = (NewsProvider) factory.getBean("newsProvider");
        newsProvider.getAndPersistent();
    }
}
