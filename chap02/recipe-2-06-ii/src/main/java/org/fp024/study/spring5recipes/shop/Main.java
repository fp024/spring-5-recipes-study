package org.fp024.study.spring5recipes.shop;

import java.util.Arrays;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.shop.config.ShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    ContextType contextType;
    if (args.length == 0) {
      contextType = ContextType.ANNOTATION;
    } else {
      contextType = ContextType.valueOf(args[0]);
    }

    try (GenericApplicationContext context = createContext(contextType)) {

      Arrays.stream(context.getBeanDefinitionNames()).forEach(s -> LOGGER.info("beanName: {}", s));

      Product aaa = context.getBean("aaa", Product.class);
      Product cdrw = context.getBean("cdrw", Product.class);
      Product dvdrw = context.getBean("dvdrw", Product.class);

      ShoppingCart cart1 = context.getBean("shoppingCart", ShoppingCart.class);
      cart1.addItem(aaa);
      cart1.addItem(cdrw);
      LOGGER.info("Shopping cart 1 contains {}", cart1.getItems());

      ShoppingCart cart2 = context.getBean("shoppingCart", ShoppingCart.class);
      cart2.addItem(dvdrw);
      LOGGER.info("Shopping cart 2 contains {}", cart2.getItems());

      Resource resource = new ClassPathResource("discounts.properties");
      Properties props = PropertiesLoaderUtils.loadProperties(resource);
      LOGGER.info("And don't forget our discounts!");
      LOGGER.info("props: {}", props);
    }
  }

  enum ContextType {
    ANNOTATION,
    XML
  }

  private static GenericApplicationContext createContext(ContextType contextType) {
    switch (contextType) {
      case ANNOTATION:
        LOGGER.info("어노테이션 방식 Context 생성");
        return new AnnotationConfigApplicationContext(ShopConfiguration.class);
      case XML:
        LOGGER.info("XML 방식 Context 생성");
        return new GenericXmlApplicationContext("beans.xml");
      default:
        throw new IllegalArgumentException();
    }
  }
}
