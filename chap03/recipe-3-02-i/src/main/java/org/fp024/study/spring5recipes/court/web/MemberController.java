package org.fp024.study.spring5recipes.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Member;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/member/list")
  public String list(Model model) {
    model.addAttribute("memberList", memberService.list());
    return "memberList";
  }

  @PostMapping("/member/add")
  public String addMember(Member member, Model model) {
    memberService.add(member);
    return "redirect:/member/list";
  }

  @PostMapping({"/member/remove", "/member/delete"})
  public String removeMember(@RequestParam("memberName") String memberName) {
    memberService.remove(memberName);
    return "redirect:/member/list";
  }
}
