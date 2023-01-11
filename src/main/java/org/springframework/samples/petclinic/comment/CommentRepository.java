package org.springframework.samples.petclinic.comment;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>{

    List<Comment> findAll();

    @Query("SELECT c FROM Comment c WHERE c.playerInfo.gameId=:gameId")
    public List<Comment> findCommentsByGame(@Param("gameId") Integer gameId);

}
