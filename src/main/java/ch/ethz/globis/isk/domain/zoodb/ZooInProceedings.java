package ch.ethz.globis.isk.domain.zoodb;

import ch.ethz.globis.isk.domain.InProceedings;
import ch.ethz.globis.isk.domain.Proceedings;

public class ZooInProceedings extends ZooPublication implements InProceedings {
	
	String note;
	String pages;
	Proceedings proceedings;
	
	public ZooInProceedings() {
		
	}
	
	public ZooInProceedings(String note, String pages, Proceedings proceedings) {
		zooActivateWrite();
		this.note = note;
		this.pages = pages;
		this.proceedings = proceedings;
	}
	
	@Override
	public String getNote() {
		zooActivateRead();
		return note;
	}

	@Override
	public void setNote(String note) {
		zooActivateWrite();
		this.note = note;
	}

	@Override
	public String getPages() {
		zooActivateRead();
		return pages;
	}

	@Override
	public void setPages(String pages) {
		zooActivateWrite();
		this.pages = pages;
	}

	@Override
	public Proceedings getProceedings() {
		zooActivateRead();
		return proceedings;
	}

	@Override
	public void setProceedings(Proceedings proceedings) {
		zooActivateWrite();
		this.proceedings = proceedings;
	}

}
