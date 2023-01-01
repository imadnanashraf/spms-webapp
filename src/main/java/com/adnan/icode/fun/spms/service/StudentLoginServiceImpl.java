package com.adnan.icode.fun.spms.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.icode.fun.spms.dao.StudentDao;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentRole;
import com.adnan.icode.fun.spms.exception.VerificationExpireException;

@Service
public class StudentLoginServiceImpl implements StudentLoginService {
	
	@Autowired
	private StudentDao studentDao;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Student theStudent = studentDao.findStudentByEmail(username);
		
		
		if(theStudent != null && theStudent.getEnabled()) {
			
			return new org.springframework.security
					.core.userdetails.User(theStudent.getEmail(), theStudent.getPassword(),
							mapRolesToAuthorities(theStudent.getRoles()));
			
		}else if(theStudent == null || !theStudent.getEnabled() ){
			theStudent = null;
			throw new UsernameNotFoundException("Invalid Email or Password");
		}
		
	
			return new org.springframework.security
					.core.userdetails.User(theStudent.getEmail(), theStudent.getPassword(),
							mapRolesToAuthorities(theStudent.getRoles())) ;
		}
	

		private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<StudentRole> roles) {
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());

	}

	

}
