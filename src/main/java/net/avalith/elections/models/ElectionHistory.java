package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@ToString
@Table(name = "election_histories")
public class ElectionHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty(value = "id_election_history")
  private Long id;

  @Column(name = "id_partial_winner")
  @JsonProperty(value = "id_partial_winner")
  private Long partialWinner;

  private Long votes;

  private Double percentage;

  @Column(name = "created_at")
  @JsonProperty(value = "created_at")
  private LocalDateTime createdAt;

}
