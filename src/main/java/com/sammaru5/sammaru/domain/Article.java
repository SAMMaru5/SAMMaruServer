package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseTime {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer viewCnt;
    private Integer likeCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size=100)
    private List<File> files = new ArrayList<>();

    public static Article createArticle(ArticleRequest articleRequest, Board board, User user) {
        return Article.builder()
                .articleRequest(articleRequest)
                .board(board)
                .user(user)
                .build();
    }

    @Builder
    private Article(ArticleRequest articleRequest, Board board, User user) {
        this.title = articleRequest.getTitle();
        this.content = articleRequest.getContent();
        this.board = board;
        this.user = user;
        this.viewCnt = 0;
        this.likeCnt = 0;
    }

    //== 연관관계 메서드 ==//
    public void addFile(File file) {
        files.add(file);
        file.belongToArticle(this);
    }

    public void removeFile() {
        files.clear();
    }

    //== 비즈니스 메서드 ==//
    public void plusViewCnt() {
        this.viewCnt++;
    }
    public void minusViewCnt() {
        this.viewCnt--;
    }
    public void plusLikeCnt() {
        this.likeCnt++;
    }
    public void minusLikeCnt() {
        this.likeCnt--;
    }

    public void modifyArticle(ArticleRequest articleRequest) {
        this.title = articleRequest.getTitle();
        this.content = articleRequest.getContent();
    }

}
