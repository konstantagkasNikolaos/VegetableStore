package vegetableStore.DAO;

public class Vegetable {

	private long id;

	private String name;

	public Vegetable() {

	}

	public Vegetable(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Vegetable [id=" + id + ", name=" + name + "]";
	}

}
