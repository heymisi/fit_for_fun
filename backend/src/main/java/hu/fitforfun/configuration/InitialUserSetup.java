package hu.fitforfun.configuration;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.user.Authority;
import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.user.Role;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.AuthorityRepository;
import hu.fitforfun.repositories.InstructorRepository;
import hu.fitforfun.repositories.RoleRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class InitialUserSetup {

    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    InstructorService instructorService;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Authority readAuthority = createAuthority("READ_AUTHORITY");
        Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
        Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

        Role roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        Role roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) return;
        User adminUser = new User();
        adminUser.setFirstName("admin");
        adminUser.setLastName("admin");
        adminUser.setEmail("heymisi99@gmail.com");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setPassword(bCryptPasswordEncoder.encode("pass"));
        adminUser.setRoles(Arrays.asList(roleAdmin));
        userRepository.save(adminUser);
        Instructor instructor = new Instructor();
        instructor.setUser(adminUser);
        instructorRepository.save(instructor);
        createInstructor();

    }

    @Transactional
    private Authority createAuthority(String name) {
        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private Role createRole(String name, List<Authority> authorities) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private Instructor createInstructor() {
        User user = new User();
        user.setEmail("misi@gmail.com");
        user.setFirstName("instructor");
        user.setLastName("instructor");
        user.setPassword("pass");
        Instructor instructor = null;
        try {
            instructor = instructorService.createInstructor(user);
        } catch (FitforfunException e) {
            System.err.println(e.getErrorCode());
        }
        return instructor;
    }
}