package uit.se122.ieltstinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uit.se122.ieltstinder.entity.enumeration.PartType;
import uit.se122.ieltstinder.entity.enumeration.QuestionType;

import java.util.List;

@Entity
@Table(name = "t_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "paragraph")
    private String paragraph;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_test_id", nullable = false, referencedColumnName = "id")
    private Test test;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionDetail> questionDetails;

}
