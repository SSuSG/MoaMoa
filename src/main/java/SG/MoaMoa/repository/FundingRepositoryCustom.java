package SG.MoaMoa.repository;


import SG.MoaMoa.domain.Funding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface FundingRepositoryCustom {
    Slice<Funding> findAllCustom(Pageable pageable);
    Page<Funding> findProceedingFundingByPaging(Pageable pageable);
    Page<Funding> findReadyFundingByPaging(Pageable pageable);

    List<Funding> findProceedingFunding();
    List<Funding> findReadyFunding();
}
