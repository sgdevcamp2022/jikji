package com.jikji.contentcommand.domain;

import com.jikji.contentcommand.domain.converter.ImageConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "contents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 3000)
    private String text;

    @Convert(converter = ImageConverter.class)
    @Column(name = "image_url")
    private List<ImageUrl> imageUrl;
    private int likes;

    private boolean visibleLikes;

    private boolean visibleComments;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "modified_at")
    private String modifiedAt;

    @PrePersist
    public void prePersist() {
        likes = 0;
        createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        modifiedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}