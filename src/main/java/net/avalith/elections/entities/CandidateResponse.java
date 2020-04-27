package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {

  private Long id;

  @JsonProperty(value = "first_name")
  private String firstName;

  @JsonProperty(value = "last_name")
  private String lastName;
}
