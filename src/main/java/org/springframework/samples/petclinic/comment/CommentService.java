package org.springframework.samples.petclinic.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
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
        return commentRepository.findCommentsByGame(gameId);
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }
}