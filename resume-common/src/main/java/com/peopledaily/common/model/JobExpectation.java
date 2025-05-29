package com.peopledaily.common.model;

import lombok.Data;
import java.util.List;

@Data
public class JobExpectation {
    private String jobType;           // 期望工作类型
    private List<String> preferredLocations;  // 期望工作地点
    private String expectedSalary;    // 期望薪资
    private String jobStatus;         // 求职状态（在职/离职/应届）
    private String arrivalTime;       // 到岗时间
    private String workMode;          // 工作方式（全职/兼职/实习）
} 