package vegetableStore.vService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vegetableStore.DAO.Vegetable;
import vegetableStore.vRepository.VegetablesImpl;

@Service
public class VegetableService {

	@Autowired
	VegetablesImpl veg;

	public List<Vegetable> getVegetables() {

		return veg.getAllVegetables();
	}

}
