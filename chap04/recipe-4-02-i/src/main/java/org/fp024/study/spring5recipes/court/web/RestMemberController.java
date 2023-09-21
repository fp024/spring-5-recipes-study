package org.fp024.study.spring5recipes.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class RestMemberController {

  private final MemberService memberService;

  @RequestMapping(
      value = {"/members.xml"},
      produces = {MediaType.APPLICATION_XML_VALUE})
  public String getRestMembersXml(Model model) {
    processMemberModel(model);
    return "xmlMemberTemplate";
  }

  @RequestMapping(
      value = {"/members", "/members.json"},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public String getRestMembersJson(Model model) {
    processMemberModel(model);
    return "jsonMemberTemplate";
  }

  private void processMemberModel(Model model) {
    Members members = new Members();
    members.addMembers(memberService.findAll());
    model.addAttribute("members", members);
  }
}
