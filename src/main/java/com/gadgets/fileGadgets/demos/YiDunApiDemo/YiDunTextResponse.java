package com.gadgets.fileGadgets.demos.YiDunApiDemo;

import java.util.List;

public class YiDunTextResponse {
    private int code;
    private String msg;
    private Result result;

    public YiDunTextResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "YiDunTextResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public static class Result {
        //文本内容安全检测结果
        Antispam antispam;

        public Result() {
        }

        public Antispam getAntispam() {
            return antispam;
        }

        public void setAntispam(Antispam antispam) {
            this.antispam = antispam;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "antispam=" + antispam +
                    '}';
        }
    }

    public static class Antispam {
        //检测任务ID，示例值："fx6sxdcd89fvbvg4967b4787d78a"
        String taskId;
        //数据ID
        String dataId;
        //建议动作，0：通过，1：嫌疑，2：不通过
        int suggestion;
        //结果类型，1：机器结果，2：人审结果
        int resultType;
        //审核模式，0：纯机审，1：机审+部分人审，2：机审+全量人审
        int censorType;
        //是否关联检测命中，true：关联检测命中，false：原文命中
        boolean isRelatedHit;
        //命中的分类信息
        List<Labels> labels;

        public Antispam() {
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public int getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(int suggestion) {
            this.suggestion = suggestion;
        }

        public int getResultType() {
            return resultType;
        }

        public void setResultType(int resultType) {
            this.resultType = resultType;
        }

        public int getCensorType() {
            return censorType;
        }

        public void setCensorType(int censorType) {
            this.censorType = censorType;
        }

        public boolean getIsRelatedHit() {
            return isRelatedHit;
        }

        public void setIsRelatedHit(boolean relatedHit) {
            isRelatedHit = relatedHit;
        }

        public List<Labels> getLabels() {
            return labels;
        }

        public void setLabels(List<Labels> labels) {
            this.labels = labels;
        }

        @Override
        public String toString() {
            return "Antispam{" +
                    "taskId='" + taskId + '\'' +
                    ", dataId='" + dataId + '\'' +
                    ", suggestion=" + suggestion +
                    ", resultType=" + resultType +
                    ", censorType=" + censorType +
                    ", isRelatedHit=" + isRelatedHit +
                    ", labels=" + labels +
                    '}';
        }
    }

    public static class Labels {
        //命中分类，分类信息，100：色情，200：广告，260：广告法，300：暴恐，400：违禁，500：涉政，600：谩骂，700：灌水，900：其他，1100：涉价值观
        int label;
        //命中级别，示例值：1：嫌疑，2：不通过
        int level;
        //细分类信息，可能包含多个
        List<SubLabels> subLabels;

        public Labels() {
        }

        public int getLabel() {
            return label;
        }

        public void setLabel(int label) {
            this.label = label;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<SubLabels> getSubLabels() {
            return subLabels;
        }

        public void setSubLabels(List<SubLabels> subLabels) {
            this.subLabels = subLabels;
        }

        @Override
        public String toString() {
            return "Labels{" +
                    "label=" + label +
                    ", level=" + level +
                    ", subLabels=" + subLabels +
                    '}';
        }
    }

    public static class SubLabels {
        //细分类,详见https://support.dun.163.com/documents/588434200783982592?docId=444281309180616704
        int subLabel;
        //命中的详细信息
        Details details;

        public SubLabels() {
        }

        public int getSubLabel() {
            return subLabel;
        }

        public void setSubLabel(int subLabel) {
            this.subLabel = subLabel;
        }

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "SubLabels{" +
                    "subLabel=" + subLabel +
                    ", details=" + details +
                    '}';
        }
    }

    public static class Details {
        //命中的敏感词信息
        List<Keywords> keywords;
        //命中的线索信息
        List<HitInfos> hitInfos;

        public Details() {
        }

        public List<Keywords> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<Keywords> keywords) {
            this.keywords = keywords;
        }

        public List<HitInfos> getHitInfos() {
            return hitInfos;
        }

        public void setHitInfos(List<HitInfos> hitInfos) {
            this.hitInfos = hitInfos;
        }

        @Override
        public String toString() {
            return "Details{" +
                    "keywords=" + keywords +
                    ", hitInfos=" + hitInfos +
                    '}';
        }
    }

    public static class Keywords {
        //自定义敏感词内容
        String word;

        public Keywords() {
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return "Keywords{" +
                    "word='" + word + '\'' +
                    '}';
        }
    }

    private static class HitInfos {
        //线索内容
        String value;
        //线索位置
        List<Positions> positions;

        public HitInfos() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<Positions> getPositions() {
            return positions;
        }

        public void setPositions(List<Positions> positions) {
            this.positions = positions;
        }

        @Override
        public String toString() {
            return "HitInfos{" +
                    "value='" + value + '\'' +
                    ", positions=" + positions +
                    '}';
        }
    }

    private static class Positions {
        //位置类型，content：正文，title：标题
        String fieldName;
        //线索开始坐标
        int startPos;
        //线索结束坐标
        int endPos;

        public Positions() {
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public int getStartPos() {
            return startPos;
        }

        public void setStartPos(int startPos) {
            this.startPos = startPos;
        }

        public int getEndPos() {
            return endPos;
        }

        public void setEndPos(int endPos) {
            this.endPos = endPos;
        }

        @Override
        public String toString() {
            return "Positions{" +
                    "fieldName='" + fieldName + '\'' +
                    ", startPos=" + startPos +
                    ", endPos=" + endPos +
                    '}';
        }
    }
}
