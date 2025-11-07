package com.tenminread;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Disabled("Decouple from infra beans for now; enable when slice tests are added.")

@SpringBootTest
class TenminreadBeApplicationTests {

	@Test
	void contextLoads() {
	}

}
