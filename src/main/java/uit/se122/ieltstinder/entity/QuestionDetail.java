package uit.se122.ieltstinder.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_question_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question_id", nullable = false, referencedColumnName = "id")
    private Question question;

    @Column(name = "question")
    private String text;

    @Column(name = "explain")
    private String explain;

    @OneToMany(mappedBy = "questionDetail", fetch = FetchType.LAZY)
    private List<Answer> answers;

    @Column(name = "point", nullable = true)
    private Integer point;

}
