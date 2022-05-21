package com.sammaru5.sammaru.controller;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.FileDB;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.dto.ResponseFile;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.security.AuthUser;
import com.sammaru5.sammaru.service.file.FileStorageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/no-permit/upload/{boardId}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResult<Boolean> uploadFile(@PathVariable Long boardId, @RequestPart(value="article") @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) throws IOException {
        fileStorageService.store(multipartFiles[0]);
        return ApiResult.OK(true);
    }

    @GetMapping("/no-permit/files")
    public ApiResult<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length
            );
        }).collect(Collectors.toList());

        return ApiResult.OK(files);
    }

    @GetMapping("/no-permit/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB file = fileStorageService.getFile(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}
