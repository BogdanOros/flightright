package io.boros.flightright.member;

import java.net.URL;
import java.util.Optional;

public interface MemberService {

    Member createMember(Member member);

    Optional<Member> updateMember(String id, Member member);

    void deleteMember(String id);

    Optional<Member> updateImage(String id, URL imageURL);

}
