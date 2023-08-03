package com.example.app.mapper;

import com.example.app.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    public void insert(FileDto fileDto);

    public void delete(Long boardNumber);

    public List<FileDto> selectList(Long boardNumber);

    public List<FileDto> selectOldList();
}
