package com.wanted.market.service;

import com.keduit.shop.entity.Member;
import com.keduit.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;

  public Member saveMember(Member member){
    validateMember(member);
    return memberRepository.save(member);
  }

  private void validateMember(Member member){
    Member findMember = memberRepository.findByEmail(member.getEmail());
    if(findMember != null){
      throw new IllegalStateException("이미 가입된 회원입니다.");
    }
  }


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email);

    if(member == null){
      throw new UsernameNotFoundException(email);
    }

    return User.builder()   //userdetails 타입을 리턴해야하는데 얜 user임 즉 user가 userdetails의 후손이라는 뜻
        .username(member.getEmail())
        .password(member.getPassword())
        .roles(member.getRole().toString())
        .build();
  }
}
