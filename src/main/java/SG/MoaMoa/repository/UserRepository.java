package SG.MoaMoa.repository;


import SG.MoaMoa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByLoginId(String loginId);

    public boolean existsByLoginId(String loginId);

    public Optional<User> findByName(String name);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByNameAndEmail(String name,String email);

    public Optional<User> findByNameAndEmailAndLoginId(String name,String email ,String loginId);
}
