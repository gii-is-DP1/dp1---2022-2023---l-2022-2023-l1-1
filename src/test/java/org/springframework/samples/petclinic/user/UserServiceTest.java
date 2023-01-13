package org.springframework.samples.petclinic.user;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    private User createUser(String username, String password, boolean enabled) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setAuthorities(null);
        return user;
    }

    @Test
    public void testSaveUser() {
        User user = createUser("Test achievement", "Please, pass this test", true);
        UserService service = new UserService(userRepository);
        try {
            service.saveUser(user);
            verify(userRepository).save(user);
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }
    
}
