package SG.MoaMoa.controller;

import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.BoardDto;
import SG.MoaMoa.dto.UpdateBoardDto;
import SG.MoaMoa.service.BoardService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String viewBoard(@PathVariable Long id, Model model){
        model.addAttribute("board" , boardService.getBoard(id));
        return "/board/viewBoard";
    }

    //공지사항 쓰기
    @GetMapping("/admin/board")
    public String viewWriteBoardPage(@ModelAttribute BoardDto boardDto){
        return "/board/writeBoard";
    }

    //관리자인이 확인
    @GetMapping("/isAdmin")
    @ResponseBody
    public String isAdmin(@Login User loginUser){
        if(loginUser.getRoleType() == RoleType.ADMIN){
            return "admin";
        }else{
            return "notAdmin";
        }
    }

    //공지사항 쓰기
    @PostMapping("/admin/board")
    public String writeBoard(
            @Login User loginUser ,
            @Valid @ModelAttribute BoardDto boardDto , BindingResult bindingResult
    ){
        if(bindingResult.hasErrors())
            return "/board/writeBoard";
        boardService.saveBoard(loginUser.getId(), boardDto);
        return "redirect:/boards";
    }

    //공지사항 수정
    @PostMapping("/admin/board/update")
    @ResponseBody
    public String updateBoard(
            @RequestBody UpdateBoardDto updateBoardDto
            ){
        if(boardService.updateBoard(updateBoardDto)){
            return "success";
        }
        return "fail";

    }


    //공지사항 삭제
    @ResponseBody
    @PostMapping("/admin/board/delete/{id}")
    public String deleteBoard(
            @PathVariable Long id
    ){
            boardService.deleteBoard(id);
            return "success";
    }


}
