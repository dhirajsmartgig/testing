package com.smartgigInternal.service;

import java.util.Date;
import java.util.List;

import java.util.Map;
import com.smartgigInternal.dto.InterviewDetailsDto;
import com.smartgigInternal.entity.Interviews;

public interface InterviewService {
    
      public Map<String, Object>saveInterviewDetails(Interviews interviews);

      public  List<Map<String, Object>>  getBycandidateId(Long candidateId);

      public List<Map<String, Object>> getAllInterviewsDetails(Date date, String role);

      public Map<String, Object>editInterviwesDetails(InterviewDetailsDto dto, Long candidateId);
      
	  public Map<String, Object> getInterviewsDetailsById(Long id);
}
