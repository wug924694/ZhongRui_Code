package com.zhongrui.controller;


import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.file.FastDFSFile;
import com.zhongrui.util.FastDFSUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/upload")
public class FileUploadController {

    /**
     * 文件上传
     * @return
     */
    @PostMapping
    public Result upload(@PathVariable("file")MultipartFile file) throws Exception {//过滤多余表单项
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String[] upload = FastDFSUtils.upload(fastDFSFile);
        String url = "192.168.200.128:8080/"+upload[0]+"/"+upload[1];
        return new Result(true, StatusCode.OK,"上传成功",url);
    }
}
