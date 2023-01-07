package org.fp024.study.spring5recipes.court.service;

import java.util.List;
import org.fp024.study.spring5recipes.court.domain.Member;

public interface MemberService {
  void add(Member member);

  void remove(String memberName);

  List<Member> list();
}
