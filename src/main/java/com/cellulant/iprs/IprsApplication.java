package com.cellulant.iprs;

import com.cellulant.iprs.entity.*;
import com.cellulant.iprs.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
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
							   UserRepository userRepository,
							   ExpiryPeriodRepository expiryPeriodRepository) {
		return args -> {
			log.info("============ starting inserting data ===============");
			Role adminRole = Role.builder()
					.roleID(1L)
					.roleName("ROLE_ADMIN")
					.updatedBy(1L).permissions("READ, WRITE, UPDATE, DELETE")
					.description("Admin Role").roleAlias("ADMIN").updatedBy(1L)
					.createdBy(1L).active(1).build();

			Role editorRole = Role.builder()
					.roleID(2L)
					.roleName("ROLE_EDITOR")
					.updatedBy(1L).permissions("READ, WRITE, UPDATE")
					.description("Editor Role").roleAlias("EDITOR").updatedBy(1L)
					.createdBy(1L).active(1).build();

			Role creatorRole = Role.builder()
					.roleID(3L)
					.roleName("ROLE_CREATOR")
					.updatedBy(1L).permissions("READ, WRITE")
					.description("Creator Role").roleAlias("CREATOR").updatedBy(1L)
					.createdBy(1L).active(1).build();

			Role userRole = Role.builder()
					.roleID(4L)
					.roleName("ROLE_USER")
					.updatedBy(1L).permissions("READ")
					.description("User Role").roleAlias("USER").updatedBy(1L)
					.createdBy(1L).active(1).build();

			RequestType requestType = RequestType.builder()
					.requestTypeName("Passport")
					.active(1)
					.createdBy(1L)
					.updatedBy(1L)
					.build();
			RequestType requestType2 = RequestType.builder()
					.requestTypeName("ID Number")
					.active(1)
					.createdBy(1L)
					.updatedBy(1L)
					.build();

			RequestType requestType3 = RequestType.builder()
					.requestTypeName("Alien ID")
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
			roles.add(adminRole);

			Collection<Role> roles2 = new ArrayList<>();
			roles2.add(creatorRole);

			Collection<Role> roles3 = new ArrayList<>();
			roles3.add(editorRole);

			Collection<Role> roles4 = new ArrayList<>();
			roles4.add(userRole);

			User user = User.builder()
					.clientID(1L)
					.roleID(1)
					.userName("kenotieno")
					.fullName("Ken Otieno")
					.emailAddress("kenotieno@gmail.com")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.passwordAttempts(1)
					.idNumber("255112663")
					.msisdn("0788993322")
					.active(1)
					.roles(roles)
					.updatedBy(1L)
					.createdBy(1L)
					.canAccessUi("yes")
					.build();

			User user2 = User.builder()
					.clientID(1L)
					.roleID(2)
					.userName("marynjoroge")
					.fullName("mary njoroge")
					.emailAddress("marynjoroge@gmail.com")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.passwordAttempts(1)
					.idNumber("55511663")
					.msisdn("0722993355")
					.active(1)
					.roles(roles2)
					.updatedBy(1L)
					.createdBy(1L)
					.canAccessUi("yes")
					.build();

			User user3 = User.builder()
					.clientID(1L)
					.roleID(3)
					.userName("katekamau")
					.fullName("kate kamau")
					.emailAddress("katekamau@gmail.com")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.passwordAttempts(1)
					.idNumber("88911699")
					.msisdn("0788993995")
					.active(1)
					.roles(roles3)
					.updatedBy(1L)
					.createdBy(1L)
					.canAccessUi("yes")
					.build();

			User user4 = User.builder()
					.clientID(1L)
					.roleID(4)
					.userName("paulomondi")
					.fullName("paul omondi")
					.emailAddress("paulomondi@gmail.com")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.passwordAttempts(1)
					.idNumber("99911699")
					.msisdn("0799993995")
					.active(1)
					.roles(roles4)
					.updatedBy(1L)
					.createdBy(1L)
					.canAccessUi("yes")
					.build();
			ExpiryPeriod expiryPeriod = ExpiryPeriod.builder()
					.expiryPeriod(10)
					.active(1)
					.createdBy(1L)
					.updatedBy(1L).build();

			roleRepository.saveAll(Arrays.asList(adminRole, editorRole, creatorRole, userRole));
			clientRepository.save(client);
			requestTypeRepository.saveAll(Arrays.asList(requestType, requestType2));
			userRepository.saveAll(Arrays.asList(user, user2, user3, user4));
			expiryPeriodRepository.save(expiryPeriod);
			log.info("============ finished inserting data ===============");
		};
	}
}
