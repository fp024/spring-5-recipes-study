package org.fp024.study.spring5recipes.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class RestMemberController {

  private final MemberService memberService;

  @RequestMapping("/members")
  public String getRestMembers(Model model) {
    Members members = new Members();
    members.addMembers(memberService.findAll());
    model.addAttribute("members", members);
    return "memberTemplate";
  }
}
