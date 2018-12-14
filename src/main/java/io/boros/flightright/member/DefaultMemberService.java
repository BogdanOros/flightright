package io.boros.flightright.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
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

    @Transactional
    public Optional<Member> updateMember(String id, Member member) {
        return memberRepository.findById(id)
                .map(existing -> merge(member, existing))
                .map(memberRepository::save);
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public Optional<Member> updateImage(String id, URL imageURL) {
        return memberRepository.findById(id)
                .map(member -> member.setImage(imageURL))
                .map(memberRepository::save);
    }

    private Member merge(Member from, Member to) {
        return to.setFirstName(from.getFirstName())
                .setLastName(from.getLastName())
                .setDateOfBirth(from.getDateOfBirth())
                .setZipCode(from.getZipCode());
    }

}
