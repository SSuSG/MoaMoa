package SG.MoaMoa.repository;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.FundingStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static SG.MoaMoa.domain.QFunding.funding;

@Slf4j
@RequiredArgsConstructor
public class FundingRepositoryImpl implements FundingRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Funding> findProceedingFundingByPaging(Pageable pageable){
        log.info("offset : {}" , pageable.getOffset());

        List<Funding> content = queryFactory
                .selectFrom(funding)
                .where(funding.fundingStatus.eq(FundingStatus.PROCEEDING))
                .orderBy(funding.restaurantName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(funding.count())
                .from(funding)
                .where(funding.fundingStatus.eq(FundingStatus.PROCEEDING));


        System.out.println("count.fetchOne() = " + count.fetchOne());

        return PageableExecutionUtils.getPage(content,pageable, count::fetchOne);
    }

    @Override
    public Page<Funding> findReadyFundingByPaging(Pageable pageable) {

        List<Funding> content = queryFactory
                .selectFrom(funding)
                .where(funding.fundingStatus.eq(FundingStatus.READY))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(funding.count())
                .from(funding)
                .where(funding.fundingStatus.eq(FundingStatus.READY));

        System.out.println("count.fetchOne() = " + count.fetchOne());

        return PageableExecutionUtils.getPage(content,pageable, count::fetchOne);
    }

    @Override
    public List<Funding> searchFunding(String searchName) {
        return queryFactory
                .selectFrom(funding)
                .where(funding.restaurantName.contains(searchName))
                .fetch();

    }

    //존재하면 참 리턴
    @Override
    public boolean isExistFundingName(String searchName) {
        return !(queryFactory
                .selectFrom(funding)
                .where(funding.restaurantName.contains(searchName))
                .fetch().isEmpty());
    }

    @Override
    public List<Funding> findProceedingFunding() {
        return queryFactory
                .selectFrom(funding)
                .where(funding.fundingStatus.eq(FundingStatus.PROCEEDING))
                .fetch();
    }

    @Override
    public List<Funding> findReadyFunding() {
        return queryFactory
                .selectFrom(funding)
                .where(funding.fundingStatus.eq(FundingStatus.READY))
                .fetch();
    }


}

