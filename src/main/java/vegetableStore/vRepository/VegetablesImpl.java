package vegetableStore.vRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vegetableStore.DAO.Vegetable;

@Repository
public class VegetablesImpl implements Vegetables {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Vegetable> getAllVegetables() {

		return jdbcTemplate.query("select * from vegetables",
				(rs, rowNum) -> new Vegetable(rs.getInt("id"), rs.getString("name")));

	}

}
