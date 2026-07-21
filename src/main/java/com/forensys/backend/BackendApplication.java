package com.forensys.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner fixDatabaseConstraints(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				jdbcTemplate.execute("ALTER TABLE forensic_report DROP CONSTRAINT IF EXISTS forensic_report_report_type_check");
				jdbcTemplate.execute("ALTER TABLE forensic_report ADD CONSTRAINT forensic_report_report_type_check CHECK (report_type IN ('MLR', 'MLEF', 'PMR', 'CERTIFICATE_OF_RECEIPT'))");
			} catch (Exception e) {
				System.err.println("Database constraint migration note: " + e.getMessage());
			}
		};
	}
}
