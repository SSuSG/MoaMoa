package SG.MoaMoa;

import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.FundingStatus;
import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();

    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1(){
            Funding funding1 = Funding.builder()
                    .minFundingCount(1)
                    .maxFundingCount(2)
                    .nowFundingCount(0)
                    .price(10000)
                    .discountPrice(8000)
                    .menu("칼국수")
                    .restaurantName("에비시")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            Funding funding2 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(30)
                    .nowFundingCount(0)
                    .price(20000)
                    .discountPrice(11000)
                    .menu("카레")
                    .restaurantName("큐덮이")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            Funding funding3 = Funding.builder()
                    .minFundingCount(30)
                    .maxFundingCount(500)
                    .nowFundingCount(0)
                    .price(20000)
                    .discountPrice(18000)
                    .menu("덮밥")
                    .restaurantName("시비시")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            Funding funding4 = Funding.builder()
                    .minFundingCount(30)
                    .maxFundingCount(100)
                    .nowFundingCount(0)
                    .price(8000)
                    .discountPrice(6000)
                    .menu("제육")
                    .restaurantName("에스디")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(13))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            Funding funding5 = Funding.builder()
                    .minFundingCount(20)
                    .maxFundingCount(40)
                    .nowFundingCount(0)
                    .price(25000)
                    .discountPrice(20000)
                    .menu("피자")
                    .restaurantName("피오큐")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(15))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            Funding funding6 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(150000)
                    .discountPrice(13000)
                    .menu("돈까스")
                    .restaurantName("케이제이")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            User user1 = User.builder()
                    .name("A")
                    .loginId("qwe")
                    .password("123")
                    .email("qwe@naver.com")
                    .roleType(RoleType.REGULAR)
                    .build();

            User user2 = User.builder()
                    .name("B")
                    .loginId("asd")
                    .password("123")
                    .email("asd@naver.com")
                    .roleType(RoleType.REGULAR)
                    .build();

            User user3 = User.builder()
                    .name("C")
                    .loginId("zxc")
                    .password("123")
                    .email("zxc@naver.com")
                    .roleType(RoleType.REGULAR)
                    .build();

            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(funding1);
            em.persist(funding2);
            em.persist(funding3);
            em.persist(funding4);
            em.persist(funding5);
            em.persist(funding6);

        }



    }

}
