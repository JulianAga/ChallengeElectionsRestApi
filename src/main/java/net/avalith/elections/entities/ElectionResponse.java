package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResponse {

  private Long id;

  @JsonProperty(value = "start_date")
  private LocalDateTime startDate;

  @JsonProperty(value = "end_date")
  private LocalDateTime endDate;

  @JsonProperty(value = "candidates")
  private List<ResponseCandidate> candidates;

}
