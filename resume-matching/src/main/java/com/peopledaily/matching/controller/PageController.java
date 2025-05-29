package com.peopledaily.matching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.peopledaily.common.model.Position;
import com.peopledaily.common.model.Resume;
import com.peopledaily.matching.service.ResumeMatchingService;

@Controller
public class PageController {

    @Autowired
    private ResumeMatchingService resumeMatchingService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/match")
    public String matchPage() {
        return "match";
    }

    @PostMapping("/match/calculate")
    public String calculateMatch(
            @RequestParam("resumeId") String resumeId,
            @RequestParam("positionId") String positionId,
            Model model) {
        // TODO: 从数据库获取简历和岗位信息
        Resume resume = new Resume();
        Position position = new Position();
        
        double score = resumeMatchingService.calculateMatchScore(resume, position);
        
        model.addAttribute("score", score);
        model.addAttribute("resume", resume);
        model.addAttribute("position", position);
        
        return "match-result";
    }
} 