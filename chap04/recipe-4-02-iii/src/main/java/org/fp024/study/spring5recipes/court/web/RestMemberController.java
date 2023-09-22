package org.fp024.study.spring5recipes.court.web;

import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.court.domain.Members;
import org.fp024.study.spring5recipes.court.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class RestMemberController {

  private final MemberService memberService;

  @RequestMapping(
      value = {"/members.xml"},
      produces = {MediaType.APPLICATION_XML_VALUE})
  @ResponseBody
  public Members getRestMembersXml() {
    return members();
  }

  @RequestMapping(
      value = {"/members", "/members.json"},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public Members getRestMembersJson() {
    return members();
  }

  private Members members() {
    Members members = new Members();
    members.addMembers(memberService.findAll());
    return members;
  }
}
