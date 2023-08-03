package com.example.app.controller;

import com.example.app.aspect.annotation.LoggingPointcut;
import com.example.app.dto.BoardDto;
import com.example.app.service.BoardService;
import com.example.app.service.FileService;
import com.example.app.vo.BoardVo;
import com.example.app.vo.Criteria;
import com.example.app.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final FileService fileService;

    @LoggingPointcut
    @GetMapping("/list")
    public String showBoardList(Criteria criteria, Model model){
        List<BoardVo> boardList = boardService.findAll(criteria);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageInfo", new PageVo(criteria, boardService.getTotal()));
        return "board/board";
    }

    @GetMapping("/write")
    public String boardWrite(HttpServletRequest req){
        return "board/boardWrite";
    }

    @PostMapping("/write")
    public RedirectView boardWrite(BoardDto boardDto, HttpServletRequest req, RedirectAttributes redirectAttributes, @RequestParam("boardFile") List<MultipartFile> files){
        // RedirectAttributes는 리다이렉트 전용 Model 객체라고 생각하면 된다.
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");
        boardDto.setUserNumber(userNumber);
        boardService.register(boardDto);

        // 리다이렉트를 사용할 때 데이터를 전송하는 2가지 방법
        // 쿼리스트링으로 데이터를 전송한다. (url에 쿼리스트링이 생긴다.)
        // 권장하는 사용 방법 : 컨트롤러에서 데이터를 사용하는 경우
        // redirectAttributes.addAttribute("boardNumber", boardDto.getBoardNumber());

        // 플래쉬를 사용하여 데이터를 전송한다.
        // 권장하는 사용 방법 : 화면에서 데이터를 사용하는 경우
        redirectAttributes.addFlashAttribute("boardNumber", boardDto.getBoardNumber());

        if(files != null){
            try {
                fileService.registerAndSaveFiles(files, boardDto.getBoardNumber());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new RedirectView("/board/list");
    }

    @GetMapping("/view")
    public String boardView(Long boardNumber, Model model){
        BoardVo boardVo = boardService.findBoard(boardNumber);
        model.addAttribute("board", boardVo);
        return "board/boardView";
    }

    @GetMapping("/modify")
    public String boardModify(Long boardNumber, Model model){
        BoardVo boardVo = boardService.findBoard(boardNumber);
        model.addAttribute("board", boardVo);
        return "board/boardModify";
    }

    @PostMapping("/modify")
    public RedirectView boardModify(BoardDto boardDto, RedirectAttributes redirectAttributes, @RequestParam("boardFile")List<MultipartFile> files){
        try {
            boardService.modify(boardDto, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        redirectAttributes.addAttribute("boardNumber", boardDto.getBoardNumber());
        return new RedirectView("/board/view");
    }

    @GetMapping("/remove")
    public RedirectView boardRemove(Long boardNumber){
        boardService.remove(boardNumber);
        return new RedirectView("/board/list");
    }
}
