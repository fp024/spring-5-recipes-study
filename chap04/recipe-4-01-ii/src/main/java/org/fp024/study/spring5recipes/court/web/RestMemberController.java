package org.fp024.study.spring5recipes.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Member;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestMemberController {

  private final MemberService memberService;

  @RequestMapping(value = "/members")
  public Members getRestMembers() {
    Members members = new Members();
    members.addMembers(memberService.findAll());
    return members;
  }

  @RequestMapping("/member/{memberId}")
  public ResponseEntity<Member> getMember(@PathVariable("memberId") long memberId) {
    Member member = memberService.find(memberId);
    if (member != null) {
      return ResponseEntity.ok(member);
    }
    return ResponseEntity.notFound().build();
  }
}
