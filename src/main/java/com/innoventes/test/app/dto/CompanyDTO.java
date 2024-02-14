package com.innoventes.test.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.innoventes.test.app.validation.EvenNumberOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyDTO {

	private Long id;

	@NotBlank(message = "Company name is mandatory")
    @Size(min = 5, message = "Company name must be at least 5 characters long")
	private String companyName;

	@NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
	private String email;

	@PositiveOrZero(message = "Strength should be a positive number or zero")
	@NotNull(message = "Strength is mandatory")
	@EvenNumberOrZero(message = "Strength must be an even number or zero")
	private Integer strength;
	
	private String webSiteURL;
	
	@Size(min = 5, max = 5, message = "Company code must be 5 characters long")
    @Pattern(regexp = "^[a-zA-Z]{2}\\d{2}[EN]$", message = "Invalid company code format")
    private String companyCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public String getWebSiteURL() {
		return webSiteURL;
	}

	public void setWebSiteURL(String webSiteURL) {
		this.webSiteURL = webSiteURL;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
}
