package io.boros.flightright.member.controller.converter;

import io.boros.flightright.member.model.Member;
import io.boros.flightright.member.controller.MemberDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ToMemberDTOConverter implements Converter<Member, MemberDTO> {

    @Override
    public MemberDTO convert(Member source) {
        return new MemberDTO(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getZipCode(),
                source.getDateOfBirth(),
                source.getImage()
        );
    }
}
