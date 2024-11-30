package com.wanted.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Cart extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long id;

  // @OneToOne : 회원 테이블과 1:1 매핑
  // @JoninColumn : 외래키 지정, name : 외래키의 이름
  // @OneToOne(fetch=FetchType.EAGER): 패치 전략은 EAGER와 LAZY가 있음.
  // EAGER : 즉시로딩(@OneToOne, @ManyToOne 얘네는 이게 디폴트임)
  // LAZY :  지연로딩(@OnetToMany, @ManyToMany)
  // 뒤가 one이면 디폴트가 즉시로딩 many면 지연로딩 fetch를 지정해주지 않으면 디폴트
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;


  public static Cart createCart(Member member){
    Cart cart = new Cart();
    cart.setMember(member);
    return cart;
  }

}
