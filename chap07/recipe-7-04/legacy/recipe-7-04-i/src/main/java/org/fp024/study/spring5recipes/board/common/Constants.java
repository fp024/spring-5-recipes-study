package org.fp024.study.spring5recipes.board.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  /** 프로젝트의 전역 인코딩 */
  public static final Charset PROJECT_ENCODING = StandardCharsets.UTF_8;

  public static final String PROJECT_ENCODING_VALUE = PROJECT_ENCODING.name();
}
