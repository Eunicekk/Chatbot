package com.example.app.mapper;

import com.example.app.dto.ReplyDto;
import com.example.app.vo.ReplyVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class ReplyMapperTest {
    @Autowired
    private ReplyMapper replyMapper;
    private ReplyDto replyDto;

    @BeforeEach
    void setUp() {
        replyDto = new ReplyDto();
        replyDto.setReplyContent("test");
        replyDto.setUserNumber(5L);
        replyDto.setBoardNumber(111L);
    }

    @Test
    void insert() {
        replyMapper.insert(replyDto);
        List<ReplyVo> list = replyMapper.selectList(111L);
        log.info(list.get(0).getUserId() + "===================================");
        assertThat(list.get(0).getReplyContent()).isEqualTo(replyDto.getReplyContent());
    }

    @Test
    void selectList() {
    }

    @Test
    void select() {
        assertThat(replyMapper.select(1L).getUserId()).isEqualTo("aaa");
    }

    @Test
    void update() {
        replyDto.setReplyNumber(1L);
        replyDto.setReplyContent("update");
        replyMapper.update(replyDto);

        assertThat(replyMapper.select(1L).getReplyContent()).isEqualTo("update");
    }

    @Test
    void delete() {
        replyMapper.delete(1L);
        assertThat(replyMapper.selectList(replyDto.getBoardNumber()).size()).isEqualTo(1);
    }
}