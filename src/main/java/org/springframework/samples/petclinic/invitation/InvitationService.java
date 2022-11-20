package org.springframework.samples.petclinic.invitation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.enums.InvitationType;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitationService {

    private InvitationRepository invitationRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public InvitationService(InvitationRepository repo) {
        this.invitationRepository = repo;
    }

    @Transactional(readOnly = true)
    public Invitation getById(Integer id) {
        return invitationRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Invitation> getInvitationsByPlayer(Player recipient) {
        return invitationRepository.findInvitationsByPlayer(recipient);
    }

    @Transactional(readOnly = true)
    public List<Player> getFriends(Player recipient) {
        List<Player> res = new ArrayList<>();
        List<Invitation> invitations = invitationRepository.findInvitationsByPlayer(recipient);
        for(Invitation i:invitations) {
            if(i.getAccepted()) {
                res.add(i.getSender());
            }
        }
        return res;
    }

    @Transactional
	public void saveInvitation(Invitation i, Player sender) throws DataAccessException {
		i.setInvitationType(InvitationType.FRIENDSHIP);
        i.setAccepted(false);
        i.setSender(sender);
        invitationRepository.save(i);
	}

    @Transactional
    public void acceptInvitationById(Integer id) throws DataAccessException {
        Invitation i = getById(id);
        i.setAccepted(true);
        invitationRepository.save(i);
    }
    
}
