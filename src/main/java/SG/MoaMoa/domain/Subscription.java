package SG.MoaMoa.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Subscription {

    @Id
    @GeneratedValue
    @Column(name = "subscription_id")
    private Long id;

    @OneToOne(mappedBy = "subscription", fetch = FetchType.LAZY)
    private User subscriber;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public static Subscription createSubscription(){
        Subscription subscription = new Subscription();
        subscription.startDate = LocalDateTime.now();
        subscription.endDate = LocalDateTime.now().plusMonths(1);
        return subscription;
    }


    //필요한가??
    //@Enumerated(EnumType.STRING)
    //Subscription subscriptionStatus;
}
