package com.cellulant.iprs;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.repository.ClientRepository;
import com.cellulant.iprs.repository.RequestTypeRepository;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
@Slf4j
public class IprsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprsApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner loadData(RoleRepository roleRepository,
							   ClientRepository clientRepository,
							   RequestTypeRepository requestTypeRepository,
							   UserRepository userRepository) {
		return args -> {
			log.info("============ starting inserting data ===============");
			Role role = Role.builder().roleName("ADMIN")
					.updatedBy(1L).permissions("READ,WRITE,UPDATE,DELETE")
					.description("admin").roleAlias("ADMIN").updatedBy(1L)
					.createdBy(1L).active(1).build();
			RequestType requestType = RequestType.builder()
					.requestTypeName("Passport")
					.active(1)
					.createdBy(1L)
					.updatedBy(1L)
					.build();
			Client client = Client.builder()
					.clientName("Cellulant")
					.clientDescription("cellulant")
					.active(1)
					.createdBy(1L)
					.updatedBy(1L)
					.build();
			Collection<Role> roles = new ArrayList<>();
			roles.add(role);
			User user = User.builder()
					.clientID(1L)
					.roleID(1)
					.userName("joeabala")
					.fullName("joseph abala")
					.emailAddress("joe@gmail.com")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.passwordAttempts(1)
					.idNumber("27711223")
					.msisdn("0788993322")
					.active(1)
					.roles(roles)
					.updatedBy(1L)
					.createdBy(1L)
					.canAccessUi("yes")
					.build();

			roleRepository.save(role);
			clientRepository.save(client);
			requestTypeRepository.save(requestType);
			userRepository.save(user);
			log.info("============ finished inserting data ===============");
		};
	}
}
