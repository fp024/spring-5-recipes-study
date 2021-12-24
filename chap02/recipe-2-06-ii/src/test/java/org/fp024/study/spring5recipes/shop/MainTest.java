package org.fp024.study.spring5recipes.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {
  @Test
  @DisplayName("어노테이션 설정 방식")
  void testMain() throws Exception {
    Main.main(new String[] {Main.ContextType.ANNOTATION.name()});
  }

  @Test
  @DisplayName("XML 설정 방식")
  void testMainWithXML() throws Exception {
    Main.main(new String[] {Main.ContextType.XML.name()});
  }
}
