package sprint.sprint1_3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
//    MemberUpdateForm toUpdateForm(Member member);
    //Member toMember(MemberDto memberDto);
    //Member updateToMember(MemberUpdateForm memberUpdateForm);
    //Member joinToMember(MemberJoinForm memberJoinForm);
    //MemberListForm toListForm(Member member);

 //   MemberDto memberToDto(Member member);

  //  MemberLoginDto memberToLoginDto(Member member);


}
