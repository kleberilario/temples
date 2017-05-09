package temples;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class TemplesApplicationTest {
	
	@Test
	public void build() throws FileNotFoundException{
		InputStream template = this.getClass().getResourceAsStream("template.txt");
		Temples temples = new Temples();
		temples.build(template);
	}

}
