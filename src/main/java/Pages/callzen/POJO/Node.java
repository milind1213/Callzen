package Pages.callzen.POJO;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Node {
    public Object isActionable;
    public Object script;
    public String activatedBy;
    public String activationDate;
    public String activationStatus;
    public Object bulkJobStatus;
    public String clusterId;
    public String createdBy;
    public String createdDate;
    public boolean globalMoment;
    public boolean isSystemGenerated;
    public String momentDescription;
    public String momentEmotion;
    public String momentId;
    public String momentName;
    public double momentThreshold;
    public String searchType;
    public String source;
    public String status;
    public String updatedBy;
    public String updatedDate;
    public List<String> conversationType;
    public List<String> momentFilterScope;
    public Object momentFeedbackScore;
    public String feedbackStatus;
    public Object bulkJobEndStamp;
    public Object bulkJobCompletionDate;
    public int version;
    public Object instructionalFeedbackScore;
    public Object untagStatus;
    public Object isNex;
    public Object enrichedData;
    public List<Object> teamName;
    public Attributes attributes;
    public AdditionalAttributes additionalAttributes;

    // Inner classes for attributes
    @Data
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Attributes {
        public List<Object> campaignName;
        public List<Object> modeOfCalling;
        public List<Object> processName;
        public List<Object> speaker;
    }

    @Data
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class AdditionalAttributes {
        public List<Object> callMeta_dispositionData_disposeName;
        public List<Object> callMeta_dispositionData_firstDisposeName;
        public List<Object> callMeta_dispositionData_secondDisposeName;
    }
}
