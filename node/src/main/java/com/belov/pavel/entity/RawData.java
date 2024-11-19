package com.belov.pavel.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "raw_data")
@EqualsAndHashCode(exclude = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class RawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    Update event;
}
