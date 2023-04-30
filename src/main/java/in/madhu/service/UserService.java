package in.madhu.service;

import in.madhu.binding.LoginForm;
import in.madhu.binding.SignUpForm;
import in.madhu.binding.UnlockForm;

public interface UserService {
	
	public boolean signup(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
		
    public String login(LoginForm form);
    
    public boolean forgotPwd(String email);
    
	}


