package com.skyeng.SEPost.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name="post_item_id")
    PostItem postItem;
    @OneToOne
    @JoinColumn(name="post_office_id")
    PostOffice postOffice;
    @Enumerated(EnumType.STRING)
    Status status;
    @Column(name = "happen_at")
    LocalDateTime happenAt;

    public enum Status {
        REGISTERED, ARRIVED, DEPARTED, RECEIVED
    }
}
