package com.wanted.market.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class MemberDTO {

  //notnull : null만 체크
  //notempty :  null과 빈 문자열 체크 (공백은 허용)
  //notblack : null, 빈 문자열, 공백 모두 체크


  @NotBlank(message = "이름은 필수 입력입니다.")
  // null체크, 문자열 길이가 0인 문자열, 빈 문자열
  private String name;

  @NotEmpty(message = "이메일은 필수입력입니다.")
  //null체크, 길이가 0인 문자열
  @Email(message = "이메일 형식으로 입력해주세요.")
  //이메일 형식인지 체크
  private String email;

  @NotEmpty(message = "비밀번호는 필수입력입니다.")
  @Length(min = 4, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
  private String password;

  @NotEmpty(message = "주소는 필수 입력입니다.")
  private String address;
}
