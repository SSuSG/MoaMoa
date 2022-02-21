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
    private int money;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "user" , cascade =  CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade =  CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

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
                .money(money)
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
        this.subscription = subscription;
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
    public void updateRoleTypeForSubscription(){
        if(this.roleType == RoleType.REGULAR)
            this.roleType = RoleType.SUBSCRIBER;
        else if(this.roleType == RoleType.SUBSCRIBER)
            this.roleType = RoleType.REGULAR;
    }
    //회원가입시 초기화
    public void join(int money , String authenticationKey , RoleType roleType){
        this.money = 0;
        this.authenticationKey = authenticationKey;
        this.roleType = RoleType.ASSOCIATE;
    }

    //돈 충전
    public void chargeMoney(int money){
        this.money += money;
    }

    //환불 받을때
    public void refundMoney(int money){
        this.money = money;
    }

    //돈 차감
    public void spendMoney(int money){this.money -= money;}
}
