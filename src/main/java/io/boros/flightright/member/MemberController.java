package io.boros.flightright.member;

import io.boros.flightright.image.FileUploader;
import io.boros.flightright.member.converter.ToMemberConverter;
import io.boros.flightright.member.converter.ToMemberDTOConverter;
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

    static final String MEMBER_API = "/members";

    private final MemberService memberService;
    private final MemberLookupService lookupService;
    private final FileUploader fileUploader;

    private final ToMemberDTOConverter toDTOConverter;
    private final ToMemberConverter toMemberConverter;

    @GetMapping
    public Page<MemberDTO> getMembers(Pageable pageable) {
        return lookupService.getMembers(pageable)
                .map(toDTOConverter::convert);
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("id") String id) {
        return lookupService.getMember(id)
                .map(toDTOConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Validated(Create.class) @RequestBody MemberDTO member) {
        Member createdMember = memberService.createMember(toMemberConverter.convert(member));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTOConverter.convert(createdMember));
    }

    @PutMapping("{id}")
    public MemberDTO updateMember(@PathVariable("id") String id,
                                  @Validated(Update.class) @RequestBody MemberDTO member) {
        return memberService.updateMember(id, toMemberConverter.convert(member))
                .map(toDTOConverter::convert)
                .orElse(null);
    }

    @DeleteMapping("{id}")
    public void deleteMember(@PathVariable("id") String id) {
        memberService.deleteMember(id);
    }

    @PostMapping(path = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberDTO> uploadImage(@PathVariable("id") String memberId,
                                                 @RequestBody MultipartFile image) {
        return lookupService.getMember(memberId)
                .flatMap(member -> fileUploader.uploadFile(image)
                        .flatMap(imageURI -> memberService.updateImage(memberId, imageURI)))
                .map(toDTOConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
