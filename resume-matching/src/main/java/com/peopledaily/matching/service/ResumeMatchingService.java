package com.peopledaily.matching.service;

import com.peopledaily.common.model.Position;
import com.peopledaily.common.model.Resume;
import com.peopledaily.common.util.CommonUtils;
import org.springframework.stereotype.Service;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.core.Variable;
import org.tensorflow.types.TFloat32;

import java.util.*;

@Service
public class ResumeMatchingService {

    private final Map<String, SMO> positionModels = new HashMap<>();

    public double calculateMatchScore(Resume resume, Position position) {
        // 计算基础匹配分数
        double baseScore = calculateBaseScore(resume, position);
        
        // 计算技能匹配分数
        double skillScore = calculateSkillScore(resume, position);
        
        // 计算经验匹配分数
        double experienceScore = calculateExperienceScore(resume, position);
        
        // 计算教育背景匹配分数
        double educationScore = calculateEducationScore(resume, position);
        
        // 使用机器学习模型进行最终评分
        double mlScore = calculateMLScore(resume, position);
        
        // 综合评分
        return combineScores(baseScore, skillScore, experienceScore, educationScore, mlScore);
    }

    private double calculateBaseScore(Resume resume, Position position) {
        double score = 0.0;
        
        // 检查工作类型匹配
        if (resume.getJobExpectation() != null && 
            resume.getJobExpectation().getJobType().equals(position.getType())) {
            score += 20;
        }
        
        // 检查地点匹配
        if (resume.getJobExpectation() != null && 
            resume.getJobExpectation().getPreferredLocations().contains(position.getLocation())) {
            score += 20;
        }
        
        return score;
    }

    private double calculateSkillScore(Resume resume, Position position) {
        if (resume.getSkills() == null || position.getRequiredSkills() == null) {
            return 0.0;
        }

        Set<String> resumeSkills = new HashSet<>(resume.getSkills());
        Set<String> requiredSkills = new HashSet<>(position.getRequiredSkills());
        Set<String> preferredSkills = new HashSet<>(position.getPreferredSkills());

        // 计算必备技能匹配度
        double requiredScore = calculateSkillMatchScore(resumeSkills, requiredSkills) * 0.7;
        
        // 计算加分技能匹配度
        double preferredScore = calculateSkillMatchScore(resumeSkills, preferredSkills) * 0.3;

        return requiredScore + preferredScore;
    }

    private double calculateSkillMatchScore(Set<String> resumeSkills, Set<String> positionSkills) {
        if (positionSkills.isEmpty()) {
            return 100.0;
        }

        int matchCount = 0;
        for (String skill : positionSkills) {
            for (String resumeSkill : resumeSkills) {
                if (CommonUtils.calculateSimilarity(skill, resumeSkill) > 0.8) {
                    matchCount++;
                    break;
                }
            }
        }

        return (double) matchCount / positionSkills.size() * 100;
    }

    private double calculateExperienceScore(Resume resume, Position position) {
        if (resume.getWorkExperience() == null || resume.getWorkExperience().isEmpty()) {
            return 0.0;
        }

        // 解析经验要求
        int requiredYears = parseExperienceYears(position.getExperienceRequirement());
        
        // 计算总工作经验年限
        int totalYears = calculateTotalWorkYears(resume.getWorkExperience());
        
        // 计算经验匹配分数
        if (totalYears >= requiredYears) {
            return 100.0;
        } else {
            return (double) totalYears / requiredYears * 100;
        }
    }

    private int parseExperienceYears(String experienceRequirement) {
        // 简单的经验年限解析，实际应用中可能需要更复杂的逻辑
        if (experienceRequirement == null) {
            return 0;
        }
        
        if (experienceRequirement.contains("应届")) {
            return 0;
        } else if (experienceRequirement.contains("1-3年")) {
            return 2;
        } else if (experienceRequirement.contains("3-5年")) {
            return 4;
        } else if (experienceRequirement.contains("5年以上")) {
            return 5;
        }
        
        return 0;
    }

    private int calculateTotalWorkYears(List<Resume.WorkExperience> workExperience) {
        int totalYears = 0;
        for (Resume.WorkExperience exp : workExperience) {
            if (exp.getStartDate() != null && exp.getEndDate() != null) {
                long diffInMillis = exp.getEndDate().getTime() - exp.getStartDate().getTime();
                totalYears += (int) (diffInMillis / (1000L * 60 * 60 * 24 * 365));
            }
        }
        return totalYears;
    }

    private double calculateEducationScore(Resume resume, Position position) {
        if (resume.getEducation() == null || resume.getEducation().isEmpty()) {
            return 0.0;
        }

        String requiredEducation = position.getEducationRequirement();
        if (requiredEducation == null) {
            return 100.0;
        }

        // 获取最高学历
        String highestDegree = getHighestDegree(resume.getEducation());
        
        // 计算教育匹配分数
        return calculateEducationMatchScore(highestDegree, requiredEducation);
    }

    private String getHighestDegree(List<Resume.Education> education) {
        Map<String, Integer> degreeRank = new HashMap<>();
        degreeRank.put("博士", 4);
        degreeRank.put("硕士", 3);
        degreeRank.put("本科", 2);
        degreeRank.put("大专", 1);

        String highestDegree = "大专";
        int highestRank = 0;

        for (Resume.Education edu : education) {
            String degree = edu.getDegree();
            int rank = degreeRank.getOrDefault(degree, 0);
            if (rank > highestRank) {
                highestRank = rank;
                highestDegree = degree;
            }
        }

        return highestDegree;
    }

    private double calculateEducationMatchScore(String candidateDegree, String requiredDegree) {
        Map<String, Integer> degreeRank = new HashMap<>();
        degreeRank.put("博士", 4);
        degreeRank.put("硕士", 3);
        degreeRank.put("本科", 2);
        degreeRank.put("大专", 1);

        int candidateRank = degreeRank.getOrDefault(candidateDegree, 0);
        int requiredRank = degreeRank.getOrDefault(requiredDegree, 0);

        if (candidateRank >= requiredRank) {
            return 100.0;
        } else {
            return (double) candidateRank / requiredRank * 100;
        }
    }

    private double calculateMLScore(Resume resume, Position position) {
        // 使用Weka模型进行评分
        SMO model = getOrCreateModel(position.getId());
        if (model == null) {
            return 50.0; // 默认中等分数
        }

        try {
            // 准备特征向量
            double[] features = prepareFeatures(resume, position);
            
            // 创建实例
            Instance instance = new DenseInstance(1.0, features);
            
            // 预测分数
            double prediction = model.classifyInstance(instance);
            
            return prediction * 100;
        } catch (Exception e) {
            return 50.0; // 发生错误时返回默认分数
        }
    }

    private SMO getOrCreateModel(String positionId) {
        return positionModels.computeIfAbsent(positionId, k -> {
            try {
                SMO model = new SMO();
                // TODO: 加载或训练模型
                return model;
            } catch (Exception e) {
                return null;
            }
        });
    }

    private double[] prepareFeatures(Resume resume, Position position) {
        // 准备特征向量
        // TODO: 实现特征提取逻辑
        return new double[10]; // 示例返回
    }

    private double combineScores(double... scores) {
        // 使用加权平均计算最终分数
        double[] weights = {0.2, 0.3, 0.2, 0.2, 0.1}; // 权重配置
        double totalScore = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < scores.length; i++) {
            totalScore += scores[i] * weights[i];
            totalWeight += weights[i];
        }

        return totalScore / totalWeight;
    }
} 