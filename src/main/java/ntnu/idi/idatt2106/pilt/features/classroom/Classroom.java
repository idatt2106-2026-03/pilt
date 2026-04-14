package ntnu.idi.idatt2106.pilt.features.classroom;

import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import ntnu.idi.idatt2106.pilt.features.user.model.Teacher;

/**
 * Entity representing a classroom in the system. A classroom has a unique class code, a name, and
 * is associated with multiple teachers and students.
 */
@Entity
@Data
@Table(name = "classrooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Column(nullable = false)
  public String name;

  @Column(unique = true, nullable = false)
  private String classCode;

  @ManyToMany
  @JoinTable(
      name = "classroom_teachers",
      joinColumns = @JoinColumn(name = "classroom_id"),
      inverseJoinColumns = @JoinColumn(name = "teacher_id")
  )
  private Set<Teacher> teachers = new HashSet<>();

  @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
  private List<Student> students = new ArrayList<>();

}
