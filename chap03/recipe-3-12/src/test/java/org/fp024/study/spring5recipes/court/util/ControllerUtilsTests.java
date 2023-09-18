package org.fp024.study.spring5recipes.court.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fp024.study.spring5recipes.court.util.ControllerUtils.getTargetPage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class ControllerUtilsTests {
  private MockHttpServletRequest request;

  private final String PARAM_PREFIX = "_target";

  @BeforeEach
  void setUp() {
    request = new MockHttpServletRequest();
  }

  @Test
  void testGetTargetPage() {
    request.setParameter("_target0", "Previous");
    int pageIndex = getTargetPage(request, PARAM_PREFIX, 1);
    assertThat(pageIndex).isEqualTo(0);

    // MockHttpServletRequest을 초기화하지 않고 한 메서드에서 계속 파라미터를 설정하면 누적되기 때문에 초기화해줘야한다.
    request.removeAllParameters();

    request.setParameter("_target1", "Next");
    pageIndex = getTargetPage(request, PARAM_PREFIX, 2);
    assertThat(pageIndex).isEqualTo(1);

    request.removeAllParameters();

    request.setParameter("_target2", "Previous");
    pageIndex = getTargetPage(request, PARAM_PREFIX, 1);
    assertThat(pageIndex).isEqualTo(2);
  }

  /*
   저자님이 이미지 버튼일 경우에 대한 예외처리를 해두셔서 테스트 해봤다.
  */
  @Test
  void testGetTargetPage_imageButton() {
    request.setParameter("target0.x", "Previous");
    int pageIndex = getTargetPage(request, PARAM_PREFIX, 0);
    assertThat(pageIndex).isEqualTo(0);

    request.removeAllParameters();

    request.setParameter("target1.y", "Previous");
    pageIndex = getTargetPage(request, PARAM_PREFIX, 1);
    assertThat(pageIndex).isEqualTo(1);
  }
}
