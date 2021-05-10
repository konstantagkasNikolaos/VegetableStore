package vegetableStore.vController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import vegetableStore.tools.MyLogger;

@Controller
public class HomeController extends MyLogger {


	@GetMapping("/")
	public String home() {

		log.info("home page showing");
		return "home";
	}

}
