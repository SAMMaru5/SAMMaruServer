package com.sammaru5.sammaru.web.controller.comment;

import com.sammaru5.sammaru.domain.*;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.request.CommentRequest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Mock
    CommentRequest commentRequest = new CommentRequest();

    @LocalServerPort
    private int port;

    private RequestSpecification basicRequest;

    private final String adminStudentId = "admin";
    private final String adminPassword = "password";

    @BeforeEach
    public void setUp() {
        basicRequest = given()
                .baseUri("http://localhost")
                .port(port);
        // 관리자 계정 추가
        userRepository.save(new User(1L, adminStudentId, "admin", passwordEncoder.encode(adminPassword), "admin@naver.com", 0L, 0, 0, UserAuthority.ROLE_ADMIN));
    }

    @Test
    @DisplayName("관리자는 다른 사용자의 댓글을 삭제할 수 있다")
    void admin_can_delete_any_comment() {
        User user = userRepository.save(new User(null, "202002000", "user1", "password", "email", 0L, 0, 0, UserAuthority.ROLE_MEMBER));
        Board board = boardRepository.save(new Board(null, "board", ""));
        Article article = articleRepository.save(new Article(null, "title", "content", 0, 0, null, board, user, null));
        Mockito.when(commentRequest.getContent()).thenReturn("comment");
        Comment comment = commentRepository.save(Comment.createComment(commentRequest, article, user));

        String path = "/api/boards/" + board.getId() + "/articles/" + article.getId() + "/comments/" + comment.getId();
        given().spec(basicRequest).basePath(path)
                .header("Authorization", "Bearer " + getAdminAccessToken())
                .when().delete()
                .then().log().all()
                .statusCode(200);
        assertThat(commentRepository.findAll().isEmpty()).isTrue();
    }

    String getAdminAccessToken() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("studentId", adminStudentId);
        jsonAsMap.put("password", adminPassword);
        return given().spec(basicRequest).contentType(ContentType.JSON).body(jsonAsMap)
                .when().post("/auth/login")
                .getBody().jsonPath().getJsonObject("response.accessToken").toString();
    }
}