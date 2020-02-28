package org.robatipoor.sessionscope;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SessionScopeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionScopeApplication.class, args);
	}
}

@RestController
class MainController {

	private DataSesstion dataSesstion;
	private Visitor visitor;

	@Autowired
	public MainController(DataSesstion dataSesstion, Visitor visitor) {
		this.dataSesstion = dataSesstion;
		this.visitor = visitor;
	}

	@GetMapping("/")
	public String getSessionValue() {
		System.out.println(visitor.getIpAddr());
		return dataSesstion.getData();
	}

	@GetMapping("/change")
	public String changeSessionValue() {
		dataSesstion.setData("Change Session !!!");
		System.out.println(visitor.getIpAddr());
		return "Ok Data Session Change !!!";
	}
}

@Configuration
class Config {

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public DataSesstion dataSesstion() {
		return new DataSesstion("New Data Session !!!");
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Visitor visitor(HttpServletRequest request) {
		return new Visitor(request.getRemoteAddr());
	}

}

class Visitor implements Serializable {
	private static final long serialVersionUID = -6267410619458022908L;

	private String ipAddr;

	public Visitor() {
	}

	public Visitor(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Visitor ipAddr(String ipAddr) {
		this.ipAddr = ipAddr;
		return this;
	}

	@Override
	public String toString() {
		return "{" + " ipAddr='" + getIpAddr() + "'" + "}";
	}

}

class DataSesstion implements Serializable {

	private static final long serialVersionUID = -9062743127648365016L;

	private String data;

	public DataSesstion() {
	}

	public DataSesstion(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{" + " data='" + getData() + "'" + "}";
	}

}