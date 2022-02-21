package SG.MoaMoa.service;


import SG.MoaMoa.domain.Subscription;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.repository.SubscriptionRepository;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static SG.MoaMoa.domain.Subscription.createSubscription;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;


    //자동결제 안될시 user의 roleType -> normal로 변경
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 체크
    public void subscriptionCancel(){
        List<User> allUser = userRepository.findAll();
        for (User user : allUser) {
            //유저마다 구독결제날짜를 체크 , 자동결제를 취소했을시에는 roleType -> normal로 변경
            if(true){ //만약 유저가 자동결제가 아니고 , 구독날짜기한이 다된경우

            }

        }
    }





}
