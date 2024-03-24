package Pages.callzen.POJO;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MomentData {
    public Data data;
    @lombok.Data
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Data {
        public Moments moments;
        @lombok.Data
        @Builder
        @Getter
        @Setter
        @AllArgsConstructor
        public static class Moments {
            public int totalCount;
            public List<Edge> edges;

            @lombok.Data
            @Builder
            @Getter
            @Setter
            @AllArgsConstructor
            public static class Edge {
                public Node node;
            }
        }
    }
}