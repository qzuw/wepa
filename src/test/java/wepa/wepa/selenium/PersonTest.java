
package wepa.wepa.selenium;

import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonTest extends FluentTest {
 
    public WebDriver webDriver = new HtmlUnitDriver();
    
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void fillForm() {
//        goTo("http://localhost:" + port + "/persons");
//
//
//        assertFalse(pageSource().contains("555555555"));
//        assertFalse(pageSource().contains("Maija"));
//        
//        goTo("http://localhost:" + port + "/exercises");
//
//        fill(find("#studentNumber")).with("555555555");
//        fill(find("#name")).with("555555555");
//        submit(find("form").first());
//
//
//        assertTrue(pageSource().contains("Van Damme"));
//
//
//        submit(find("ol").find("form").first());
//        assertFalse(pageSource().contains("Van Damme"));
    }

    
}
