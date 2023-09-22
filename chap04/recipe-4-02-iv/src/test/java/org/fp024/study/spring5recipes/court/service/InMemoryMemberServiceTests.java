package org.fp024.study.spring5recipes.court.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import org.fp024.study.spring5recipes.court.config.RootConfiguration;
import org.fp024.study.spring5recipes.court.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(RootConfiguration.class)
class InMemoryMemberServiceTests {

  @Autowired private MemberService memberService;

  @Test
  void testFindAll() {
    Collection<Member> list = memberService.findAll();
    assertThat(list).isNotEmpty();
  }

  @Test
  void testFind() {
    Member member = memberService.find(1L);
    assertThat(member).hasFieldOrPropertyWithValue("name", "Marten Deinum");
  }
}
