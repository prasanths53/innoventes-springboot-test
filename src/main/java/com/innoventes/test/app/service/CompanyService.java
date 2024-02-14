package com.innoventes.test.app.service;

import java.util.List;
import java.util.Map;

import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.exception.ValidationException;

public interface CompanyService {

	List<Company> getAllCompanies();

	Company addCompany(Company company) throws ValidationException;

	Company updateCompany(Long id, Company company) throws ValidationException;
	
	void deleteCompany(Long id);
	
	Company getCompanyById(Long id);
	
	Company getCompanyByCompanyCode(String companyCode);
	
	Company partialUpdateCompany(Long id, Map<String, Object> updates) throws ValidationException;
}