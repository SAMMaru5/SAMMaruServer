package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "select a from Article a left join fetch a.files " +
            "join fetch a.user where a.id = :id")
    Optional<Article> findOneWithFilesAndUserById(@Param("id") Long id);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.board = :board",
            countQuery = "select count(a) from Article a where a.board = :board")
    List<Article> findArticlesWithFilesAndUserByBoard(@Param("board") Board board, Pageable pageable);

    @Query(value = "select a from Article a join fetch a.user where a.board = :board",
            countQuery = "select count(a) from Article a where a.board = :board")
    List<Article> findByBoard(@Param("board") Board board, Pageable pageable);

    @Query(value = "select a from Article a join fetch a.user where a.board = :board",
            countQuery = "select count(a) from Article a where a.board = :board")
    List<Article> findByBoard(@Param("board") Board board);

    @Query(value = "select a from Article a join fetch a.files where a.id = :id")
    Optional<Article> findArticleWithFile(@Param("id") Long articleId);

    @Transactional
    @Modifying
    @Query(value = "delete from Article a where a.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    // -------------------------- 특정 게시판 게시글 검색 --------------------------------------

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.board = :board and a.user.studentId = :studentId",
            countQuery = "select count(a) from Article a where a.board = :board and a.user.studentId = :studentId")
    List<Article> findArticlesWithFilesAndUserByBoardAndWriterStudentId(@Param("board") Board board, Pageable pageable, @Param("studentId") String studentId);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.board = :board and a.user.username = :username",
            countQuery = "select count(a) from Article a where a.board = :board and a.user.username = :username")
    List<Article> findArticlesWithFilesAndUserByBoardAndWriterUsername(@Param("board") Board board, Pageable pageable, @Param("username") String username);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.board = :board and a.title like %:keyword%",
            countQuery = "select count(a) from Article a where a.board = :board and a.title like %:keyword%")
    List<Article> findArticlesWithFilesAndUserByBoardAndKeywordForArticleTitle(@Param("board") Board board, Pageable pageable, @Param("keyword") String keyword);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.board = :board and a.content like %:keyword%",
            countQuery = "select count(a) from Article a where a.board = :board and a.content like %:keyword%")
    List<Article> findArticlesWithFilesAndUserByBoardAndKeywordForArticleContent(@Param("board") Board board, Pageable pageable, @Param("keyword") String keyword);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where (a.board = :board) and (a.title like %:keyword% or a.content like %:keyword%)",
            countQuery = "select count(a) from Article a where (a.board = :board) and (a.title like %:keyword% or a.content like %:keyword%)")
    List<Article> findArticlesWithFilesAndUserByBoardAndKeywordForArticleTitleAndArticleContent(@Param("board") Board board, Pageable pageable, @Param("keyword") String keyword);

    // -------------------- 전체 게시판 게시글 검색 ------------------------

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.user.studentId = :studentId",
            countQuery = "select count(a) from Article a where a.user.studentId = :studentId")
    List<Article> findArticlesWithFilesAndUserByWriterStudentId(Pageable pageable, @Param("studentId") String studentId);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.user.username = :username",
            countQuery = "select count(a) from Article a where a.user.username = :username")
    List<Article> findArticlesWithFilesAndUserByWriterUsername(Pageable pageable, @Param("username") String username);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.title like %:keyword%",
            countQuery = "select count(a) from Article a where a.title like %:keyword%")
    List<Article> findArticlesWithFilesAndUserByKeywordForArticleTitle(Pageable pageable, @Param("keyword") String keyword);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.content like %:keyword%",
            countQuery = "select count(a) from Article a where a.content like %:keyword%")
    List<Article> findArticlesWithFilesAndUserByKeywordForArticleContent(Pageable pageable, @Param("keyword") String keyword);

    @Query(value = "select a from Article a left join fetch a.files join fetch a.user where a.title like %:keyword% or a.content like %:keyword%",
            countQuery = "select count(a) from Article a where a.title like %:keyword% or a.content like %:keyword%")
    List<Article> findArticlesWithFilesAndUserByKeywordForArticleTitleAndArticleContent(Pageable pageable, @Param("keyword") String keyword);

}
