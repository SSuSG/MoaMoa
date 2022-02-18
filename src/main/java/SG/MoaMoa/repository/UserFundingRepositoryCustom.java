package SG.MoaMoa.repository;

import SG.MoaMoa.domain.UserFunding;

public interface UserFundingRepositoryCustom {

    UserFunding findUserFunding(Long userId, Long fundingId);
}
