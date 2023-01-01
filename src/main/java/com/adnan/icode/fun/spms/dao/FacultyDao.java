package com.adnan.icode.fun.spms.dao;

import java.util.List;

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyRole;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.MentorAllotStudents;

public interface FacultyDao {
	
	public void registerFaculty(Faculty tempFaculty);
	
	public Faculty findFacultyByEmail(String email);

	public FacultyVerificationToken loadFacultytoken(String token);

	public void deleteAllTokens(Faculty tempFaculty);

	public List<Faculty> findFacultyByDept(String dept);

	public List<Faculty> findFacultyByDeptWithRoles(String dept);

	public void deleteRole(FacultyRole deleteRole);

	public Faculty findFacultyByEmailWithRoles(String facultyEmail);

	public Faculty findFacultyByEmailWithStudents(String facultyEmail);

	public void deleteAllotedStudents(List<MentorAllotStudents> allotedStudents);



	

	

}
