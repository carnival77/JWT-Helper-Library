plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.9.10"
	id("jacoco")
	// 퍼블리싱 플러그인
//	id("maven-publish") // Maven 퍼블리싱 플러그인
//	signing       // 서명 플러그인 (Maven Central 필수)
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

// 프로젝트 메타데이터 정의 - 라이브러리 정보
group = "com.example"       // 그룹 ID (예: 도메인 형식)
version = "1.0.0"           // 라이브러리 버전
description = "JWT_Parsing_Verification_Library" // 라이브러리 설명


java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Maven Central 퍼블리싱 설정

// Maven 퍼블리싱 설정
//publishing {
//	publications {
//		create<MavenPublication>("mavenJava") {
//			from(components["java"]) // Java/Kotlin 컴포넌트 포함
//
//			// 프로젝트 메타데이터
//			pom {
//				name.set("My Kotlin Library")
//				description.set("A sample library for Maven Central publishing.")
//				url.set("https://github.com/username/my-library")
//
//				licenses {
//					license {
//						name.set("The Apache License, Version 2.0")
//						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//					}
//				}
//
//				developers {
//					developer {
//						id.set("username")
//						name.set("Your Name")
//						email.set("your.email@example.com")
//					}
//				}
//
//				scm {
//					connection.set("scm:git:git://github.com/username/my-library.git")
//					developerConnection.set("scm:git:ssh://github.com/username/my-library.git")
//					url.set("https://github.com/username/my-library")
//				}
//			}
//		}
//	}
//
//	repositories {
//		maven {
//			name = "MavenCentral"
//			url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
//
//			credentials {
//				username = System.getenv("OSSRH_USERNAME") // Sonatype 계정
//				password = System.getenv("OSSRH_PASSWORD") // Sonatype 비밀번호
//			}
//		}
//	}
//}
//
//// GPG 서명 설정
//signing {
//	useInMemoryPgpKeys(
//		System.getenv("GPG_PRIVATE_KEY"),  // GPG 개인 키
//		System.getenv("GPG_PRIVATE_KEY_PASSWORD") // GPG 비밀번호
//	)
//	sign(publishing.publications["mavenJava"])
//}
