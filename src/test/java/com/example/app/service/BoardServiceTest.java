package com.example.app.service;

import com.example.app.dto.BoardDto;
import com.example.app.mapper.BoardMapper;
import com.example.app.vo.BoardVo;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@Transactional
@Slf4j
class BoardServiceTest {
    @Mock
    private BoardMapper boardMapper;

    @InjectMocks
    private BoardService boardService;

    private BoardDto boardDto;
    private BoardVo boardVo;

    @BeforeEach
    void setUp() {
        boardDto = new BoardDto();
        boardDto.setBoardTitle("Test Title");
        boardDto.setBoardContent("Test Content");

        boardVo = new BoardVo();
        boardVo.setBoardTitle("Test Title");
        boardVo.setBoardContent("Test Content");
        boardVo.setUserNumber(1L);
        boardVo.setUserId("aaa");
    }

    @Test
    @DisplayName("게시물 등록")
    void register() {
        doNothing().when(boardMapper).insert(any(BoardDto.class));
        boardService.register(boardDto);
        verify(boardMapper, times(1)).insert(boardDto);
    }

    @Test
    @DisplayName("게시물 삭제")
    void remove() {
        doNothing().when(boardMapper).delete(any(Long.class));
        boardService.remove(1L);
        verify(boardMapper, times(1)).delete(1L);
    }

    @Test
    @DisplayName("게시물 변경")
    void update() {
        boardDto.setBoardTitle("Update Title");
        boardDto.setBoardContent("Update Content");

        doNothing().when(boardMapper).update(any(BoardDto.class));
        boardService.modify(boardDto);
        verify(boardMapper, times(1)).update(boardDto);
    }

    @Test
    @DisplayName("게시물 조회")
    void findBoard() {
        doReturn(boardVo).when(boardMapper).select(any(Long.class));
        BoardVo foundBoard = boardService.findBoard(1L);

        assertThat(foundBoard).isEqualTo(boardVo);
        assertThatThrownBy(()->boardService.findBoard(null))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("없습니다");
    }

    @Test
    @DisplayName("게시물 조회 예외")
    void findBoardException(){
        doReturn(null).when(boardMapper).select(any(Long.class));
        assertThatThrownBy(()->boardService.findBoard(1L))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("존재하지");
    }

//    @Test
//    @DisplayName("게시물 전체 조회")
//    void findAll() {
//        doReturn(List.of(boardVo)).when(boardMapper).selectAll();
//        List<BoardVo> foundList = boardService.findAll();
//        assertThat(foundList).contains(boardVo);
//    }
}