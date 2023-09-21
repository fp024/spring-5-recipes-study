package org.fp024.study.spring5recipes.court.service;

import java.util.Collection;
import org.fp024.study.spring5recipes.court.domain.Member;

public interface MemberService {

  Collection<Member> findAll();

  Member find(long id);
}
