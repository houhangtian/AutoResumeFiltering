package com.peopledaily.common.constant;

public class SystemConstants {
    // 简历状态
    public static final String RESUME_STATUS_NEW = "NEW";
    public static final String RESUME_STATUS_REVIEWING = "REVIEWING";
    public static final String RESUME_STATUS_SHORTLISTED = "SHORTLISTED";
    public static final String RESUME_STATUS_REJECTED = "REJECTED";
    public static final String RESUME_STATUS_INTERVIEWING = "INTERVIEWING";
    public static final String RESUME_STATUS_OFFERED = "OFFERED";
    public static final String RESUME_STATUS_ACCEPTED = "ACCEPTED";
    public static final String RESUME_STATUS_DECLINED = "DECLINED";

    // 岗位状态
    public static final String POSITION_STATUS_DRAFT = "DRAFT";
    public static final String POSITION_STATUS_PUBLISHED = "PUBLISHED";
    public static final String POSITION_STATUS_CLOSED = "CLOSED";
    public static final String POSITION_STATUS_CANCELLED = "CANCELLED";

    // 面试阶段
    public static final String INTERVIEW_STAGE_INITIAL = "INITIAL";
    public static final String INTERVIEW_STAGE_TECHNICAL = "TECHNICAL";
    public static final String INTERVIEW_STAGE_HR = "HR";
    public static final String INTERVIEW_STAGE_LEADER = "LEADER";

    // 文件类型
    public static final String FILE_TYPE_PDF = "PDF";
    public static final String FILE_TYPE_WORD = "WORD";
    public static final String FILE_TYPE_IMAGE = "IMAGE";

    // 评分维度
    public static final String SCORE_DIMENSION_EDUCATION = "EDUCATION";
    public static final String SCORE_DIMENSION_EXPERIENCE = "EXPERIENCE";
    public static final String SCORE_DIMENSION_SKILLS = "SKILLS";
    public static final String SCORE_DIMENSION_CERTIFICATES = "CERTIFICATES";

    // 缓存key前缀
    public static final String CACHE_PREFIX_RESUME = "RESUME:";
    public static final String CACHE_PREFIX_POSITION = "POSITION:";
    public static final String CACHE_PREFIX_USER = "USER:";

    // 安全相关
    public static final String JWT_SECRET_KEY = "your-secret-key";
    public static final long JWT_EXPIRATION = 86400000; // 24小时
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_HR = "ROLE_HR";
    public static final String ROLE_INTERVIEWER = "ROLE_INTERVIEWER";

    // 分页默认值
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    // 其他系统配置
    public static final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
} 