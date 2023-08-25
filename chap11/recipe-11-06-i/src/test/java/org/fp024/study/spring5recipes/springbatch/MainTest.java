package org.fp024.study.spring5recipes.springbatch;

import org.junit.jupiter.api.Test;

class MainTest {
  // ItemProcessor에서 csv 파일의 내용을 검증하므로, csv 파일을 미리 만들어 두고 그것을 읽도록하자!
  @Test
  void testMain() throws Exception {
    Main.main(null);
  }
}
