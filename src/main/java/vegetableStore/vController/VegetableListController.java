package vegetableStore.vController;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vegetableStore.tools.MyLogger;
import vegetableStore.vService.PdfService;
import vegetableStore.vService.VegetableService;

@Controller
public class VegetableListController extends MyLogger {

	@Autowired
	VegetableService service;

	@Autowired
	PdfService pdfService;

	@GetMapping("/vegetableList")
	public String getVegetableList(Model model) throws URISyntaxException, IOException {

		log.info("asking for vegetable list");

		model.addAttribute("vegetables", service.getVegetables());

		return "vegetable_list";
	}

	@GetMapping("/getPdfVegetableList")
	public void downloadFile(HttpServletResponse response) {

		log.info("download pdf of vegetables requested");

		pdfService.getPDF();
		pdfService.sendResponse(response);

	}
}
