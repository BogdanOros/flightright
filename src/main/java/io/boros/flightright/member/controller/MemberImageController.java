package io.boros.flightright.member.controller;

import io.boros.flightright.image.FileUploader;
import io.boros.flightright.member.service.MemberLookupService;
import io.boros.flightright.member.service.MemberService;
import io.boros.flightright.member.controller.converter.ToMemberDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static io.boros.flightright.member.controller.MemberController.MEMBER_API;

@ConditionalOnBean(FileUploader.class)
@RestController
@RequiredArgsConstructor
@RequestMapping(MEMBER_API)
public class MemberImageController {

    private final FileUploader fileUploader;
    private final MemberLookupService lookupService;
    private final MemberService memberService;

    private final ToMemberDTOConverter toDTOConverter;

    @PostMapping(path = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberDTO> uploadImage(@PathVariable("id") String memberId,
                                                 @RequestBody MultipartFile image) {
        return lookupService.getMember(memberId)
                .flatMap(member -> fileUploader.uploadFile(image)
                        .flatMap(imageName -> memberService.updateImage(memberId, imageName)))
                .map(toDTOConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
