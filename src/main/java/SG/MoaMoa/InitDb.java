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
                    .discountPrice(9000)
                    .menu("모다카레")
                    .restaurantName("모다모다")
                    .imageList(images1)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("모다모다는 샤로수길 중앙에 위치해있는 카레 전문점입니다.")
                    .information("서울 관악구 관악로14길 36 1층")
                    .notice("2호선 서울대입구역 2번 출구로 나와서 직진하시다가 스타벅스 리저브 카페와 올리브영 사이 골목이 보입니다.\n" +
                            "좌회전으로 꺽어서 들어오셔서 150미터 쯤 걸어들어오시면 아기자기하고 노란 매장이 모다모다입니다.")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding1.getImageList().add(Image.builder().uploadImageName("ex모다모다1.jpg").storeImageName("모다모다1.jpg").isMain(true).funding(funding1).build());
            funding1.getImageList().add(Image.builder().uploadImageName("ex모다모다2.jpg").storeImageName("모다모다2.jpg").isMain(false).funding(funding1).build());
            funding1.getImageList().add(Image.builder().uploadImageName("ex모다모다3.jpg").storeImageName("모다모다3.jpg").isMain(false).funding(funding1).build());
            funding1.getImageList().add(Image.builder().uploadImageName("ex모다모다4.jpg").storeImageName("모다모다4.jpg").isMain(false).funding(funding1).build());
            funding1.getImageList().add(Image.builder().uploadImageName("ex모다모다5.jpg").storeImageName("모다모다5.jpg").isMain(false).funding(funding1).build());

            Funding funding2 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(30)
                    .nowFundingCount(0)
                    .price(15000)
                    .discountPrice(13500)
                    .menu("사케롤 정식")
                    .restaurantName("동경산책")
                    .imageList(images2)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusHours(1))
                    .introduction("일본 가정식 동경 산책입니다")
                    .information("서울 관악구 관악로14길 30")
                    .notice("")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding2.getImageList().add(Image.builder().uploadImageName("ex동경산책1.jpg").storeImageName("동경산책1.jpg").isMain(true).funding(funding2).build());
            funding2.getImageList().add(Image.builder().uploadImageName("ex동경산책2.jpg").storeImageName("동경산책2.jpg").isMain(false).funding(funding2).build());
            funding2.getImageList().add(Image.builder().uploadImageName("ex동경산책3.jpg").storeImageName("동경산책3.jpg").isMain(false).funding(funding2).build());
            funding2.getImageList().add(Image.builder().uploadImageName("ex동경산책4.jpg").storeImageName("동경산책4.jpg").isMain(false).funding(funding2).build());
            funding2.getImageList().add(Image.builder().uploadImageName("ex동경산책5.jpg").storeImageName("동경산책5.jpg").isMain(false).funding(funding2).build());

            Funding funding3 = Funding.builder()
                    .minFundingCount(20)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(16000)
                    .discountPrice(14600)
                    .menu("게살파스타")
                    .restaurantName("모힝")
                    .imageList(images3)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(5))
                    .introduction("퓨전양식을 즐길수 있는 곳")
                    .information("서울 관악구 봉천로62길 7")
                    .notice("낙성대역 4번 출구로 나오셔서 직진, 하나님의 교회를 끼고 좌회전 해서 건널목을 건너면 대각선 방향으로\n" +
                            "로향양꼬치(CU편의점 맞은편)가 보입니다. 바로 그 뒷건물 2층입니다. (낙성대 새마을식당 건너편)")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding3.getImageList().add(Image.builder().uploadImageName("ex모힝1.jpg").storeImageName("모힝1.jpg").isMain(true).funding(funding3).build());
            funding3.getImageList().add(Image.builder().uploadImageName("ex모힝2.jpg").storeImageName("모힝2.jpg").isMain(false).funding(funding3).build());
            funding3.getImageList().add(Image.builder().uploadImageName("ex모힝3.jpg").storeImageName("모힝3.jpg").isMain(false).funding(funding3).build());
            funding3.getImageList().add(Image.builder().uploadImageName("ex모힝4.jpg").storeImageName("모힝4.jpg").isMain(false).funding(funding3).build());
            funding3.getImageList().add(Image.builder().uploadImageName("ex모힝5.jpg").storeImageName("모힝5.jpg").isMain(false).funding(funding3).build());
            funding3.getImageList().add(Image.builder().uploadImageName("ex모힝6.jpg").storeImageName("모힝6.jpg").isMain(false).funding(funding3).build());


            Funding funding4 = Funding.builder()
                    .minFundingCount(30)
                    .maxFundingCount(100)
                    .nowFundingCount(0)
                    .price(8000)
                    .discountPrice(7200)
                    .menu("스페셜텐동")
                    .restaurantName("텐동요츠야")
                    .imageList(images4)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(13))
                    .introduction("신선한 재료 본연의 맛과 깨끗한 기름으로 튀겨 자극적이지않은 담백한 튀김덮밥 한그릇")
                    .information("서울 관악구 관악로14길 35 1층")
                    .notice("")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding4.getImageList().add(Image.builder().uploadImageName("ex텐동1.jpg").storeImageName("텐동1.jpg").isMain(true).funding(funding4).build());
            funding4.getImageList().add(Image.builder().uploadImageName("ex텐동2.jpg").storeImageName("텐동2.jpg").isMain(false).funding(funding4).build());
            funding4.getImageList().add(Image.builder().uploadImageName("ex텐동3.jpg").storeImageName("텐동3.jpg").isMain(false).funding(funding4).build());
            funding4.getImageList().add(Image.builder().uploadImageName("ex텐동4.jpg").storeImageName("텐동4.jpg").isMain(false).funding(funding4).build());
            funding4.getImageList().add(Image.builder().uploadImageName("ex텐동5.jpg").storeImageName("텐동5.jpg").isMain(false).funding(funding4).build());

            Funding funding5 = Funding.builder()
                    .minFundingCount(20)
                    .maxFundingCount(40)
                    .nowFundingCount(0)
                    .price(30000)
                    .discountPrice(27000)
                    .menu("양념대창")
                    .restaurantName("안녕부산")
                    .imageList(images5)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(15))
                    .introduction("부산 맛을 재해석하여 다양한 부산음식을 소개합니다.")
                    .information("서울 관악구 관악로14길 45 1층")
                    .notice("서울대입구역 2번출구로 나와 300m 직진 후 올리브영 골목(샤로수길)으로 들어와 300m 다시 직진하면 있습니다")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding5.getImageList().add(Image.builder().uploadImageName("ex안녕부산1.jpg").storeImageName("안녕부산1.jpg").isMain(true).funding(funding5).build());
            funding5.getImageList().add(Image.builder().uploadImageName("ex안녕부산2.jpg").storeImageName("안녕부산2.jpg").isMain(false).funding(funding5).build());
            funding5.getImageList().add(Image.builder().uploadImageName("ex안녕부산3.jpg").storeImageName("안녕부산3.jpg").isMain(false).funding(funding5).build());
            funding5.getImageList().add(Image.builder().uploadImageName("ex안녕부산4.jpg").storeImageName("안녕부산4.jpg").isMain(false).funding(funding5).build());
            funding5.getImageList().add(Image.builder().uploadImageName("ex안녕부산5.jpg").storeImageName("안녕부산5.jpg").isMain(false).funding(funding5).build());

            Funding funding6 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(30000)
                    .discountPrice(27000)
                    .menu("런치2인세트")
                    .restaurantName("아마")
                    .imageList(images6)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("인도 커리집")
                    .information("서울 관악구 관악로14길 11 3층")
                    .notice("")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding6.getImageList().add(Image.builder().uploadImageName("ex아마1.jpg").storeImageName("아마1.jpg").isMain(true).funding(funding6).build());
            funding6.getImageList().add(Image.builder().uploadImageName("ex아마2.jpg").storeImageName("아마2.jpg").isMain(false).funding(funding6).build());
            funding6.getImageList().add(Image.builder().uploadImageName("ex아마3.jpg").storeImageName("아마3.jpg").isMain(false).funding(funding6).build());
            funding6.getImageList().add(Image.builder().uploadImageName("ex아마4.jpg").storeImageName("아마4.jpg").isMain(false).funding(funding6).build());
            funding6.getImageList().add(Image.builder().uploadImageName("ex아마5.jpg").storeImageName("아마5.jpg").isMain(false).funding(funding6).build());

            Funding funding7 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(15000)
                    .discountPrice(13000)
                    .menu("삼백돈카츠")
                    .restaurantName("삼백돈")
                    .imageList(images7)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("샤로수길 맛집, 삼백돈 돈가츠입니다!")
                    .information("서울 관악구 남부순환로230길 48")
                    .notice("서울대입구역 1번 출구로 나와서 200m 직진 후 구이가서울대입구역점 골목으로 200m 직진하시면 됩니다.")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding7.getImageList().add(Image.builder().uploadImageName("ex삼백돈1.jpg").storeImageName("삼백돈1.jpg").isMain(true).funding(funding7).build());
            funding7.getImageList().add(Image.builder().uploadImageName("ex삼백돈2.jpg").storeImageName("삼백돈2.jpg").isMain(false).funding(funding7).build());
            funding7.getImageList().add(Image.builder().uploadImageName("ex삼백돈3.jpg").storeImageName("삼백돈3.jpg").isMain(false).funding(funding7).build());
            funding7.getImageList().add(Image.builder().uploadImageName("ex삼백돈4.jpg").storeImageName("삼백돈4.jpg").isMain(false).funding(funding7).build());
            funding7.getImageList().add(Image.builder().uploadImageName("ex삼백돈5.jpg").storeImageName("삼백돈5.jpg").isMain(false).funding(funding7).build());

            Funding funding8 = Funding.builder()
                    .minFundingCount(10)
                    .maxFundingCount(50)
                    .nowFundingCount(0)
                    .price(12000)
                    .discountPrice(10800)
                    .menu("부타동")
                    .restaurantName("킷사서울")
                    .imageList(images8)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusSeconds(20))
                    .introduction("안녕하세요^^ 샤로수길맛집 킷사서울입니다. '킷 사서울'은 교토의 오반자이에서 영감을 받아 다 양한 일본 한상차림을 준비하였습니다.")
                    .information("서울 관악구 남부순환로226길 31 2층 킷사서울")
                    .notice("")
                    .fundingStatus(FundingStatus.PROCEEDING)
                    .build();

            funding8.getImageList().add(Image.builder().uploadImageName("ex킷사서울1.jpg").storeImageName("킷사서울1.jpg").isMain(true).funding(funding8).build());
            funding8.getImageList().add(Image.builder().uploadImageName("ex킷사서울2.jpg").storeImageName("킷사서울2.jpg").isMain(false).funding(funding8).build());
            funding8.getImageList().add(Image.builder().uploadImageName("ex킷사서울3.jpg").storeImageName("킷사서울3.jpg").isMain(false).funding(funding8).build());
            funding8.getImageList().add(Image.builder().uploadImageName("ex킷사서울4.jpg").storeImageName("킷사서울4.jpg").isMain(false).funding(funding8).build());
            funding8.getImageList().add(Image.builder().uploadImageName("ex킷사서울5.jpg").storeImageName("킷사서울5.jpg").isMain(false).funding(funding8).build());
            funding8.getImageList().add(Image.builder().uploadImageName("ex킷사서울6.jpg").storeImageName("킷사서울6.jpg").isMain(false).funding(funding8).build());


            User user1 = User.builder()
                    .name("Admin")
                    .loginId("Admin")
                    .password("123")
                    .email("admin@naver.com")
                    .money(0)
                    .roleType(RoleType.ADMIN)
                    .build();

            User user2 = User.builder()
                    .name("user1")
                    .loginId("user1")
                    .password("123")
                    .email("user1@naver.com")
                    .money(0)
                    .roleType(RoleType.REGULAR)
                    .build();

            User user3 = User.builder()
                    .name("user2")
                    .loginId("user2")
                    .password("123")
                    .email("user2@naver.com")
                    .money(0)
                    .roleType(RoleType.REGULAR)
                    .build();

            Board board1 = Board.builder().title("예시1").writer("ex").build();
            Board board2 = Board.builder().title("예시2").writer("ex").build();
            Board board3 = Board.builder().title("예시3").writer("ex").build();
            Board board4 = Board.builder().title("예시4").writer("ex").build();

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

            em.persist(board1);
            em.persist(board2);
            em.persist(board3);
            em.persist(board4);

        }



    }

}
