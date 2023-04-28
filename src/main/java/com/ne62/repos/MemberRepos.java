package com.ne62.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ne62.models.Members;

@Repository
public interface MemberRepos extends JpaRepository<Members, String> {

	List<Members> findByMemberId(String memberId);
	List<Members> findByMemberName(String memberName);
}
