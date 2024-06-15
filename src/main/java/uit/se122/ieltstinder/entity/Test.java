package uit.se122.ieltstinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uit.se122.ieltstinder.entity.enumeration.TestLevel;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "t_tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "difficulty_level")
    @Enumerated(EnumType.STRING)
    private TestLevel difficultyLevel;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    private List<Question> questions;

    @Column(name = "created_at")
    private Instant createdAt;

}
