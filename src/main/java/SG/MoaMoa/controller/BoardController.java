package SG.MoaMoa.controller;

import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.BoardDto;
import SG.MoaMoa.service.BoardService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //공지사항 리스트
    @GetMapping("/boards")
    public String getBoardList(Model model){
        model.addAttribute("boardList",boardService.getBoardList());
        return "/board/boards";
    }

    //공지사항 읽기
    @GetMapping("/board/{id}")
    public String viewBoard(
            @PathVariable Long id, Model model){
        model.addAttribute("board" , boardService.getBoard(id));
        return "/board/viewBoard";
    }

    //공지사항 쓰기
    @GetMapping("/admin/board")
    public String viewWriteBoardPage(@ModelAttribute BoardDto boardDto){
        return "/board/writeBoard";
    }

    //공지사항 쓰기
    @PostMapping("/admin/board")
    public String writeBoard(
            @Login User loginUser ,
            @ModelAttribute BoardDto boardDto
    ){
        log.info("Title : {}",boardDto.getTitle());
        log.info("Content : {}",boardDto.getContent());
        boardService.saveBoard(loginUser.getId(), boardDto);
        return "redirect:/boards";
    }

}
