import com.crawler.CrawlerApplication;
import com.crawler.controller.ArticleController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
@WebAppConfiguration
public class ArticleControllerTest {

    @Autowired
    private ArticleController articleController;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @After
    public void tearDown() throws Exception {
        mvc = null;
    }

    @Test
    public void testListAllCrawlerContents() throws Exception {
        RequestBuilder request = get("/article/listAllCrawlerContents");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msgCode").value("200"));
    }

    @Test
    public void testSaveTemplateConfig() throws Exception {
        RequestBuilder request = post("/article/saveTemplateConfig")
                .param("url", "")
                .param("firstLevelPattern", "1")
                .param("titlePattern", "1")
                .param("timePattern", "1")
                .param("contentPattern", "1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msgCode").value("200"));
    }
}
