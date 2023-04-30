package in.madhu.controller;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.madhu.binding.DashboardResponse;
import in.madhu.binding.EnquiryForm;
import in.madhu.binding.EnquirySearchCriteria;
import in.madhu.entity.StudentEnqEntity;
import in.madhu.repo.StudentEnqRepo;
import in.madhu.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnqRepo studentRepo;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("id") Integer eId, Model model) {

		StudentEnqEntity entity = enqService.getStudentEnq(eId);

		EnquiryForm form = new EnquiryForm();

		BeanUtils.copyProperties(entity, form);

		initForm(model);
		model.addAttribute("formObj", form);

		return "add-enquiry";
	}
	
	@GetMapping("/viewenquires")
	public String getAllproducts(Model model) {
		List<StudentEnqEntity> list = studentRepo.findAll();
		model.addAttribute("enquires", list);
		return "View-enquires";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		model.addAttribute("formObj", new EnquiryForm());

		initForm(model);

		return "add-enquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		boolean status = enqService.saveEnquiry(formObj, userId);

		if (status) {
			model.addAttribute("successMsg", "Enquiry Added..");
		} else {
			model.addAttribute("errMsg", "Problem Occured..");
		}
		return "add-enquiry";
	}

	private void initForm(Model model) {
		// get courses for dropdown

		List<String> courses = enqService.getCourses();

		// get enqStatus for dropdown

		List<String> enqStatus = enqService.getEnqStatuses();

		// create binding class obj

		// set data in model obj

		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatus);

	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		// model.addAttribute("searchForm", new EnquirySearchCriteria());
		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnqEntity> enquires = enqService.getEnquiries(userId, new EnquirySearchCriteria());
		model.addAttribute("enquires", enquires);
		return "View-enquires";

	}
	
	@GetMapping("/filter-enquries")
	public String getFilteredEnqs(@RequestParam("cname") String cname, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();

		if(null != mode && !"".equals(mode)) {
			criteria.setClassMode(mode);
		}

		if(null != cname && !"".equals(cname)) {
			criteria.setCourseName(cname);
		}
		
		if(null != status && !"".equals(status)) {
			criteria.setEnqStatus(status);
		}

		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnqEntity> filteredEnqs = enqService.getEnquiries(userId, criteria);

		model.addAttribute("enquires", filteredEnqs);

		return "filter-enquiry-page";
	}

}



