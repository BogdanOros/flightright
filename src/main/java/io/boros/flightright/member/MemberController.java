package io.boros.flightright.member;

import io.boros.flightright.image.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static io.boros.flightright.member.MemberController.MEMBER_API;
import static io.boros.flightright.utils.ValidationGroup.Create;
import static io.boros.flightright.utils.ValidationGroup.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = MEMBER_API, produces = {
        MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE
})
public class MemberController {

    public static final String MEMBER_API = "/members";

    private final MemberService memberService;
    private final MemberLookupService lookupService;
    private final FileUploader fileUploader;

    @GetMapping
    public Page<Member> getMembers(Pageable pageable) {
        return lookupService.getMembers(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") String id) {
        return lookupService.getMember(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Validated(Create.class) @RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.createMember(member));
    }

    @PutMapping("{id}")
    public Member updateMember(@PathVariable("id") String id,
                               @Validated(Update.class) @RequestBody Member member) {
        return memberService.updateMember(id, member).orElse(null);
    }

    @DeleteMapping("{id}")
    public void deleteMember(@PathVariable("id") String id) {
        memberService.deleteMember(id);
    }

    @PostMapping(path = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Member uploadImage(
            @PathVariable("id") String memberId,
            @RequestBody MultipartFile image) {
        return lookupService.getMember(memberId)
                .flatMap(member -> fileUploader.uploadFile(image)
                        .flatMap(imageURI -> memberService.updateImage(memberId, imageURI)))
                .orElse(null);
    }
}
