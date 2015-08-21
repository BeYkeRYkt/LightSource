package ru.BeYkeRYkt.LightSource.sources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Entity;

public class SourceManager {

	private List<Source> sources;

	public SourceManager() {
		this.sources = new ArrayList<Source>();
	}

	public boolean addSource(Source source) {
		if (!sources.isEmpty()) {
			for (Source i : sources) {
				if (!i.equals(source)) {
					sources.add(source);
					return true;
				}
			}
		} else {
			sources.add(source);
		}

		return false;
	}

	public synchronized boolean removeSource(Source source) {
		Iterator<Source> it = getSourceList().iterator();
		while (it.hasNext()) {
			Source i = it.next();
			if (i.equals(source)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	public Source getSource(Entity entity) {
		if (!sources.isEmpty()) {
			for (Source i : sources) {
				if (i.getOwner().getEntityId() == entity.getEntityId()) {
					return i;
				}
			}
		}
		return null;
	}

	public List<Source> getSourceList() {
		return sources;
	}
}
