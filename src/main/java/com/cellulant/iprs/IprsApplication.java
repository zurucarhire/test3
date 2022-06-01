package com.cellulant.iprs;

import com.cellulant.iprs.entity.*;
import com.cellulant.iprs.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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
							   ExpiryPeriodRepository expiryPeriodRepository,
							   ProcedureRepository procedureRepository,
							   QuestionRepository questionRepository,
							   ExperienceRepository experienceRepository,
							   ExperienceCommentRepository experienceCommentRepository,
							   ProductRepository productRepository,
							   ProductCategoryRepository productCategoryRepository) {
		return args -> {
			log.info("============ starting inserting data ===============");
			Role adminRole = Role.builder()
					.roleID(1L)
					.roleName("ROLE_ADMIN")
					.roleAlias("ADMIN")
					.build();

			Role editorRole = Role.builder()
					.roleID(2L)
					.roleName("ROLE_EDITOR")
					.roleAlias("EDITOR")
					.build();

			Role creatorRole = Role.builder()
					.roleID(3L)
					.roleName("ROLE_CREATOR")
					.roleAlias("CREATOR")
					.build();

			Role userRole = Role.builder()
					.roleID(4L)
					.roleName("ROLE_USER")
					.roleAlias("USER").build();


			Collection<Role> roles = new ArrayList<>();
			roles.add(adminRole);

			Collection<Role> roles2 = new ArrayList<>();
			roles2.add(creatorRole);

			Collection<Role> roles3 = new ArrayList<>();
			roles3.add(editorRole);

			Collection<Role> roles4 = new ArrayList<>();
			roles4.add(userRole);

			User user = User.builder()
					.roleID(1)
					.fullName("Ken Otieno")
					.businessName("Kennedy Enterprices")
					.shopLocation("CBD")
					.phone("254717722334")
					.profileImage("test.png")
					.emailAddress("kenotieno@gmail.com")
					.phone("254717729123")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.roles(roles)
					.category("merchant")
					.status(1)
					.build();

			User user2 = User.builder()
					.roleID(2)
					.fullName("mary njoroge")
					.emailAddress("marynjoroge@gmail.com")
					.businessName("nil")
					.shopLocation("nil")
					.phone("nil")
					.profileImage("test.png")
					.password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
					.roles(roles2)
					.category("customer")
					.status(1)
					.phone("nil")
					.build();

			JSONArray jsonArray = new JSONArray();
			jsonArray.put("Toronto");
			jsonArray.put("New York");

			Procedure procedure = Procedure.builder()
					.procedureName("Face Lift")
					.description("In general, a face-lift involves elevating the skin and tightening the underlying tissues and muscles. Fat in the face and neck may be sculpted, removed or redistributed. Facial skin is then re-draped over the newly repositioned contours of the face, excess skin is removed, and the wound is stitched or taped closed. Sometimes the procedure is done with sedation and local anesthesia, which numbs only part of your body. In other cases, general anesthesia — which renders you unconscious — is recommended.")
					.category("Invasive")
					.subCategory("Skin")
					.type("Facial")
					.subType("Skin")
					.photo("team-1.jpg")
					.cost(2000D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Toronto").put("New York").toString())
					.build();

			Procedure procedure2 = Procedure.builder()
					.procedureName("Nose Reshaping")
					.description("Rhinoplasty is the medical name for surgical procedures that some people call a \"nose job,\" \"nose reshaping\" or \"nasal surgery.\" It involves the surgical reconstruction and shaping of the bone and cartilage to enhance the appearance or function of the nose. Rhinoplasty can also help reshape the interior of the nose so a person can breathe more easily. It is important to consult with an expert facial plastic surgeon who specializes in rhinoplasty. ")
					.category("Invasive")
					.subCategory("Nose")
					.type("Nasal")
					.subType("Nose")
					.photo("team-2.jpg")
					.cost(4000D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Miami").put("Tenessee").toString())
					.build();

			Procedure procedure3 = Procedure.builder()
					.procedureName("Nasal Surgery")
					.description("Sinus surgery is most commonly used to treat chronic sinusitis (inflammation of the nose and sinuses), but may be needed for other sinus problems. Surgery involves enlarging the openings between the sinuses and the inside of the nose so air can get in and drainage can get out. The most common nasal procedures include turbinate reduction and correcting any septal deviations (septoplasty). Sometimes correction of external nasal deformities (rhinosseptoplasty) or sinus surgery will be necessary and it may be performed in combination with one of our colleagues from the rhinology or facial plastic surgery team. ")
					.category("Non Invasive")
					.subCategory("Nose")
					.type("Nasal")
					.subType("Nose")
					.photo("team-3.jpg")
					.cost(1000D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Miami").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure4 = Procedure.builder()
					.procedureName("Eyelid Lift")
					.description("Blepharoplasty (BLEF-uh-roe-plas-tee) is a type of surgery that repairs droopy eyelids and may involve removing excess skin, muscle and fat. As you age, your eyelids stretch, and the muscles supporting them weaken. As a result, excess fat may gather above and below your eyelids, causing sagging eyebrows, droopy upper lids and bags under your eyes.")
					.category("Non Invasive")
					.subCategory("Eye")
					.type("Eye")
					.subType("Eye")
					.photo("team-4.jpg")
					.cost(1400D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Philadelphia").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure5 = Procedure.builder()
					.procedureName("Ear Reshaping")
					.description("You might choose to have otoplasty if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure6 = Procedure.builder()
					.procedureName("Consultative Cardiology")
					.description("You might choose to have Consultative Cardiology if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Miami").put("Kansas").put("New York").toString())
					.build();

			Procedure procedure7 = Procedure.builder()
					.procedureName("Nuclear Imaging")
					.description("You might choose to have Nuclear Imaging if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure8 = Procedure.builder()
					.procedureName("Stress Testing")
					.description("You might choose to have Stress Testing if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure9 = Procedure.builder()
					.procedureName("Echocardiography")
					.description("You might choose to have Echocardiography if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure10 = Procedure.builder()
					.procedureName("Hair Transplant")
					.description("You might choose to have Hair Transplant if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure11 = Procedure.builder()
					.procedureName("Neck Lift")
					.description("You might choose to have Neck Lift if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure12 = Procedure.builder()
					.procedureName("Gynecomastia")
					.description("You might choose to have Gynecomastia if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONArray().put("Kansas").put("Tenessee").put("New York").toString())
					.build();

			Procedure procedure13 = Procedure.builder()
					.procedureName("Eyelid rejuvenation")
					.description("You might choose to have Eyelid rejuvenation if you're bothered by how far your ears stick out from your head. You might also consider otoplasty if your ear or ears are misshapen due to an injury or birth defect. Otoplasty can be done at any age after the ears have reached their full size — usually after age 5 — through adulthood.")
					.category("Non Invasive")
					.subCategory("Ear")
					.type("Ear")
					.subType("Ear")
					.photo("team-1.jpg")
					.cost(1450D)
					.photo("volvo.jpeg")
					.city(new JSONObject().put("city",new JSONArray().put("Kansas").put("Miami").put("New York")).toString())
					.build();

			Question question = Question.builder()
					.procedureID(1L)
					.category("Pablo Fucasso")
					.title("Face lift cost")
					.description("I would like to know the cost of face lift. Is there a payment plan and is there a discount?")
					.thumbnail("test.png")
					.build();

			Question question2= Question.builder()
					.procedureID(2L)
					.category("Anonymous")
					.title("Nasal surgery healing process")
					.description("What is the recovery process for nasal surgery treatment? When will I go back to my normal routine?")
					.thumbnail("test.png")
					.build();

			Experience experience= Experience.builder()
					.procedureID(1L)
					.category("Miss Pekele")
					.title("Miss Pekele2")
					.completed("Yes")
					.cost(200D)
					.thumbnail("test.png")
					.description("If you are considering a facelift procedure, it's important to know not just about the surgical procedure but the protocol following surgery as well. Although it may take a few months to begin seeing final results of a facelift, the recovery process takes much less time.")
					.build();

			Experience experience2= Experience.builder()
					.procedureID(2L)
					.category("Jack Karma")
					.title("Jack Karma2")
					.completed("Yes")
					.cost(200D)
					.thumbnail("test.png")
					.description("Like many others, one thing about my body that always brought about feelings of insecurity was my nose. I could tell you lots of stories about the kid in elementary school who nicknamed me “pelican, or the way my grandma so lovingly yet annoyingly referred to the shape of my nose as “Roman.”")
					.build();

			ExperienceComment experienceComment= ExperienceComment.builder()
					.experienceID(1L)
					.name("oyaa oyaa")
					.description("oyaa oyaa of insecurity was my nose. I could tell you lots of stories about the kid in elementary school who nicknamed me “pelican, or the way my grandma so lovingly yet annoyingly referred to the shape of my nose as “Roman.”")
					.build();

			ExperienceComment experienceComment2= ExperienceComment.builder()
					.experienceID(1L)
					.name("msee msee")
					.description("msee msee of insecurity was my nose. I could tell you lots of stories about the kid in elementary school who nicknamed me “pelican, or the way my grandma so lovingly yet annoyingly referred to the shape of my nose as “Roman.”")
					.build();

			Product product = Product.builder()
					.userID(1L)
					.name("Product 1")
					.category("Beauty Products")
					.subcategory("Skin & Body")
					.description("Product 1 Lorem Ipsum")
					.price(1000)
					.overallprice(890)
					.discount(10)
					.sale(100)
					.count(4)
					.thumbnail("hello.png")
					.status(1)
					.build();

			Product product2 = Product.builder()
					.userID(1L)
					.name("Product 2")
					.category("Beauty Products")
					.subcategory("Skin & Body")
					.description("Product 2 Lorem Ipsum")
					.price(500)
					.overallprice(500)
					.discount(0)
					.sale(0)
					.count(6)
					.thumbnail("hello.png")
					.status(1)
					.build();

			Product product22 = Product.builder()
					.userID(1L)
					.name("Product 22")
					.category("Beauty Products")
					.subcategory("Skin & Body")
					.description("Product 22 Lorem Ipsum")
					.price(1500)
					.overallprice(1300)
					.discount(0)
					.sale(200)
					.count(6)
					.thumbnail("hello.png")
					.status(1)
					.build();
			Product product3 = Product.builder()
					.userID(1L)
					.name("Product 3")
					.category("Beauty Products")
					.subcategory("Facial Treatment")
					.description("Product 3 Lorem Ipsum")
					.price(900)
					.overallprice(790)
					.discount(10)
					.sale(10)
					.count(4)
					.thumbnail("hello.png")
					.status(1)
					.build();

			Product product4 = Product.builder()
					.userID(1L)
					.name("Product 4")
					.category("Beauty Products")
					.subcategory("Scars & Stretch Marks")
					.description("Product 4 Lorem Ipsum")
					.price(200)
					.overallprice(90)
					.discount(10)
					.sale(10)
					.count(5)
					.thumbnail("hello.png")
					.status(1)
					.build();

			Product product5 = Product.builder()
					.userID(1L)
					.name("Product 5")
					.category("Beauty Products")
					.subcategory("Hair Treatment")
					.description("Product 5 Lorem Ipsum")
					.price(1200)
					.overallprice(1090)
					.discount(10)
					.sale(10)
					.count(2)
					.thumbnail("hello.png")
					.status(1)
					.build();

			Product product6 = Product.builder()
					.userID(1L)
					.name("Product 6")
					.category("Beauty Products")
					.subcategory("Hair Treatment")
					.description("Product 6 Lorem Ipsum")
					.price(1500)
					.overallprice(1500)
					.discount(0)
					.sale(0)
					.count(12)
					.thumbnail("hello.png")
					.status(1)
					.build();

			ProductCategory productCategory = ProductCategory.builder()
					.name("Skin & Body")
					.description("Skin & Body Lorem Ipsum")
					.thumbnail("skinbody.png")
					.build();
			ProductCategory productCategory2 = ProductCategory.builder()
					.name("Facial Treatment ")
					.description("Facial Treatment  Lorem Ipsum")
					.thumbnail("facialtreatment .png")
					.build();
			ProductCategory productCategory3 = ProductCategory.builder()
					.name("Scars & Stretch marks")
					.description("Scars & Stretch marks Lorem Ipsum")
					.thumbnail("scarsstretchmarks.png")
					.build();
			ProductCategory productCategory4 = ProductCategory.builder()
					.name("Hair Treatment")
					.description("Hair Treatment Lorem Ipsum")
					.thumbnail("hairtreatment.png")
					.build();
			roleRepository.saveAll(Arrays.asList(adminRole, editorRole, creatorRole, userRole));
			userRepository.saveAll(Arrays.asList(user, user2));
			procedureRepository.saveAll(Arrays.asList(procedure, procedure2, procedure3, procedure4, procedure5,
					procedure6, procedure7, procedure8, procedure9, procedure10, procedure11, procedure12, procedure13));
			questionRepository.saveAll(Arrays.asList(question, question2));
			experienceRepository.saveAll(Arrays.asList(experience, experience2));
			experienceCommentRepository.saveAll(Arrays.asList(experienceComment, experienceComment2));
			productRepository.saveAll(Arrays.asList(product, product2, product22, product3, product4, product5, product6));
			productCategoryRepository.saveAll(Arrays.asList(productCategory, productCategory2, productCategory3, productCategory4));
			log.info("============ finished inserting data ===============");
		};
	}
}
