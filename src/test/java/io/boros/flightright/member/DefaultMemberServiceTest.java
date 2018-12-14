package io.boros.flightright.member;

import io.boros.flightright.member.model.Member;
import io.boros.flightright.member.repository.MemberRepository;
import io.boros.flightright.member.service.DefaultMemberService;
import io.boros.flightright.member.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class DefaultMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    private final String userId = "1";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Member old = new Member(userId, "test", "test", Instant.MIN, "123", null);
        Mockito.when(memberRepository.findById(userId)).thenReturn(Optional.of(old));

        memberService = new DefaultMemberService(memberRepository);
    }

    @Test
    public void shouldUpdateMembersField() {
        Member source = new Member();
        source.setFirstName("firstName");

        memberService.updateMember(userId, source);

        Mockito.verify(memberRepository).save(
                Mockito.argThat(argument -> source.getFirstName().equals(argument.getFirstName()))
        );
    }

    @Test
    public void shouldChangeMembersImageURL() throws Exception {
        String image = UUID.randomUUID().toString();
        memberService.updateImage(userId, image);

        Mockito.verify(memberRepository).save(
                Mockito.argThat(argument -> image.equals(argument.getImage()))
        );
    }
}