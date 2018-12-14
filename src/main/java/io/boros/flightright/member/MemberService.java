package io.boros.flightright.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberService {

    Page<Member> getMembers(Pageable pageable);

    Optional<Member> getMember(String id);

    Member createMember(Member member);

    Optional<Member> updateMember(String id, Member member);

    void deleteMember(String id);

}
