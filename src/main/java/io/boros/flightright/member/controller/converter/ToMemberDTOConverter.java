package io.boros.flightright.member.controller.converter;

import io.boros.flightright.image.ImageLocationProvider;
import io.boros.flightright.member.controller.MemberDTO;
import io.boros.flightright.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ToMemberDTOConverter implements Converter<Member, MemberDTO> {

    private final ImageLocationProvider locationProvider;

    @Override
    public MemberDTO convert(Member source) {

        URL image = Optional.ofNullable(source.getImage())
                .flatMap(locationProvider::getURL)
                .orElse(null);

        return new MemberDTO(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getZipCode(),
                source.getDateOfBirth(),
                image
        );
    }
}
