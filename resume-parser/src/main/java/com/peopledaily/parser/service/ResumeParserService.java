package com.peopledaily.parser.service;

import com.peopledaily.common.model.Resume;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import com.hankcs.hanlp.HanLP;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParserService {
    private final StanfordCoreNLP pipeline;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("1[3-9]\\d{9}");

    public ResumeParserService() {
        // 初始化NLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("ner.useSUTime", "false");
        pipeline = new StanfordCoreNLP(props);
    }

    public Resume parseResume(MultipartFile file) throws IOException {
        String content = extractText(file);
        return parseContent(content);
    }

    private String extractText(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toLowerCase();
        if (fileName.endsWith(".pdf")) {
            return extractFromPDF(file.getInputStream());
        } else if (fileName.endsWith(".docx")) {
            return extractFromDOCX(file.getInputStream());
        } else {
            throw new IllegalArgumentException("不支持的文件格式");
        }
    }

    private String extractFromPDF(InputStream inputStream) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractFromDOCX(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }
    }

    private Resume parseContent(String content) {
        Resume resume = new Resume();
        
        // 使用Stanford NLP进行命名实体识别
        CoreDocument document = new CoreDocument(content);
        pipeline.annotate(document);

        // 提取基本信息
        resume.setName(extractName(document));
        resume.setPhone(extractPhone(content));
        resume.setEmail(extractEmail(content));

        // 使用HanLP进行中文分词和关键词提取
        List<String> keywords = HanLP.extractKeyword(content, 20);
        
        // 提取教育背景
        resume.setEducation(extractEducation(document, content));
        
        // 提取工作经验
        resume.setWorkExperience(extractWorkExperience(document, content));
        
        // 提取项目经验
        resume.setProjectExperience(extractProjectExperience(document, content));
        
        // 提取技能标签
        resume.setSkills(extractSkills(content, keywords));
        
        // 设置初始状态
        resume.setStatus("NEW");
        resume.setCreateTime(new Date());
        resume.setUpdateTime(new Date());

        return resume;
    }

    private String extractName(CoreDocument document) {
        // 查找PERSON类型的命名实体
        for (CoreEntityMention em : document.entityMentions()) {
            if (em.entityType().equals("PERSON")) {
                return em.text();
            }
        }
        return null;
    }

    private String extractPhone(String content) {
        Matcher matcher = PHONE_PATTERN.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    private String extractEmail(String content) {
        Matcher matcher = EMAIL_PATTERN.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    private List<Resume.Education> extractEducation(CoreDocument document, String content) {
        List<Resume.Education> educationList = new ArrayList<>();
        // 使用正则表达式或关键词匹配提取教育信息
        // 这里需要根据实际简历格式进行定制
        // TODO: 实现教育信息提取逻辑
        return educationList;
    }

    private List<Resume.WorkExperience> extractWorkExperience(CoreDocument document, String content) {
        List<Resume.WorkExperience> workExperienceList = new ArrayList<>();
        // 使用正则表达式或关键词匹配提取工作经验
        // 这里需要根据实际简历格式进行定制
        // TODO: 实现工作经验提取逻辑
        return workExperienceList;
    }

    private List<Resume.ProjectExperience> extractProjectExperience(CoreDocument document, String content) {
        List<Resume.ProjectExperience> projectExperienceList = new ArrayList<>();
        // 使用正则表达式或关键词匹配提取项目经验
        // 这里需要根据实际简历格式进行定制
        // TODO: 实现项目经验提取逻辑
        return projectExperienceList;
    }

    private List<String> extractSkills(String content, List<String> keywords) {
        Set<String> skills = new HashSet<>();
        // 结合预定义的技能词库和关键词提取结果
        // TODO: 实现技能提取逻辑
        return new ArrayList<>(skills);
    }
} 