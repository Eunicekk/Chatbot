package com.example.app.schedule;

import com.example.app.dto.FileDto;
import com.example.app.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileSchedule {
    private final FileService fileService;
    @Value("${file.dir}")
    private String fileDir;
    /*
    cron 표현식
    6~7개의 정보를 표현한다.
    초 분 시 일 월 요일 년 (년도는 생략 가능)
    * : 와일드 카드 (매번)
    ? : 설정값 없음 (일, 요일에서만 사용 가능)
    / : 증가 표현 0/15 == 0부터 15마다 (0, 15, 30, 45, ... )

    0 * * * * * * : 매분 0초마다
    0/1 * * * * * : 매분 0초부터 1초 간격으로
    0 0/1 * * * : 매시간 0분부터 1분 간격
    0 0/5 * ? : 매시간 0분부터 5분 간격
    0 0 0/1 * * : 매일 0시부터 시작하여 1시간 마다
    0 0 0 * * ? : 매일 0시
    0 0 0 1 * ? : 매월 1일
    * 10-13 * * * * : 매시간 10, 11, 12, 13분 마다
    */

    @Scheduled(cron = "* * 3 * * *") // 매일 오전 3시마다
    public void checkFiles(){
        log.info("File Check!!");
        log.info("----------------------------");

        // 이전 파일 리스트를 뽑아온다.
        List<FileDto> oldFileList = fileService.findOldList();

        // 이전 파일들의 전체 경로를 List<Path> 타입으로 저장한다.
        List<Path> fileListPaths = oldFileList.stream()
                .map(fileDto -> Paths.get(fileDir, fileDto.getFileUploadPath(),
                        fileDto.getFileUuid() + "_"+ fileDto.getFileName())).collect(Collectors.toList());

        // 이전 썸네일 파일들의 전체 경로를 fileListPaths에 추가한다.
        oldFileList.stream().map(fileDto -> Paths.get(fileDir, fileDto.getFileUploadPath(),
                "th_" + fileDto.getFileUuid() + "_" + fileDto.getFileName())).collect(Collectors.toList())
                .forEach(path -> fileListPaths.add(path));

        // 이전 파일들이 들어있는 경로(파일 이름을 제외한)를 파일 객체로 저장한다.
        File directory = Paths.get(fileDir, getUploadPathOldFile()).toFile();

        // 파일 객체는 폴더 안에 있는 모든 파일 목록을 불러오는 기능이 있다. listFiles()
        File[] files = directory.listFiles(file -> !fileListPaths.contains(file.toPath()));

        if(files == null){ return ; }
        for(File file : files){
            log.info(file.getPath() + " delete!!!");
            file.delete();
        }
    }

    // 1일 전의 파일 경로를 만들어주는 메소드
    private String getUploadPathOldFile(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        return sdf.format(yesterday.getTime());
    }
}
