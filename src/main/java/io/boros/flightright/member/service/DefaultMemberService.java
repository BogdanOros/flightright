package io.boros.flightright.member.service;

import io.boros.flightright.member.model.Member;
import io.boros.flightright.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService, MemberLookupService {

    private final MemberRepository memberRepository;

    public Page<Member> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Optional<Member> getMember(String id) {
        return memberRepository.findById(id);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> updateMember(String id, Member member) {
        return memberRepository.findById(id)
                .map(existing -> merge(member, existing))
                .map(memberRepository::save);
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }

    public Optional<Member> updateImage(String id, String imageName) {
        return memberRepository.findById(id)
                .map(member -> member.setImage(imageName))
                .map(memberRepository::save);
    }

    private Member merge(Member from, Member to) {
        return to.setFirstName(from.getFirstName())
                .setLastName(from.getLastName())
                .setDateOfBirth(from.getDateOfBirth())
                .setZipCode(from.getZipCode());
    }

}
