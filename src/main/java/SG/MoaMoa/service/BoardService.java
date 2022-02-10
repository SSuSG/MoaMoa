package SG.MoaMoa.service;


import SG.MoaMoa.domain.Board;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.BoardDto;
import SG.MoaMoa.repository.BoardRepository;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시글 작성
    @Transactional
    public Long saveBoard(Long userId , BoardDto boardDto){
        User user = userRepository.findById(userId).get();
        Board board = Board.builder().writer(user.getName()).title(boardDto.getTitle()).content(boardDto.getContent()).build();
        user.addBoard(board);

        return boardRepository.save(board).getId();
    }

    //게시글 보여주기
    //Repository에서 모든 데이터를 조회하여, BoardDTO List에 데이터를 넣어 반환
    @Transactional
    public List<BoardDto> getBoardList() {
        return boardRepository.findAll().stream().map(b -> b.toDto()).collect(Collectors.toList());
    }

    //게시글의 id를 받아 해당 게시글의 데이터만 가져와 화면에 뿌려줘야함.
    @Transactional
    public BoardDto getBoard(Long id) {
        return boardRepository.findById(id).get().toDto();
    }

    //게시글 수정
    @Transactional
    public BoardDto updateBoard(BoardDto boardDto){
        Board board = boardRepository.findById(boardDto.getId()).get();
        board.updateBoard(boardDto);

        return board.toDto();

    }


    //글을 조회하는 페이지에서 ‘삭제’ 버튼을 누르면, /post/{id}으로 Delete 요청을 한다.
    // (만약 1번 글에서 ‘삭제’ 버튼을 클릭하면 /post/1로 접속.)
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

}
