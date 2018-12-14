package io.boros.flightright.member.service;

import io.boros.flightright.member.model.Member;

import java.util.Optional;

public interface MemberService {

    Member createMember(Member member);

    Optional<Member> updateMember(String id, Member member);

    void deleteMember(String id);

    Optional<Member> updateImage(String id, String imageName);

}
