package com.smartgigInternal.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgigInternal.dto.InterviewDetailsDto;
import com.smartgigInternal.entity.CandidateDetails;
import com.smartgigInternal.entity.Interviews;
import com.smartgigInternal.repository.CandidateDetailsRepository;
import com.smartgigInternal.repository.InterviewRepository;
import com.smartgigInternal.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	public InterviewRepository interviewRepository;

	@Autowired
	private CandidateDetailsRepository candidateDetailsRepository;

	@Override
	public Map<String, Object> saveInterviewDetails(Interviews interview) {
		Map<String, Object> map = new HashMap();
		CandidateDetails candidate = candidateDetailsRepository.findByIdAndStatus(interview.getCandidateId(),
				"Rejected");
		if (candidate != null) {
			map.put("msg", "this is rejected candidate");
			return map;
		}
		Interviews interviews = this.interviewRepository.findByCandidateIdAndInterviewRound(interview.getCandidateId(),
				interview.getInterviewRound());
		if (interviews != null) {
			map.put("msg", "Interview round already scheduled");
			return map;
		}
//		Interviews inter = this.interviewRepository.findByInterviewerAndInterviewerAndfromTimeAndtoTime(
//				interview.getInterviewer(), interview.getInterviewDate(), interview.getTotime(),
//				interview.getAmAndpm());
//		if (inter != null) {
//			map.put("msg", "interviewer not avilable");
//			return map;
//		}
		Interviews details = interviewRepository.save(interview);
		map.put("msg", "interview scheduled");
		map.put("data", details);
		return map;
	}

	@Override
	public List<Map<String, Object>> getBycandidateId(Long candidateId) {
		List<Map<String, Object>> resultList = new ArrayList();
		List<Interviews> list = interviewRepository.findByCandidateId(candidateId);
		for (Interviews inter : list) {
			Map<String, Object> map = new HashMap();
			CandidateDetails candidate = this.candidateDetailsRepository.findById(inter.getCandidateId()).get();
			if (candidate != null) {
				map.put("interviewId", inter.getId());
				map.put("candidateName", candidate.getCandidateName());
				map.put("appliedfor", candidate.getAppliedFor());
				map.put("candidateId", inter.getCandidateId());
				map.put("date", inter.getInterviewDate().toString());
				map.put("fromtime", String.format("%.2f", inter.getFromtime()));
				map.put("totime", String.format("%.2f", inter.getTotime()));
				map.put("amAndpm", inter.getAmAndpm());
				map.put("interviewer", inter.getInterviewer());
				map.put("interviewRound", inter.getInterviewRound());
				map.put("interviewType", inter.getInterviewType());
				map.put("interviewStatus", inter.getInterviewStatus());
				map.put("comment", inter.getComments());
				resultList.add(map);
			}
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getAllInterviewsDetails(Date date, String role) {
		List<Interviews> list = interviewRepository.findByInterviewDate(date);
		List<CandidateDetails> candidateList = candidateDetailsRepository.findByAppliedFor(role);
		List<Map<String, Object>> resultList = new ArrayList();
		if (role.equals("")) {
			for (Interviews inter : list) {
				Map<String, Object> map = new HashMap();
				CandidateDetails candidate = this.candidateDetailsRepository.findById(inter.getCandidateId()).get();
				if (candidate != null) {
					map.put("interviewId", inter.getId());
					map.put("candidateName", candidate.getCandidateName());
					map.put("appliedfor", candidate.getAppliedFor());
					map.put("candidateId", inter.getCandidateId());
					map.put("date", inter.getInterviewDate().toString());
					map.put("time", String.format("%.2f", inter.getFromtime()) + " " + "TO" + " "
							+ String.format("%.2f", inter.getTotime()) + " " + inter.getAmAndpm());
					map.put("interviewer", inter.getInterviewer());
					map.put("interviewRound", inter.getInterviewRound());
					map.put("interviewType", inter.getInterviewType());
					map.put("interviewStatus", inter.getInterviewStatus());
					map.put("comment", inter.getComments());
					resultList.add(map);
				}
			}
		} else {
			for (CandidateDetails inter : candidateList) {
				List<Interviews> interList = interviewRepository.findByInterviewDateAndCandidateId(date, inter.getId());
				for (Interviews inter1 : interList) {
					Map<String, Object> map = new HashMap();
					CandidateDetails candidate = this.candidateDetailsRepository.findById(inter1.getCandidateId())
							.get();
					if (candidate != null) {
						map.put("interviewId", inter1.getId());
						map.put("candidateName", candidate.getCandidateName());
						map.put("appliedfor", candidate.getAppliedFor());
						map.put("candidateId", inter1.getCandidateId());
						map.put("date", inter1.getInterviewDate().toString());
						map.put("time", String.format("%.2f", inter1.getFromtime()) + " " + "TO" + " "
								+ String.format("%.2f", inter1.getTotime()) + " " + inter1.getAmAndpm());
						map.put("interviewer", inter1.getInterviewer());
						map.put("interviewRound", inter1.getInterviewRound());
						map.put("interviewType", inter1.getInterviewType());
						map.put("interviewStatus", inter1.getInterviewStatus());
						map.put("comment", inter1.getComments());
						resultList.add(map);
					}
				}
			}
		}
		return resultList;

	}

	@Override
	public Map<String, Object> editInterviwesDetails(InterviewDetailsDto dto, Long id) {
		Interviews existingInterview = interviewRepository.findById(id).get();
		Map<String, Object> map = new HashMap<>();
		if (existingInterview != null) {
			if (dto.getCandidateId() != null)
				existingInterview.setCandidateId(dto.getCandidateId());
			if (dto.getAmAndpm() != null)
				existingInterview.setAmAndpm(dto.getAmAndpm());
			if (dto.getInterviewDate() != null)
				existingInterview.setInterviewDate(dto.getInterviewDate());
			if (dto.getFromtime() != 0)
				existingInterview.setFromtime(dto.getFromtime());
			if (dto.getTotime() != 0)
				existingInterview.setTotime(dto.getTotime());
			if (dto.getInterviewer() != null)
				existingInterview.setInterviewer(dto.getInterviewer());
			if (dto.getInterviewRound() != null)
				existingInterview.setInterviewRound(dto.getInterviewRound());
			if (dto.getInterviewType() != null)
				existingInterview.setInterviewType(dto.getInterviewType());
			if (dto.getInterviewStatus() != null)
				existingInterview.setInterviewStatus(dto.getInterviewStatus());
			if (dto.getRoundStatus() != null)
				existingInterview.setRoundStatus(dto.getRoundStatus());
			if (dto.getComments() != null)
				existingInterview.setComments(dto.getComments());
			interviewRepository.save(existingInterview);
			map.put("msg", "Interviews details updated");
		}
		return map;
	}

	@Override
	public Map<String, Object> getInterviewsDetailsById(Long id) {
		Map<String, Object> map = new HashMap();
		Interviews inter = interviewRepository.findById(id).get();
		CandidateDetails candidate = this.candidateDetailsRepository.findById(inter.getCandidateId()).get();
		if (candidate != null) {
			map.put("interviewId", inter.getId());
			map.put("candidateName", candidate.getCandidateName());
			map.put("appliedfor", candidate.getAppliedFor());
			map.put("candidateId", inter.getCandidateId());
			map.put("date", inter.getInterviewDate().toString());
			map.put("fromtime", String.format("%.2f", inter.getFromtime()));
			map.put("totime", String.format("%.2f", inter.getTotime()));
			map.put("amAndpm", inter.getAmAndpm());
			map.put("interviewer", inter.getInterviewer());
			map.put("interviewRound", inter.getInterviewRound());
			map.put("interviewType", inter.getInterviewType());
			map.put("interviewStatus", inter.getInterviewStatus());
			map.put("comment", inter.getComments());
		}
		return map;
	}
}