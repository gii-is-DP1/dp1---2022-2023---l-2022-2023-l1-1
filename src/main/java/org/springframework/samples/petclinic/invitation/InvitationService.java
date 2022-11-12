package org.springframework.samples.petclinic.invitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitationService {

    private InvitationRepository repo;

    @Autowired
    public InvitationService(InvitationRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Invitation> getInvitationsByUser(User recipient) {
        return repo.findInvitationsByUser(recipient);
    }
    
}
