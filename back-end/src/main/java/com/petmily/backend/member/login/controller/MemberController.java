package com.petmily.backend.member.login.controller;

import com.petmily.backend.member.login.dto.MemberRoleUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.petmily.backend.member.login.domain.Member;
import com.petmily.backend.member.login.dto.MemberDto;
import com.petmily.backend.member.login.dto.MemberRegister;
import com.petmily.backend.member.login.dto.MemberUpdateRequest;
import com.petmily.backend.member.login.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
    private final MemberService memberService;
    private final HttpSession httpSession;

    public MemberController(MemberService memberService, HttpSession httpSession){
        this.memberService = memberService;
        this.httpSession = httpSession;
    }

    @PostMapping("/login")
    public long login(@RequestBody MemberDto dto){
        long loginResult = memberService.login(dto);
        if(loginResult == 1) { //로그인 성공시
            httpSession.setAttribute("id", dto.getMemberId()); //httpSession에 session 추가 (서버측 관리)
            log.info("User {} 로그인 성공 ", dto.getMemberId());
            log.info("세션ID값 : {}" , (String) httpSession.getAttribute("id"));
        }else{
            log.warn("User {} 로그인 실패",dto.getMemberId());
        }
        return loginResult;
    }

    @GetMapping("/logout")
    public String logout() {
        httpSession.invalidate(); //로그아웃시 httpSession값 초기화
        return "로그아웃 성공";
    }

    @GetMapping("/check-login") //로그인 되어있는지 검사 되어있을때 true 아닐때 false 반환
    public ResponseEntity<Boolean> checkLoginStatus(){
        String loggedInUser = (String) httpSession.getAttribute("id");

        if (loggedInUser != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false,HttpStatus.OK);
        }

    }

    @GetMapping("/get-usernum") //member 식별자 가져옴 (작성자 확인하는 경우 비교를 위함)
    public ResponseEntity<Long> getLoggedInUserNum(){
        String loggedInUserId = (String) httpSession.getAttribute("id");

        if(loggedInUserId != null){
            Member member = memberService.getMember(loggedInUserId); // 세션에서 가져온 userId를 getMember 메소드에 전달하여, member 객체값을 받는다.
            Long memberNum = member.getMemberNum(); // 가져온 객체에서 해당 Id의 memberNum의 값을 가져온다.
            return new ResponseEntity<>(memberNum, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
    @GetMapping("/get-userroll/{memberNum}") // memberRoll 값 가져옴
    public String getUserRoll(@PathVariable Long memberNum) {
    	
    	return memberService.getMemberRoll(memberNum);
    }

    @GetMapping("/members")
    public Page<Member> getMembers(Pageable pageable){
        return memberService.getMembers(pageable);
    }

    @GetMapping("/members/count")
    public ResponseEntity<Long> getTotalMembers(){
        return ResponseEntity.ok(memberService.getTotalMembers());
    }

    @DeleteMapping("/members/{memberNum}")
    public  ResponseEntity<String> deleteMember(@PathVariable Long memberNum){
        try {
            memberService.deleteMember(memberNum);
            return new ResponseEntity<>("멤버 삭제 성공",HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("멤버 삭제 실패",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateRole/{memberNum}")
    private ResponseEntity<String> updateMemberRole(@PathVariable Long memberNum, @RequestBody MemberRoleUpdateDto dto){
        try{
            dto.setMemberNum(memberNum);
            memberService.updateMemberRole(dto);
            return new ResponseEntity<>("멤버 Role 수정 성공", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("멤버 Role 수정 실패",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/selectMember")
    public Member selectMember(@RequestBody MemberDto dto){
        return memberService.selectMember(dto);
    }

    @PostMapping("/findpw")
    public long findmember(@RequestBody MemberDto dto){
        return memberService.findMember(dto);
    }

    @PutMapping("/changepw")
    public int pwChange(@RequestBody MemberDto dto){
        return memberService.pwChange(dto);
    }
    
    @PutMapping("/update/{memberNum}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberNum, @RequestBody MemberUpdateRequest request) {
        boolean updated = memberService.updateMember(memberNum, request);
        if (updated) {
            return ResponseEntity.ok("Member updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public long register(@Validated @RequestBody MemberRegister register) {
    	return memberService.register(register);
    }
    
    @PostMapping("/updateValid")
    public long updateValid(@Validated @RequestBody MemberUpdateRequest request) {
    	return memberService.updateValid(request);
    }
    
    @GetMapping("/memberInfo/{memberNum}")
    public Member getMemberInfo(@PathVariable Long memberNum){
    	System.out.println("hello");
        return memberService.getMemberInfo(memberNum);
    }

    @GetMapping("/get-userinfo")
    public ResponseEntity<Member> getLoggedInUserInfo() {
        String loggedInUserId = (String) httpSession.getAttribute("id");

        if (loggedInUserId != null) {
            Member member = memberService.getMember(loggedInUserId);
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getMemberDetail/{memberNum}")
    public Member getMemberDetail(@PathVariable Long memberNum){
    	System.out.println("hello");
        return memberService.getMemberDetail(memberNum);
    }
}
