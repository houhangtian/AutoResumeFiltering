package com.peopledaily.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "resumes")
public class Resume {
    @Id
    private String id;
    
    // 基本信息
    private String name;
    private String gender;
    private Integer age;
    private Date birthDate;
    private String phone;
    private String email;
    private String currentLocation;
    
    // 教育背景
    private List<Education> education;
    
    // 工作经验
    private List<WorkExperience> workExperience;
    
    // 项目经验
    private List<ProjectExperience> projectExperience;
    
    // 技能标签
    private List<String> skills;
    
    // 证书资质
    private List<String> certificates;
    
    // 自我评价
    private String selfEvaluation;
    
    // 期望工作
    private JobExpectation jobExpectation;
    
    // 简历评分
    private Map<String, Double> scores;
    
    // 简历状态
    private String status;
    
    // 创建和更新时间
    private Date createTime;
    private Date updateTime;
}

@Data
class Education {
    private String school;
    private String major;
    private String degree;
    private Date startDate;
    private Date endDate;
    private Double gpa;
    private String description;
}

@Data
class WorkExperience {
    private String company;
    private String position;
    private String department;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<String> achievements;
}

@Data
class ProjectExperience {
    private String name;
    private String role;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<String> technologies;
    private List<String> achievements;
}

@Data
class JobExpectation {
    private List<String> positions;
    private List<String> industries;
    private String expectedSalary;
    private List<String> preferredLocations;
    private String jobType; // 全职、兼职、实习等
} 