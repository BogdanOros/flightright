package io.boros.flightright.member.converter;

import io.boros.flightright.member.Member;
import io.boros.flightright.member.MemberDTO;
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
