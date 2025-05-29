package com.peopledaily.parser.controller;

import com.peopledaily.common.model.ApiResponse;
import com.peopledaily.common.model.Resume;
import com.peopledaily.parser.service.ResumeParserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resume-parser")
@Api(tags = "简历解析服务")
public class ResumeParserController {

    @Autowired
    private ResumeParserService resumeParserService;

    @PostMapping("/parse")
    @ApiOperation("解析简历文件")
    public ApiResponse<Resume> parseResume(
            @ApiParam(value = "简历文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            Resume resume = resumeParserService.parseResume(file);
            return ApiResponse.success(resume);
        } catch (Exception e) {
            return ApiResponse.error("简历解析失败：" + e.getMessage());
        }
    }

    @PostMapping("/parse/batch")
    @ApiOperation("批量解析简历文件")
    public ApiResponse<List<Resume>> parseResumes(
            @ApiParam(value = "简历文件列表", required = true)
            @RequestParam("files") List<MultipartFile> files) {
        try {
            List<Resume> resumes = new ArrayList<>();
            for (MultipartFile file : files) {
                Resume resume = resumeParserService.parseResume(file);
                resumes.add(resume);
            }
            return ApiResponse.success(resumes);
        } catch (Exception e) {
            return ApiResponse.error("批量简历解析失败：" + e.getMessage());
        }
    }

    @GetMapping("/health")
    @ApiOperation("健康检查")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.success("Resume Parser Service is running");
    }
} 