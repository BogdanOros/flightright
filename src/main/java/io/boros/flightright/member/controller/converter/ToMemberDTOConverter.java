package io.boros.flightright.member.controller.converter;

import io.boros.flightright.image.ImageLocationProvider;
import io.boros.flightright.member.model.Member;
import io.boros.flightright.member.controller.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToMemberDTOConverter implements Converter<Member, MemberDTO> {

    private final ImageLocationProvider locationProvider;

    @Override
    public MemberDTO convert(Member source) {
        return new MemberDTO(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getZipCode(),
                source.getDateOfBirth(),
                locationProvider.getURL(source.getImage())
        );
    }
}
