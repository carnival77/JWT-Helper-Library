plugins {
	java
	// Spring Boot 기반 환경을 완전히 제거하고 순수한 Spring 환경으로 변경
//	id("org.springframework.boot") version "3.2.5"
//	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.9.10"
	id("jacoco")
}

jacoco {
	toolVersion = "0.8.8" // 원하는 JaCoCo 버전
}

tasks.test {
	useJUnitPlatform() // JUnit5 사용 시
	// build, bootJar 등의 작업은 test 작업을 포함한다
// test 가 수행될 때마다 jacocoTestReport 작업도 수행하도록 설정하는 내용이다
	finalizedBy(tasks.jacocoTestReport) // 테스트 후 jacocoTestReport 태스크 실행
}

// Jacoco 리포트 생성 설정
// build 혹은 bootJar 작업은 test를 포함하므로,
// 설정에 의해 test 수행 후 jacocoTestReport 를 수행,
// 이때 build/reports/jacoco 이하 경로에 리포트 파일들을 생성
tasks.jacocoTestReport { // jacocoTestReport 작업은 생성 전에 반드시 test 작업이 수행되도록 선언
	dependsOn(tasks.test) // test 작업을 수행한다면, jacocoTestReport 작업도 함께 수행되도록 선언
	reports {
		xml.required.set(true)  // XML 리포트 생성. xml파일은 Github Actions에서 사용
		html.required.set(true) // HTML 리포트 생성. html파일은 로컬 개발환경에서 생성된 결과를 확인하는데 사용
	}
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework:spring-context:6.2.0")

	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	testImplementation("org.springframework:spring-context:6.2.0")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0") // JUnit 5 API
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0") // JUnit 5 실행 엔진
	testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.test {
//	useJUnitPlatform()
//}
