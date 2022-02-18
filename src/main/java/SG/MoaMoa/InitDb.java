package SG.MoaMoa;

import SG.MoaMoa.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

            List<Image> images1 = new ArrayList<>();
            List<Image> images2 = new ArrayList<>();
            List<Image> images3 = new ArrayList<>();
            List<Image> images4 = new ArrayList<>();
            List<Image> images5 = new ArrayList<>();
            List<Image> images6 = new ArrayList<>();
            List<Image> images7 = new ArrayList<>();
            List<Image> images8 = new ArrayList<>();

            Funding funding1 = Funding.builder()
                    .minFundingCount(1)
                    .maxFundingCount(2)
                    .nowFundingCount(0)
                    .price(10000)
                    .discountPrice(8000)
                    .menu("황금올리브")
                    .restaurantName("BBQ")
                    .imageList(images1)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding1.getImageList().add(Image.builder().uploadImageName("exbbq1.jpg").storeImageName("bbq1.jpg").isMain(true).funding(funding1).build());

            Funding funding2 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(30)
                    .nowFundingCount(0)
                    .price(20000)
                    .discountPrice(11000)
                    .menu("뿌링클")
                    .restaurantName("BHC")
                    .imageList(images2)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding2.getImageList().add(Image.builder().uploadImageName("exbhc1.jpg").storeImageName("bhc1.jpg").isMain(true).funding(funding2).build());

            Funding funding3 = Funding.builder()
                    .minFundingCount(30)
                    .maxFundingCount(500)
                    .nowFundingCount(0)
                    .price(20000)
                    .discountPrice(18000)
                    .menu("발사믹 치킨")
                    .restaurantName("교촌치킨")
                    .imageList(images3)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding3.getImageList().add(Image.builder().uploadImageName("exkyochon1.jpg").storeImageName("kyochon1.jpg").isMain(true).funding(funding3).build());

            Funding funding4 = Funding.builder()
                    .minFundingCount(30)
                    .maxFundingCount(100)
                    .nowFundingCount(0)
                    .price(8000)
                    .discountPrice(6000)
                    .menu("고추바사삭")
                    .restaurantName("굽네치킨")
                    .imageList(images4)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(13))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding4.getImageList().add(Image.builder().uploadImageName("exgubne1.jpg").storeImageName("gubne1.jpg").isMain(true).funding(funding4).build());

            Funding funding5 = Funding.builder()
                    .minFundingCount(20)
                    .maxFundingCount(40)
                    .nowFundingCount(0)
                    .price(25000)
                    .discountPrice(20000)
                    .menu("순살")
                    .restaurantName("지코바")
                    .imageList(images5)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(15))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding5.getImageList().add(Image.builder().uploadImageName("exgikoba1.jpg").storeImageName("gikoba1.jpg").isMain(true).funding(funding5).build());

            Funding funding6 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(150000)
                    .discountPrice(13000)
                    .menu("양념치킨")
                    .restaurantName("처갓집")
                    .imageList(images6)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding6.getImageList().add(Image.builder().uploadImageName("exchugazip1.jpg").storeImageName("chugazip1.jpg").isMain(true).funding(funding6).build());

            Funding funding7 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(150000)
                    .discountPrice(17000)
                    .menu("블랙알리오")
                    .restaurantName("푸라닭")
                    .imageList(images7)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding7.getImageList().add(Image.builder().uploadImageName("expuradak1.jpg").storeImageName("puradak1.jpg").isMain(true).funding(funding7).build());

            Funding funding8 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(150000)
                    .discountPrice(12000)
                    .menu("후라이드")
                    .restaurantName("후라이드 참 잘하는집")
                    .imageList(images8)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("asd")
                    .information("zxc")
                    .notice("qwe")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding8.getImageList().add(Image.builder().uploadImageName("exhuchamzal1.jpg").storeImageName("huchamzal1.jpg").isMain(true).funding(funding8).build());

            Funding ex1 = Funding.builder()
                    .restaurantName("ex1")
                    .discountPrice(5000)
                    .build();

            Funding ex2 = Funding.builder()
                    .restaurantName("ex2")
                    .discountPrice(5000)
                    .build();

            Funding ex3 = Funding.builder()
                    .restaurantName("ex3")
                    .discountPrice(5000)
                    .build();

            Funding ex4 = Funding.builder()
                    .restaurantName("ex4")
                    .discountPrice(5000)
                    .build();

            Funding ex5 = Funding.builder()
                    .restaurantName("ex5")
                    .discountPrice(5000)
                    .build();

            Funding ex6 = Funding.builder()
                    .restaurantName("ex6")
                    .discountPrice(5000)
                    .build();

            Funding ex7 = Funding.builder()
                    .restaurantName("ex7")
                    .discountPrice(5000)
                    .build();

            Funding ex8 = Funding.builder()
                    .restaurantName("ex8")
                    .discountPrice(5000)
                    .build();

            Funding ex9 = Funding.builder()
                    .restaurantName("ex9")
                    .discountPrice(5000)
                    .build();


            User user1 = User.builder()
                    .name("A")
                    .loginId("qwe")
                    .password("123")
                    .email("qwe@naver.com")
                    .money(0)
                    .roleType(RoleType.ADMIN)
                    .build();

            User user2 = User.builder()
                    .name("B")
                    .loginId("asd")
                    .password("123")
                    .email("asd@naver.com")
                    .money(0)
                    .roleType(RoleType.REGULAR)
                    .build();

            User user3 = User.builder()
                    .name("C")
                    .loginId("zxc")
                    .password("123")
                    .email("zxc@naver.com")
                    .money(0)
                    .roleType(RoleType.REGULAR)
                    .build();

            Board board1 = Board.builder().title("ex1").writer("zzz").build();
            Board board2 = Board.builder().title("ex2").writer("xxx").build();
            Board board3 = Board.builder().title("ex3").writer("ccc").build();
            Board board4 = Board.builder().title("ex4").writer("ddd").build();

            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(funding1);
            em.persist(funding2);
            em.persist(funding3);
            em.persist(funding4);
            em.persist(funding5);
            em.persist(funding6);
            em.persist(funding7);
            em.persist(funding8);
            em.persist(ex1);
            em.persist(ex2);
            em.persist(ex3);
            em.persist(ex4);
            em.persist(ex5);
            em.persist(ex6);
            em.persist(ex7);
            em.persist(ex8);
            em.persist(ex9);

            em.persist(board1);
            em.persist(board2);
            em.persist(board3);
            em.persist(board4);

        }



    }

}
