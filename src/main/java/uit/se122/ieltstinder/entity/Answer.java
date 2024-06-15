package uit.se122.ieltstinder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question_detail_id", nullable = false, referencedColumnName = "id")
    private QuestionDetail questionDetail;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

}
