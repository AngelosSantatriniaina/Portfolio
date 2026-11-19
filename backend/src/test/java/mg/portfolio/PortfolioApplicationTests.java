package mg.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PortfolioApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateUpdateAndDeleteServices() throws Exception {

        mockMvc.perform(post("/api/v1/admin/services")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(
                        "{\"title\":\"Service de test\",\"description\":\"Description\",\"icon\":\"wrench\",\"sortOrder\":1,\"published\":true}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Service de test"));

        mockMvc.perform(get("/api/v1/admin/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Service de test"));

        mockMvc.perform(put("/api/v1/admin/services/1")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(
                        "{\"title\":\"Service modifié\",\"description\":\"Nouvelle description\",\"icon\":\"wrench\",\"sortOrder\":2,\"published\":false}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.published").value(false));

        mockMvc.perform(delete("/api/v1/admin/services/1").with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/admin/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanManageProjectTechnologies() throws Exception {

        String firstImageUrl =
                "https://test.supabase.co/storage/v1/object/public/portfolio-images/first.jpg";

        String updatedImageUrl =
                "https://test.supabase.co/storage/v1/object/public/portfolio-images/updated.jpg";

        mockMvc.perform(post("/api/v1/admin/projects")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(
                        "{\"title\":\"Projet test\",\"description\":\"Description\",\"category\":\"Web\",\"technologies\":[\"Java\",\"React\"],\"imagePaths\":[\"/uploads/first.jpg\",\"/uploads/second.jpg\"],\"sortOrder\":1,\"featured\":false,\"published\":true}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technologies.length()").value(2))
                .andExpect(jsonPath("$.imagePaths.length()").value(2))
                .andExpect(jsonPath("$.imagePath").value(firstImageUrl))
                .andExpect(jsonPath("$.imagePaths[0]").value(firstImageUrl));

        mockMvc.perform(put("/api/v1/admin/projects/1")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(
                        "{\"title\":\"Projet modifié\",\"description\":\"Nouvelle description\",\"category\":\"Web\",\"technologies\":[\"Vue\"],\"imagePaths\":[\"/uploads/updated.jpg\"],\"sortOrder\":1,\"featured\":false,\"published\":true}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technologies[0]").value("Vue"))
                .andExpect(jsonPath("$.imagePaths.length()").value(1))
                .andExpect(jsonPath("$.imagePaths[0]").value(updatedImageUrl));

        mockMvc.perform(get("/api/v1/public/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imagePaths[0]").value(updatedImageUrl));

        mockMvc.perform(delete("/api/v1/admin/projects/1").with(csrf()))
                .andExpect(status().isOk());
    }
}