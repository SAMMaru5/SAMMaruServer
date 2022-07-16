package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ArticlesSearchTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    User[] users;
    Board[] boards;
    Article[][] articles;

    @BeforeEach
    public void setUp() {
        //given
        users = new User[2];
        for (int i = 0; i < users.length; i++) {
            users[i] = User.builder().studentId("1111111" + i).username("test" + i).password("1234").email("test" + i + "@gmail.com").point(0L).role(UserAuthority.ROLE_MEMBER).build();
            users[i] = userRepository.save(users[i]);
        }

        boards = new Board[3];
        String[] boardNames = new String[]{"자유게시판", "사진첩", "공지사항"};
        for (int i = 0; i < boards.length; i++) {
            boards[i] = new Board(null, boardNames[i], "description" + i);
            boards[i] = boardRepository.save(boards[i]);
        }

        articles = new Article[boards.length][4];
        String[] titles = new String[]{"title", "어쩌구 키워드 어쩌구", "title3", "키워드로 시작"};
        String[] contents = new String[]{"어쩌구 키워드", "키워드", "아무거나", "아무거나"};
        for (int i = 0; i < boards.length; i++) {
            for (int j = 0; j < 4; j++) {
                articles[i][j] = new Article(null, titles[j] + i * j, i * j + contents[j], 0, 0, boards[i], users[j % 2], null);
                articles[i][j] = articleRepository.save(articles[i][j]);
            }
        }
    }

    // ----------------- 특정 게시판 게시글 검색 테스트 ------------------------

    @Test
    @DisplayName("특정 게시판에서 작성자 학번으로 게시글 검색")
    void findArticlesWithFilesAndUserByBoardAndWriterStudentId() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByBoardAndWriterStudentId(boards[0], pageable, users[0].getStudentId());

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        assertThat(searchedArticlesIds).hasSameElementsAs(Arrays.asList(articles[0][0].getId(), articles[0][2].getId()));
    }

    @Test
    @DisplayName("특정 게시판에서 작성자 이름으로 게시글 검색")
    void findArticlesWithFilesAndUserByBoardAndWriterUsername() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByBoardAndWriterUsername(boards[1], pageable, users[1].getUsername());

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        assertThat(searchedArticlesIds).hasSameElementsAs(Arrays.asList(articles[1][1].getId(), articles[1][3].getId()));
    }

    @Test
    @DisplayName("특정 게시판에서 게시글 제목으로 게시글 검색")
    void findArticlesWithFilesAndUserByBoardAndKeywordForArticleTitle() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByBoardAndKeywordForArticleTitle(boards[2], pageable, "키워드");

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        assertThat(searchedArticlesIds).hasSameElementsAs(Arrays.asList(articles[2][1].getId(), articles[2][3].getId()));
    }

    @Test
    @DisplayName("특정 게시판에서 게시글 내용으로 게시글 검색")
    void findArticlesWithFilesAndUserByBoardAndKeywordForArticleContent() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByBoardAndKeywordForArticleContent(boards[0], pageable, "키워드");

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        assertThat(searchedArticlesIds).hasSameElementsAs(Arrays.asList(articles[0][0].getId(), articles[0][1].getId()));
    }

    @Test
    @DisplayName("특정 게시판에서 게시글 제목+내용으로 게시글 검색")
    void findArticlesWithFilesAndUserByBoardAndKeywordForArticleTitleAndArticleContent() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByBoardAndKeywordForArticleTitleAndArticleContent(boards[1], pageable, "키워드");

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        assertThat(searchedArticlesIds).hasSameElementsAs(Arrays.asList(articles[1][0].getId(), articles[1][1].getId(), articles[1][3].getId()));
    }

    // ------------------------ 전체 게시판 게시글 검색 테스트 ----------------------------

    @Test
    @DisplayName("전체 게시판에서 작성자 학번으로 게시글 검색")
    void findArticlesWithFilesAndUserByWriterStudentId() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByWriterStudentId(pageable, users[0].getStudentId());

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        List<Long> expectedElements = new ArrayList<>();
        for (int i = 0; i < boards.length; i++) {
            expectedElements.add(articles[i][0].getId());
            expectedElements.add(articles[i][2].getId());
        }
        assertThat(searchedArticlesIds).hasSameElementsAs(expectedElements);
    }

    @Test
    @DisplayName("전체 게시판에서 작성자 이름으로 게시글 검색")
    void findArticlesWithFilesAndUserByWriterUsername() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByWriterUsername(pageable, users[1].getUsername());

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        List<Long> expectedElements = new ArrayList<>();
        for (int i = 0; i < boards.length; i++) {
            expectedElements.add(articles[i][1].getId());
            expectedElements.add(articles[i][3].getId());
        }
        assertThat(searchedArticlesIds).hasSameElementsAs(expectedElements);
    }

    @Test
    @DisplayName("전체 게시판에서 게시글 제목으로 게시글 검색")
    void findArticlesWithFilesAndUserByKeywordForArticleTitle() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByKeywordForArticleTitle(pageable, "키워드");

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        List<Long> expectedElements = new ArrayList<>();
        for (int i = 0; i < boards.length; i++) {
            expectedElements.add(articles[i][1].getId());
            expectedElements.add(articles[i][3].getId());
        }
        assertThat(searchedArticlesIds).hasSameElementsAs(expectedElements);
    }

    @Test
    @DisplayName("전체 게시판에서 게시글 내용으로 게시글 검색")
    void findArticlesWithFilesAndUserByKeywordForArticleContent() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByKeywordForArticleContent(pageable, "키워드");

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        List<Long> expectedElements = new ArrayList<>();
        for (int i = 0; i < boards.length; i++) {
            expectedElements.add(articles[i][0].getId());
            expectedElements.add(articles[i][1].getId());
        }
        assertThat(searchedArticlesIds).hasSameElementsAs(expectedElements);
    }

    @Test
    @DisplayName("전체 게시판에서 게시글 제목+내용으로 게시글 검색")
    void findArticlesWithFilesAndUserByKeywordForArticleTitleAndArticleContent() {
        //when
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        List<Article> searchedArticles = articleRepository.findArticlesWithFilesAndUserByKeywordForArticleTitleAndArticleContent(pageable, "키워드");

        //then
        List<Long> searchedArticlesIds = searchedArticles.stream().map(Article::getId).collect(Collectors.toList());
        List<Long> expectedElements = new ArrayList<>();
        for (int i = 0; i < boards.length; i++) {
            expectedElements.add(articles[i][0].getId());
            expectedElements.add(articles[i][1].getId());
            expectedElements.add(articles[i][3].getId());
        }
        assertThat(searchedArticlesIds).hasSameElementsAs(expectedElements);
    }
}