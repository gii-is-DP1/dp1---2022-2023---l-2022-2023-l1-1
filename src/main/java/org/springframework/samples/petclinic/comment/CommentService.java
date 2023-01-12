package org.springframework.samples.petclinic.comment;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }

    List<Comment> getComments(){
        return commentRepository.findAll();
    }
    
    public Comment getCommentById(Integer id){
        return commentRepository.findById(id).get();
    }
    
    public List<Comment> getCommentsByGame(Integer gameId){
        List<Comment> list = commentRepository.findCommentsByGame(gameId);
        list.sort(Comparator.comparing(Comment::getDate));
        Collections.reverse(list);
        return list;
    }

    public void saveComment(Comment comment, PlayerInfo playerInfo){
        comment.setDate(Date.from(Instant.now()));
        comment.setPlayerInfo(playerInfo);
        commentRepository.save(comment);
    }
}