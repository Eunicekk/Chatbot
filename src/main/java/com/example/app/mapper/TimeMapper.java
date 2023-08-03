package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

// 이 인터페이스를 빈으로 등록하고 매퍼를 사용하기 위한 인터페이스인 것을
// 알려주기 위해 @Mapper 어노테이션을 사용한다.
@Mapper
public interface TimeMapper {
    // Mapper.xml의 쿼리 id와 일치하는 메소드 이름을 사용하면 알아서 매핑된다.
    String getTime();

    @Select("SELECT SYSDATE FROM DUAL")
    String getTime2();
}
