package io.boros.flightright.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberLookupService {

    Page<Member> getMembers(Pageable pageable);

    Optional<Member> getMember(String id);

}
