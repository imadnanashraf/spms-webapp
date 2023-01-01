package com.adnan.icode.fun.spms.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.icode.fun.spms.dao.FacultyDao;
import com.adnan.icode.fun.spms.dao.StudentDao;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyRole;

@Service
public class FacultyLoginServiceImpl implements FacultyLoginService {
	
	@Autowired
	private FacultyDao facultyDao;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Faculty theFaculty = facultyDao.findFacultyByEmail(username);
		
		if(theFaculty != null && theFaculty.getEnabled()) {
			return new org.springframework.security
					.core.userdetails.User(theFaculty.getEmail(), theFaculty.getPassword(),
							mapRolesToAuthorities(theFaculty.getRoles()));
		}else if(theFaculty == null || !theFaculty.getEnabled()) {
			theFaculty = null;
			throw new UsernameNotFoundException("Invalid Email or Password");
		}
		
		
		return new org.springframework.security
				.core.userdetails.User(theFaculty.getEmail(), theFaculty.getPassword(),
						mapRolesToAuthorities(theFaculty.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<FacultyRole> roles) {
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

}
