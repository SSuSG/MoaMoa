package SG.MoaMoa.repository;


import SG.MoaMoa.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
