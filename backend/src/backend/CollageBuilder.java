import java.util.ArrayList;
import java.util.List;

public class CollageBuilder {
	
	private Boolean insufficientErrorMessageBoolean;
	private List<Collage> collages;
	
	public CollageBuilder() {
		collages = new ArrayList<Collage>(30);
		
	}
	
	//returns true/false whether the collage list has 30 images or not
	public boolean calculateSufficiecy() {
		return (collages.size() >= 30);
	}
	
	//builds the collage
	public void buildCollage() {
		
	}
	
	
	
	
	
	
	
	

}
