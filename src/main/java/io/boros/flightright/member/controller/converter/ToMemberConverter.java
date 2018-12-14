package io.boros.flightright.member.controller.converter;

import io.boros.flightright.member.model.Member;
import io.boros.flightright.member.controller.MemberDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ToMemberConverter implements Converter<MemberDTO, Member> {

    @Override
    public Member convert(MemberDTO source) {
        return new Member(
                source.getFirstName(),
                source.getLastName(),
                source.getDateOfBirth(),
                source.getZipCode()
        );
    }
}
