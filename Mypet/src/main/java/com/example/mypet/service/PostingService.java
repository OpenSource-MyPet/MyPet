package com.example.mypet.service;

import com.example.mypet.entity.PostingEntity;
import com.example.mypet.entity.PostingFileEntity;
import com.example.mypet.memberDTO.PostingDTO;
import com.example.mypet.repository.PostingFileRepository;
import com.example.mypet.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {
    PostingRepository repo ;
    PostingFileRepository fileRepo ;


    @Autowired
    public PostingService(PostingRepository repo, PostingFileRepository fileRepo){
        this.repo = repo ;
        this.fileRepo = fileRepo ;
    }
    public void savetodb(PostingDTO dto) throws IOException {
        if(dto.getFile().isEmpty()){
            PostingEntity entity = PostingEntity.toPostingEntity(dto);
            repo.save(entity);
        }
        else{
            MultipartFile file = dto.getFile();
            String fileName = file.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + fileName;
            String path = "/Users/kimkieun/Documents/mypet_upload/" + storedFileName;
            file.transferTo(new File(path));
            PostingEntity entity = PostingEntity.toPostingFileEntity(dto);
            int saveRid = repo.save(entity).getRid();
            PostingEntity postingEntity = repo.findById(saveRid).get();
            PostingFileEntity fileEntity = PostingFileEntity.toPostingFileEntity(postingEntity, fileName, storedFileName);
            fileRepo.save(fileEntity);
        }
    }

    public List<PostingDTO> getPostings() {
        List<PostingEntity> list = repo.findAll(Sort.by(Sort.Direction.DESC, "rid"));
        List<PostingDTO> dtolist = new ArrayList<>();

        for(PostingEntity element : list){
            dtolist.add(PostingDTO.toPostingDTO(element));
        }
        return dtolist;
    }

    public PostingDTO getPosting(int id) {
        PostingEntity entity = repo.findById(id).get();
        return PostingDTO.toPostingDTO(entity);
    }
}
