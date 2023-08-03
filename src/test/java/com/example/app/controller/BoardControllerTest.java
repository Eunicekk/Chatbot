package com.example.app.controller;

import com.example.app.service.BoardService;
import com.example.app.vo.BoardVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BoardService boardService;
    private BoardVo boardVo;

    @BeforeEach
    void setUp(){
        boardVo = new BoardVo();
        boardVo.setUserId("aaa");
        boardVo.setBoardTitle("Test Title");
        boardVo.setBoardContent("Test Content");
        boardVo.setUserNumber(1L);
    }

//    @Test
//    void showBoardList() throws Exception {
//        doReturn(List.of(boardVo)).when(boardService).findAll();
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(boardService, times(1)).findAll();
//    }

    @Test
    void boardWrite() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/board/write")
//                .sessionAttr("userNumber", 1L)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}