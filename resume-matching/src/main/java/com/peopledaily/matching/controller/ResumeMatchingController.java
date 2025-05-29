package com.peopledaily.matching.controller;

import com.peopledaily.common.model.ApiResponse;
import com.peopledaily.common.model.Position;
import com.peopledaily.common.model.Resume;
import com.peopledaily.matching.service.ResumeMatchingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/resume-matching")
@Api(tags = "简历匹配服务")
public class ResumeMatchingController {

    @Autowired
    private ResumeMatchingService resumeMatchingService;

    @PostMapping("/match")
    @ApiOperation("计算单个简历与岗位的匹配度")
    public ApiResponse<Double> calculateMatchScore(
            @RequestBody Map<String, Object> request) {
        try {
            Resume resume = (Resume) request.get("resume");
            Position position = (Position) request.get("position");
            
            if (resume == null || position == null) {
                return ApiResponse.error("简历或岗位信息不能为空");
            }
            
            double score = resumeMatchingService.calculateMatchScore(resume, position);
            return ApiResponse.success(score);
        } catch (Exception e) {
            return ApiResponse.error("计算匹配度失败: " + e.getMessage());
        }
    }

    @PostMapping("/match/batch")
    @ApiOperation("批量计算简历与岗位的匹配度")
    public ApiResponse<Map<String, Double>> calculateBatchMatchScores(
            @RequestBody Map<String, Object> request) {
        try {
            List<Resume> resumes = (List<Resume>) request.get("resumes");
            Position position = (Position) request.get("position");
            
            if (resumes == null || position == null) {
                return ApiResponse.error("简历列表或岗位信息不能为空");
            }
            
            Map<String, Double> scores = new java.util.HashMap<>();
            for (Resume resume : resumes) {
                double score = resumeMatchingService.calculateMatchScore(resume, position);
                scores.put(resume.getId(), score);
            }
            
            return ApiResponse.success(scores);
        } catch (Exception e) {
            return ApiResponse.error("批量计算匹配度失败: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    @ApiOperation("健康检查")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.success("Resume Matching Service is running");
    }
} 