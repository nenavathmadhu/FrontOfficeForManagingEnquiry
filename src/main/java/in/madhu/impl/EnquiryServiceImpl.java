package in.madhu.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.madhu.binding.DashboardResponse;
import in.madhu.binding.EnquiryForm;
import in.madhu.binding.EnquirySearchCriteria;
import in.madhu.entity.StudentEnqEntity;
import in.madhu.entity.UserDtlsEntity;
import in.madhu.repo.CourseRepo;
import in.madhu.repo.EnqStatusRepo;
import in.madhu.repo.StudentEnqRepo;
import in.madhu.repo.UserDtlsRepo;
import in.madhu.service.EnquiryService;


@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnqRepo studentRepo;

	@Autowired
	private UserDtlsRepo userRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;


	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		UserDtlsEntity user = findById.get();

		List<StudentEnqEntity> enquiries = user.getEnquries();
		long enrolledCount = enquiries.stream().filter(e -> e.getEnqStatus().equalsIgnoreCase("enrolled")).count();

		long lostCount = enquiries.stream().filter(e -> e.getEnqStatus().equalsIgnoreCase("lost")).count();

		long totalCount = enquiries.stream().count();

		DashboardResponse data = new DashboardResponse();

		data.setEnrolledCnt((int) enrolledCount);
		data.setLostCnt((int) lostCount);
		data.setTotalEnquriesCnt((int) totalCount);

		return data;
	}

	@Override
	public List<String> getCourses() {
		List<String> courses = courseRepo.getCourses();
		return courses;
	}

	@Override
	public List<String>  getEnqStatuses() {
		List<String> statusNames = statusRepo.getStatusNames();
		return statusNames;
	}

	@Override
	public StudentEnqEntity getStudentEnq(Integer eId) {
		Optional<StudentEnqEntity> findById = studentRepo.findById(eId);
		StudentEnqEntity entity = findById.get();
		return entity;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form, Integer userId) {
		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		UserDtlsEntity user = findById.get();

		if (form.getEnqId() != null) {
			
			StudentEnqEntity entity = studentRepo.findById(form.getEnqId()).get();
			entity.setClassMode(form.getClassMode());
			entity.setCourseName(form.getCourseName());
			entity.setEnqStatus(form.getEnqStatus());
			entity.setStudentPhno(form.getStudentPhno());
			entity.setStudentName(form.getStudentName());
			entity.setUser(user);
			
			entity.setUser(user);

			studentRepo.save(entity);

			return true;
		}

		StudentEnqEntity entity = new StudentEnqEntity();

		BeanUtils.copyProperties(form, entity);

		entity.setUser(user);

		studentRepo.save(entity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries(Integer userId, EnquirySearchCriteria search) {
		StudentEnqEntity entity = new StudentEnqEntity();

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		UserDtlsEntity user = findById.get();
		
		
		BeanUtils.copyProperties(search, entity);

		entity.setUser(user);

		Example<StudentEnqEntity> example = Example.of(entity);
		List<StudentEnqEntity> list = studentRepo.findAll(example);

		return list;
	}


}

