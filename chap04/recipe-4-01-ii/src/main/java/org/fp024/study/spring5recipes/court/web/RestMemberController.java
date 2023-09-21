package org.fp024.study.spring5recipes.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestMemberController {

  private final MemberService memberService;

  @RequestMapping(value = "/members.xml")
  public Members getRestMembersXML() {
    Members members = new Members();
    members.addMembers(memberService.findAll());
    return members;
  }
}
