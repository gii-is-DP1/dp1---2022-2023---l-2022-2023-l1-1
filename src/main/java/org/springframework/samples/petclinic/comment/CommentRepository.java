package org.springframework.samples.petclinic.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>{

    List<Comment> findAll();
    
    @Query("SELECT DISTINCT c FROM Comment c WHERE c.id LIKE :id")
    public Optional<Comment> findById(@Param("id") Integer id);

    @Query("SELECT c FROM Comment c WHERE c.playerInfo =?1")
    List<Comment> findCommentsByLobby(@Param("playerInfo") PlayerInfo playerInfo);
    
}
