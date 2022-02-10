package SG.MoaMoa.domain;

import SG.MoaMoa.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String loginId;
    private String password;
    private String email;
    private String authenticationKey;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "user" , cascade =  CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade =  CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private Subscription mySubscription;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<UserFunding> userFundings = new ArrayList<>();


    public UserDto toDto() {
        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .loginId(loginId)
                .password(password)
                .roleType(roleType)
                .email(email)
                .build();

        return userDto;
    }

    //==연관관계 편의 메소드==//
    public void addUserFunding(UserFunding userFunding) {
        this.userFundings.add(userFunding);
        userFunding.setUser(this);
    }

    public void addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.setUser(this);
    }

    public void setSubscription(Subscription subscription) {
        this.mySubscription = subscription;
        subscription.setSubscriber(this);
    }

    public void addBoard(Board board){
        this.boardList.add(board);
        board.setUser(this);
    }

    //==비즈니스 로직==//

    //유저의 구독상태에 따른 roleType udpate
    //가입을 하고 이메일 인증을하면 준회원 -> 정회원
    public void updateRoleTypeForJoin() {
        this.roleType = RoleType.REGULAR;
    }

    //구독결제를 하면 roleType udpate
    //구독결제를 취소하면 roleType update
    public void updateRoleTypeForPayment(){
        if(this.roleType == RoleType.REGULAR)
            this.roleType = RoleType.SUBSCRIBER;
        else if(this.roleType == RoleType.SUBSCRIBER)
            this.roleType = RoleType.REGULAR;
    }
}
