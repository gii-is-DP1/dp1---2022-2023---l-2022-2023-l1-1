package org.springframework.samples.petclinic.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    public Optional<Comment> getCommentById(Integer id){
        return commentRepository.findById(id);
    }
    
    public List<Comment> getCommentsByLobby(PlayerInfo playerInfo){
        return commentRepository.findCommentsByLobby(playerInfo);
    }

    public void deleteCommentById(int id){
    	commentRepository.deleteById(id);
    }

    public Comment save(Comment comment, Player player){
    	comment.setSender(player.getUser().getUsername());
        comment.setMessage(null);
        return commentRepository.save(comment);
    }
}
