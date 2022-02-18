package SG.MoaMoa.repository;

import SG.MoaMoa.domain.UserFunding;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static SG.MoaMoa.domain.QUserFunding.userFunding;

@RequiredArgsConstructor
public class UserFundingRepositoryImpl implements UserFundingRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public UserFunding findUserFunding(Long userId, Long fundingId) {
        return queryFactory
                .selectFrom(userFunding)
                .where(userFunding.user.id.eq(userId).and(userFunding.funding.id.eq(fundingId)))
                .fetchOne();
    }
}
