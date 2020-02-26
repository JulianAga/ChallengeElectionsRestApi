package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalith.elections.models.Candidate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResultResponse {

  @JsonProperty(value = "id_election")
  private Long id;

  private List<CandidateWithVotesResponse> candidates;

  @JsonProperty(value = "total_votes")
  private Long totalVotes;

}
