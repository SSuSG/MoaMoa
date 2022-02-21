package SG.MoaMoa.repository;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.FundingStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static SG.MoaMoa.domain.QFunding.funding;


@RequiredArgsConstructor
public class FundingRepositoryImpl implements FundingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //진행중인 펀딩만 페이징
    @Override
    public Slice<Funding> findAllCustom(Pageable pageable) {

        QueryResults<Funding> result = queryFactory
                .selectFrom(funding)
                .where(funding.fundingStatus.eq(FundingStatus.PROCEEDING))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetchResults();

        List<Funding> content = result.getResults();
        System.out.println("content.size() = " + content.size());
        System.out.println("pageable.getPageSize() = " + pageable.getPageSize());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }


    @Override
    public Page<Funding> findProceedingFundingByPaging(Pageable pageable){
        List<Funding> content = queryFactory
                .selectFrom(funding)
                .where(funding.fundingStatus.eq(FundingStatus.PROCEEDING))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(funding.count())
                .from(funding)
                .where(funding.fundingStatus.eq(FundingStatus.PROCEEDING));

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

