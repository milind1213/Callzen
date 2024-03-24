package Pages.callzen.POJO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class Payloads {
    public static MomentData pojoPayload() {
        return MomentData.builder()
          .data(MomentData.Data.builder()
          .moments(MomentData.Data.Moments.builder()
          .totalCount(3748)
          .edges(List.of(MomentData.Data.Moments.Edge.builder()
          .node(Node.builder()
                  .isActionable(null)
                  .script(null)
                  .activatedBy("")
                  .activationDate("2024-03-16T06:14:39.752Z")
                  .activationStatus("FEEDBACK_PENDING")
                  .bulkJobStatus(null)
                  .clusterId("0")
                  .createdBy("c57cef95-d1d6-4bc2-80a1-b61c63b3fc8c")
                  .createdDate("2024-03-16T06:14:22.031Z")
                  .globalMoment(true)
                  .isSystemGenerated(false)
                  .momentDescription("c")
                  .momentEmotion("positive")
                  .momentId("0d79d04a-4f79-4807-b935-e3ba3e10c290")
                  .momentName("Speaker Test")
                  .momentThreshold(1.85)
                  .searchType("semantic")
                  .source("moment_screen")
                  .status("inactive")
                  .updatedBy("c57cef95-d1d6-4bc2-80a1-b61c63b3fc8c")
                  .updatedDate("2024-03-16T06:14:39.615Z")
                  .conversationType(List.of("call"))
                  .momentFilterScope(List.of(""))
                  .momentFeedbackScore(null)
                  .feedbackStatus("FEEDBACK_PENDING")
                  .bulkJobEndStamp(null)
                  .bulkJobCompletionDate(null)
                  .version(2)
                  .instructionalFeedbackScore(null)
                  .untagStatus(null)
                  .isNex(null)
                  .enrichedData(null)
                  .teamName(List.of())
                  .attributes(Node.Attributes.builder()
                          .campaignName(List.of())
                          .modeOfCalling(List.of())
                          .processName(List.of())
                          .speaker(List.of())
                          .build())
                  .additionalAttributes(Node.AdditionalAttributes.builder()
                          .callMeta_dispositionData_disposeName(List.of())
                          .callMeta_dispositionData_firstDisposeName(List.of())
                          .callMeta_dispositionData_secondDisposeName(List.of())
                          .build())
                  .build())
                  .build()))
                  .build())
                  .build())
                .build();
    }


}
