package io.boros.flightright.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

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
                .map(existing -> merge(member, existing));
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }

    private Member merge(Member from, Member to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setDateOfBirth(from.getDateOfBirth());
        to.setZipCode(from.getZipCode());
        return to;
    }

}
