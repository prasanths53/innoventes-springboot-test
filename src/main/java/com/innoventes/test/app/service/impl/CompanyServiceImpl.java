package com.innoventes.test.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.error.ApplicationErrorCodes;
import com.innoventes.test.app.exception.ResourceNotFoundException;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.repository.CompanyRepository;
import com.innoventes.test.app.service.CompanyService;
import com.innoventes.test.app.util.ServiceHelper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ServiceHelper serviceHelper;

	@Override
	public List<Company> getAllCompanies() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		companyRepository.findAll().forEach(companyList::add);
		return companyList;
	}

	@Override
	public Company addCompany(Company company) throws ValidationException {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) throws ValidationException {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		company.setId(existingCompanyRecord.getId());
		return companyRepository.save(company);
	}

	@Override
	public void deleteCompany(Long id) {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		companyRepository.deleteById(existingCompanyRecord.getId());
	}
	
	public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
	
	@Override
    public Company getCompanyByCompanyCode(String companyCode) {
        return companyRepository.findByCompanyCode(companyCode);
    }

	@Override
    public Company partialUpdateCompany(Long id, Map<String, Object> updates) throws ValidationException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Company not found with id: " + id));

        applyUpdates(company, updates);

        validateCompany(company);

        return companyRepository.save(company);
    }
	
	private void applyUpdates(Company company, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            switch (key) {
                case "companyName":
                    company.setCompanyName((String) value);
                    break;
                case "email":
                    company.setEmail((String) value);
                    break;
                case "strength":
                    company.setStrength((Integer) value);
                    break;
                case "webSiteURL":
                    company.setWebSiteURL((String) value);
                    break;
                case "companyCode":
                    company.setCompanyCode((String) value);
                    break;
                // Add cases for other fields if needed
                default:
                    break;
            }
        });
    }
	
	private void validateCompany(Company company) throws ValidationException {
	    // Validate the company object
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();
	    Set<ConstraintViolation<Company>> violations = validator.validate(company);
	    if (!violations.isEmpty()) {
	        BindingResult bindingResult = new BeanPropertyBindingResult(company, "company");
	        for (ConstraintViolation<Company> violation : violations) {
	            String propertyPath = violation.getPropertyPath().toString();
	            String message = violation.getMessage();
	            bindingResult.addError(new FieldError("company", propertyPath, message));
	        }
	        throw new ValidationException(bindingResult);
	    }
	}
}
