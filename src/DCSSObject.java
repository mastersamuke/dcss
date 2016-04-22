import java.util.LinkedList;

public class DCSSObject {
	private String strName;
	private LinkedList<String> llNames;
	private boolean bExclusive;

	public DCSSObject(String passedName) {
		strName = passedName;
		llNames = new LinkedList<String>();
		bExclusive = false;
	}

	public void SetExclusive(String strExclusive) {
		bExclusive = Boolean.parseBoolean(strExclusive);
	}

	public boolean GetExclusive() {
		return bExclusive;
	}

	public boolean AddDV(String strName) {
		return llNames.add(strName);
	}

	public LinkedList<String> GetDVs() {
		return llNames;
	}

	public String GetName() {
		return strName;
	}

	public int hashCode() {
		return strName.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof DCSSObject) {
			DCSSObject other = (DCSSObject) o;
			return (strName.equals(other.strName));
		}
		return false;
	}
}
