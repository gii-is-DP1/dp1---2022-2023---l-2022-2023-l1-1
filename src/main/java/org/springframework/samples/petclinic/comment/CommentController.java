package org.springframework.samples.petclinic.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {
     // private final String  COMMENTS_LISTING_VIEW="/comments/createOrUpdateTurnoForm";
     private CommentService commentService;

     @Autowired
     public CommentController(CommentService commentService){
         this.commentService=commentService;
     }
 /*
     @Transactional(readOnly = true)
     @GetMapping("/")
     public ModelAndView showComments(){
         ModelAndView result=new ModelAndView(COMMENTS_LISTING_VIEW);
         result.addObject("turns", commentService.getComments());
         return result;
     }
     @Transactional()
     @GetMapping("/{id}/delete")
     public ModelAndView deleteTurns(@PathVariable int id){
         mensajeService.deleteCommentById(id);        
         ModelAndView result=showComments();
         result.addObject("message", "El mensaje se ha eliminado correctamente");
         return result;
     }
 */
    
}
