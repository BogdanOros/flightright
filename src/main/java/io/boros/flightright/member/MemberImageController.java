package io.boros.flightright.member;

import io.boros.flightright.image.FileUploader;
import io.boros.flightright.member.converter.ToMemberDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static io.boros.flightright.member.MemberController.MEMBER_API;

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
                        .flatMap(imageURI -> memberService.updateImage(memberId, imageURI)))
                .map(toDTOConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
