package com.rc.exspring;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class JasyptTest {

	@DisplayName("프로퍼티 암호화 테스트")
	@Test
	void jasypt(){
		String password = System.getenv("JASYPT_ENC_PW");
		System.out.println("#############"+password);

		//given(준비): 어떠한 데이터가 준비되었을 때
		String userName = "root";

		//when(실행): 어떠한 함수를 실행하면
		String encryUserName = jasyptEncrypt(userName);

		//then(검증): 어떠한 결과가 나와야 한다.
		//assertThat(dto.getAmount()).isEqualTo(amount);
		System.out.println("encryUserName : " + encryUserName);
	}

	private String jasyptEncrypt(String input) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		return encryptor.encrypt(input);
	}

	private String jasyptDecryt(String input){
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		return encryptor.decrypt(input);
	}

	@DisplayName("프로퍼티 암호화 테스트2")
	@Test
	void jasypt2(){
		//System.out.println("#############"+PASSWORD);

		//given(준비): 어떠한 데이터가 준비되었을 때
		String key = System.getenv("JASYPT_ENC_PW");
		String userName = "root";
		String password = "1234";

		//when(실행): 어떠한 함수를 실행하면
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		// 암호화 키 설정: 암호화와 복호화에 사용될 비밀 키 설정
		config.setPassword(key);
		// 암호화 알고리즘 설정: MD5 해싱과 DES 암호화를 결합한 PBE 알고리즘 사용
		config.setAlgorithm("PBEWithMD5AndDES");
		// 키 생성 반복 횟수 설정: 보안 강화를 위해 키 생성 과정에서 수행할 반복 횟수
		config.setKeyObtentionIterations("1000");
		// 풀 크기 설정: 동시에 수행될 수 있는 암호화 작업의 수를 1로 설정
		config.setPoolSize("1");
		// 암호화 제공자 설정: Java Cryptography Extension (JCE)의 SunJCE를 사용
		config.setProviderName("SunJCE");
		// 소금 생성기 클래스 설정: 암호화 시 사용할 소금을 무작위로 생성하는 클래스 지정
		config.setSaltGeneratorClassName("ofg.jasypt.salt.RandomSaltGenerator");
		//문자열 출력 형식 설정: 암호화된 데이터의 출력 형식을 base64로 설정
		config.setStringOutputType("base64");
		//암호화기에 구성 설정 적용: 위에서 정의한 설정을 암호화기에 적용
		encryptor.setConfig(config);

		String encryptStr = encryptor.encrypt(userName);
		String decryptStr = encryptor.decrypt(userName);

		//then(검증): 어떠한 결과가 나와야 한다.
		System.out.println("암호화 된 문자열 : " + encryptStr);
		System.out.println("복호화 된 문자열 : " + decryptStr);
		//assertThat(dto.getAmount()).isEqualTo(amount);
	}

	@DisplayName("프로퍼티 암호화 테스트2")
	@Test
	void jasypt3(){
		//System.out.println("#############"+PASSWORD);

		//given(준비): 어떠한 데이터가 준비되었을 때
		String key = System.getenv("JASYPT_ENC_PW");
		String url = "jdbc:mysql://localhost/poms?characterEncoding=UTF-8&serverTimezone=UTC&allowMultiQueries=true&useCursorFetch=true&fetchSize=1000";
		String userName = "root";
		String password = "1234";

		//when(실행): 어떠한 함수를 실행하면
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setPoolSize(8);   // 코어 수
		encryptor.setPassword(key);
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");  // 암호화 알고리즘
		String encryptStr = encryptor.encrypt(url);
		String decryptStr = encryptor.decrypt(encryptStr);

		//then(검증): 어떠한 결과가 나와야 한다.
		System.out.println("암호화 된 문자열 : " + encryptStr);
		System.out.println("복호화 된 문자열 : " + decryptStr);
		//assertThat(dto.getAmount()).isEqualTo(amount);
	}
}
