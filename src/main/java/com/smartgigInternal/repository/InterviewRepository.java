package com.smartgigInternal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.entity.CandidateDetails;
import com.smartgigInternal.entity.Interviews;

public interface InterviewRepository extends JpaRepository<Interviews,Long > {

    Interviews findByCandidateIdAndInterviewRound(Long candidateId, String interviewRound);
    
    List<Interviews> findByCandidateId(Long candidateId);

    List<Interviews> findByInterviewDate(Date date);
//    @Query(value = "select t from Interviews t where t.interviewer=?1 and t.interviewDate=?2 and t.fromtime t.totime>?3 and t.amAndpm=?")
//	Interviews findByInterviewerAndInterviewerAndfromTimeAndtoTime(String interviewer, String interviewDate,
//			double totime, String amAndpm);

	List<Interviews> findByInterviewDateAndCandidateId(Date date, Long id);

}