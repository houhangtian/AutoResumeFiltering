package com.peopledaily.matching.service;

import com.peopledaily.common.model.Position;
import com.peopledaily.common.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResumeMatchingServiceTest {

    @Autowired
    private ResumeMatchingService resumeMatchingService;

    private Resume resume;
    private Position position;

    @BeforeEach
    void setUp() {
        // 创建测试简历
        resume = new Resume();
        resume.setId("1");
        resume.setName("张三");
        resume.setGender("男");
        resume.setPhone("13800138000");
        resume.setEmail("zhangsan@example.com");
        resume.setCurrentLocation("北京");
        
        // 设置教育背景
        Resume.Education education = new Resume.Education();
        education.setSchool("北京大学");
        education.setMajor("计算机科学");
        education.setDegree("硕士");
        education.setStartDate(new Date());
        education.setEndDate(new Date());
        resume.setEducation(Arrays.asList(education));
        
        // 设置工作经验
        Resume.WorkExperience workExp = new Resume.WorkExperience();
        workExp.setCompany("腾讯");
        workExp.setPosition("高级工程师");
        workExp.setDepartment("技术部");
        workExp.setStartDate(new Date());
        workExp.setEndDate(new Date());
        resume.setWorkExperience(Arrays.asList(workExp));
        
        // 设置技能
        resume.setSkills(Arrays.asList("Java", "Spring Boot", "MySQL", "Redis"));
        
        // 设置求职期望
        Resume.JobExpectation jobExp = new Resume.JobExpectation();
        jobExp.setJobType("全职");
        jobExp.setPreferredLocations(Arrays.asList("北京", "上海"));
        resume.setJobExpectation(jobExp);

        // 创建测试岗位
        position = new Position();
        position.setId("1");
        position.setTitle("Java高级工程师");
        position.setDepartment("技术部");
        position.setLocation("北京");
        position.setType("全职");
        position.setDescription("负责核心业务系统开发");
        position.setRequiredSkills(Arrays.asList("Java", "Spring Boot", "MySQL"));
        position.setPreferredSkills(Arrays.asList("Redis", "Kafka"));
        position.setEducationRequirement("硕士");
        position.setExperienceRequirement("3-5年");
    }

    @Test
    void testCalculateMatchScore() {
        double score = resumeMatchingService.calculateMatchScore(resume, position);
        assertTrue(score >= 0 && score <= 100, "匹配分数应该在0-100之间");
        System.out.println("匹配分数: " + score);
    }

    @Test
    void testCalculateMatchScoreWithEmptyResume() {
        Resume emptyResume = new Resume();
        double score = resumeMatchingService.calculateMatchScore(emptyResume, position);
        assertTrue(score >= 0 && score <= 100, "空简历的匹配分数应该在0-100之间");
        System.out.println("空简历匹配分数: " + score);
    }

    @Test
    void testCalculateMatchScoreWithEmptyPosition() {
        Position emptyPosition = new Position();
        double score = resumeMatchingService.calculateMatchScore(resume, emptyPosition);
        assertTrue(score >= 0 && score <= 100, "空岗位的匹配分数应该在0-100之间");
        System.out.println("空岗位匹配分数: " + score);
    }
} 