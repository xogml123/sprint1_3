package sprint.sprint1_3.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import sprint.sprint1_3.dto.member.MemberDto;
import sprint.sprint1_3.dto.member.MemberMatchForm;
import sprint.sprint1_3.exception.member.PayMoneyOverflow;
import sprint.sprint1_3.exception.member.PayMoneyUnderflow;

@Entity
@Getter
@ToString
@Slf4j
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    private String loginId;
    private String loginPassword;

    private Integer age;
    private Long payMoney;
    private Gender gender;
    private MemberShip memberShip;


    private static Long payMoneyMax = 1000000000L;

    public Member() {
    }



    public Member(String name, Integer age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Member(Long id, String name, Integer age,
        Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Member(String name, String loginId, String loginPassword, Integer age,
        Gender gender) {
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.age = age;
        this.gender = gender;
    }

    public static MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getAge(), member.getPayMoney(), member.getGender(), member.getMemberShip());
    }

    public static MemberMatchForm toMatchForm(Member member) {
        return new MemberMatchForm(member.getId(), member.getName(), member.getAge(), member.getGender());
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long updateInfo(Long id, String name, Integer age,
        Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;

        return this.id;
    }


    public void defaultMemberShip(MemberShip bronze) {
        this.memberShip = bronze;
    }

    public void defaultPayMoney(Long i) {
        this.payMoney = Long.valueOf(0);
    }

    public Long addPayment(Long payment) {
        Long payMoneyTemp = this.payMoney + payment;
        if (payMoneyTemp >= payMoneyMax) {
            throw new PayMoneyOverflow("개인 결제 한계 금액을 초과 하였습니다.");
        }
        this.payMoney = payMoneyTemp;
        updateMemberShip();
        return payMoney;
    }

    public Long subtractPayment(Long payment) {
        Long payMoneyTemp = this.payMoney - payment;
        if (payMoneyTemp < MemberShip.BRONZE.getMinimum()) {
            throw new PayMoneyUnderflow("총 결제 금액이 BRONZE 최소 보다 낮습니다.");
        }
        this.payMoney = payMoneyTemp;
        updateMemberShip();
        return payMoney;
    }

    public String getSecretName() {
        return name.substring(0, 1) + "*".repeat(name.substring(1).length());
    }


    private void updateMemberShip() {
        if (memberShip == MemberShip.GOLD) {
            return ;
        }
        if (payMoney >= MemberShip.GOLD.getMinimum()){
            memberShip = MemberShip.GOLD;
        } else if (payMoney >= MemberShip.SILVER.getMinimum()) {
            memberShip = MemberShip.SILVER;
        }
        else{
            memberShip = MemberShip.BRONZE;
        }
    }
}
