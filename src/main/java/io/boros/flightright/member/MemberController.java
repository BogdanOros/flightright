package io.boros.flightright.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.boros.flightright.member.MemberController.MEMBER_API;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = MEMBER_API, produces = {
        MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE
})
public class MemberController {

    public static final String MEMBER_API = "/members";

    private final MemberService memberService;

    @GetMapping
    public Page<Member> getMembers(Pageable pageable) {
        return memberService.getMembers(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") String id) {
        return memberService.getMember(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO: add validation
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.createMember(member));
    }

    // TODO: add validation
    @PutMapping("{id}")
    public Member updateMember(@PathVariable("id") String id,
                               @RequestBody Member member) {
        return memberService.updateMember(id, member).orElse(null);
    }
}
