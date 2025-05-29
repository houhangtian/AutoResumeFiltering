package com.peopledaily.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "positions")
public class Position {
    @Id
    private String id;

    // 基本信息
    private String title;
    private String department;
    private String location;
    private String type; // 全职、兼职、实习等
    private String salaryRange;
    private Integer headcount;
    private String status; // 招聘中、已结束等

    // 岗位要求
    private List<String> requiredSkills; // 必备技能
    private List<String> preferredSkills; // 加分技能
    private String educationRequirement; // 学历要求
    private String experienceRequirement; // 经验要求
    private List<String> certificates; // 必需证书

    // 岗位职责
    private List<String> responsibilities;

    // 岗位权重配置（用于简历评分）
    private Map<String, Double> scoreWeights;

    // 部门负责人
    private String departmentManager;
    private String recruiter;

    // 统计信息
    private Integer totalApplications;
    private Integer shortlistedCount;
    private Integer interviewedCount;
    private Integer offeredCount;
    private Integer acceptedCount;

    // 时间信息
    private Date publishDate;
    private Date deadline;
    private Date createTime;
    private Date updateTime;

    // 关键词
    private List<String> keywords;

    // 岗位描述
    private String description;

    // 工作地点详细信息
    private LocationDetail locationDetail;

    // 面试流程
    private List<InterviewStage> interviewProcess;

    // 新增字段
    private String workMode;              // 工作方式
    private String reportTo;              // 汇报对象
    private List<String> benefits;        // 福利待遇
}

@Data
class LocationDetail {
    private String city;
    private String district;
    private String address;
    private String workingHours;
    private String transportationInfo;
}

@Data
class InterviewStage {
    private String stageName;
    private String description;
    private Integer sequence;
    private String assessmentMethod;
    private List<String> interviewers;
} 