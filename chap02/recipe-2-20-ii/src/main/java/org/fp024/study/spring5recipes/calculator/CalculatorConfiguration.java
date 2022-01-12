package org.fp024.study.spring5recipes.calculator;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// @EnableAspectJAutoProxy  // AspectJ 프레임워크를 직접 사용하기 때문에 활성화하지 않는다.  META-INF에 aop.xml이 정의됨
@ComponentScan
public class CalculatorConfiguration {}
