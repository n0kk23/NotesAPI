package org.rzsp.notes.notes;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


}
