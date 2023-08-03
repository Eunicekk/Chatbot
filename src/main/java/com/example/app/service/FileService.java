package com.example.app.service;

import com.example.app.dto.FileDto;
import com.example.app.mapper.BoardMapper;
import com.example.app.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    // application.properties에 저장해둔 file.dir 프로퍼티 값을 가져온다.
    @Value("${file.dir}")
    private String fileDir;

    public void register(FileDto fileDto){
        if(fileDto == null){
            throw new IllegalArgumentException("파일 정보가 누락되었습니다.");
        }
        fileMapper.insert(fileDto);
    }

    public void remove(Long boardNumber){
        if(boardNumber == null){
            throw new IllegalArgumentException("게시물 번호가 누락되었습니다.");
        }

        List<FileDto> fileList = findList(boardNumber);
        for(FileDto file : fileList){
            File target = new File(fileDir, file.getFileUploadPath() + "/" + file.getFileUuid() + "_" + file.getFileName());
            File thumbnail = new File(fileDir, file.getFileUploadPath() + "/th_" + file.getFileUuid() + "_" + file.getFileName());

            if(target.exists()){
                target.delete();
            }
            if(thumbnail.exists()){
                thumbnail.delete();
            }
        }

        fileMapper.delete(boardNumber);
    }

    public List<FileDto> findList(Long boardNumber){
        return fileMapper.selectList(boardNumber);
    }

    // 파일 저장 처리
    public FileDto saveFile(MultipartFile file) throws IOException {
        // 사용자가 올린 파일 이름 (확장자를 포함)
        String originName = file.getOriginalFilename();
        originName = originName.replaceAll("\\s+", ""); // 파일 이름에 공백이 들어오면 처리해준다.
        // 파일 이름에 붙여줄 uuid 생성 (파일이름 중복이 나오지 않게 처리)
        UUID uuid = UUID.randomUUID();
        // uuid와 파일 이름을 합쳐준다.
        String sysName = uuid.toString() + "_" + originName;

        File uploadPath = new File(fileDir, getUploadPath());

        // 경로가 존재하지 않는다면(폴더가 없다면)
        if(!uploadPath.exists()){
            // 경로에 필요한 폴더를 생성한다.
            uploadPath.mkdirs();
        }

        // 전체 경로와 파일 이름을 연결한다.
        File uploadFile = new File(uploadPath, sysName);

        // 매개변수로 받은 파일을 우리가 만든 경로와 이름으로 저장한다.
        // transferTo(경로)
        // MultipartFile 객체를 실제로 저장시킨다.
        // 저장시킬 경로와 이름을 매개변수로 넘겨주면 된다.
        file.transferTo(uploadFile);

        // 썸네일 저장처리
        // 이미지 파일인 경우에만 처리하는 조건식
        if(Files.probeContentType(uploadFile.toPath()).startsWith("image")){
            FileOutputStream out = new FileOutputStream(new File(uploadPath, "th_" + sysName));
            Thumbnailator.createThumbnail(file.getInputStream(), out, 300, 200);
            out.close();
        }

        // boardNumber를 제외한 모든 정보를 가진 FileDto를 반환한다.
        FileDto fileDto = new FileDto();
        fileDto.setFileUuid(uuid.toString());
        fileDto.setFileName(originName);
        fileDto.setFileUploadPath(getUploadPath());

        return fileDto;
    }

    /**
     * 파일 리스트를 DB 등록 및 저장 처리
     *
     * @param files 여러 파일을 담은 리스트
     * @param boardNumber 파일이 속하는 게시글 번호
     * @throws IOException
     */
    public void registerAndSaveFiles(List<MultipartFile> files, Long boardNumber) throws IOException {
        for(MultipartFile file : files){
            FileDto fileDto = saveFile(file);
            fileDto.setBoardNumber(boardNumber);
            register(fileDto);
        }
    }

    // 파일이 저장되는 하위 경로를 현재 날짜로 설정할 것이기 때문에 현재 날짜를 구한다.
    private String getUploadPath(){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    public List<FileDto> findOldList(){
        return fileMapper.selectOldList();
    }
}
