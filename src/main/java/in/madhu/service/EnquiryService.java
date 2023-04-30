package in.madhu.service;

import java.util.List;

import in.madhu.binding.DashboardResponse;
import in.madhu.binding.EnquiryForm;
import in.madhu.binding.EnquirySearchCriteria;
import in.madhu.entity.StudentEnqEntity;

public interface EnquiryService {

	public List<String> getCourses();

	public List<String> getEnqStatuses();

	public DashboardResponse getDashboardData(Integer userId);

	public boolean saveEnquiry(EnquiryForm form, Integer userId);
	
	public StudentEnqEntity getStudentEnq(Integer eId);

	List<StudentEnqEntity> getEnquiries(Integer userId,EnquirySearchCriteria search);

}
