package org.rzsp.notes.notes;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Сущность заметки.
 */
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString
@Entity
@Table(name = "notes")
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

}
