import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springSleuthVersion = "3.0.4"
val tokenValidationVersion = "2.1.2"

plugins {
	id("org.springframework.boot") version "2.5.12"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.7.10"
	kotlin("plugin.spring") version "1.7.10"
}

group = "no.nav.klage"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth:$springSleuthVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("no.nav.security:token-validation-spring:$tokenValidationVersion")
	implementation("no.nav.security:token-client-spring:$tokenValidationVersion")
	implementation("com.github.navikt:kabal-kodeverk:2022.07.28-10.26.a72aea83f105")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	this.archiveFileName.set("app.jar")
}
