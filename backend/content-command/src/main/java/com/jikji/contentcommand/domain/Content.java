package com.jikji.contentcommand.domain;

import com.jikji.contentcommand.domain.converter.ImageConverter;
import com.jikji.contentcommand.dto.request.ContentUpdateRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
@Builder
@AllArgsConstructor
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

    private Boolean visibleLikes;

    private Boolean visibleComments;

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

    public void update(final ContentUpdateRequest content) {
        updateText(content.getText());
        updateImageUrl(content.getImageUrl());
        updateVisibleComments(content.getVisibleComments());
        updateVisibleLikes(content.getVisibleLikes());
    }

    private void updateText(final String text) {
        if (text != null) {
            this.text = text;
        }
    }

    private void updateImageUrl(final List<ImageUrl> imageUrl) {
        if (imageUrl.size() > 0) {
            this.imageUrl = imageUrl;
        }
    }

    private void updateVisibleLikes(final Boolean visibleLikes) {
        this.visibleLikes = visibleLikes;
    }

    private void updateVisibleComments(final Boolean visibleComments) {
        this.visibleComments = visibleComments;
    }

    public void changeVisibleLikes(final Boolean visibleLikes) {
        if (visibleLikes != null) {
            this.visibleLikes = !this.visibleLikes;
        }
    }

    public void changeVisibleComments(final Boolean visibleComments) {
        if (visibleComments != null) {
            this.visibleComments = !this.visibleComments;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        final Content content = (Content) o;
        return Objects.equals(id, content.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}